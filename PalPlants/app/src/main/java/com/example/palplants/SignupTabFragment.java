package com.example.palplants;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Usuario;

public class SignupTabFragment extends Fragment {

    private EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword;
    private Button buttonRegister;
    private TextView textViewLogin;
    private Usuario usuarioRegistro = new Usuario();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_tab, container, false);

        editTextUsername = view.findViewById(R.id.usernameRegister);
        editTextEmail = view.findViewById(R.id.emailRegister);
        editTextPassword = view.findViewById(R.id.passwordRegister);
        editTextConfirmPassword = view.findViewById(R.id.confirmPasswordRegister);
        buttonRegister = view.findViewById(R.id.registerButton);

        // Escuchar el botón si es clickeado
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });


        return view;
    }


    private void registerUser() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();


        // Validar nombre de usuario
        if (!username.matches("[a-zA-Z0-9]+")) {
            editTextUsername.setError("Nombre de usuario debe contener solo caracteres alfanuméricos");
            editTextUsername.requestFocus();
            usuarioRegistro = new Usuario();
            return;
        }

        usuarioRegistro.setNombreUsuario(username);
        // Validar correo electrónico
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Correo electrónico inválido");
            editTextEmail.requestFocus();
            usuarioRegistro = new Usuario();
            return;
        }

        usuarioRegistro.setEmail(email);
        // Validar longitud de contraseña
        if (password.length() < 8) {
            editTextPassword.setError("Contraseña debe tener al menos 8 caracteres");
            editTextPassword.requestFocus();
            usuarioRegistro = new Usuario();
            return;
        }

        // Validar que las contraseñas coincidan
        if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.setError("Las contraseñas no coinciden");
            editTextConfirmPassword.requestFocus();
            usuarioRegistro = new Usuario();
            return;
        }
        usuarioRegistro.setContrasena(password);

        new InsertarUsuarioTask().execute(usuarioRegistro);
    }

    private class InsertarUsuarioTask extends AsyncTask<Usuario, Void, Integer> {
        @Override
        protected Integer doInBackground(Usuario... params) {
            Usuario usuario = params[0];
            try {
                BotanicaCC bcc = new BotanicaCC(); // No se pasa la IP
                return bcc.insertarUsuario(usuario);
            } catch (ExcepcionBotanica ex) {
                ex.printStackTrace();
                Toast.makeText(getActivity(), "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                return -1;
            }
        }

        @Override
        protected void onPostExecute(Integer resultado) {
            if (resultado == 1) {
                // Si la inserción fue exitosa, guardar el usuario en SharedPreferences
                saveUserToSharedPreferences(usuarioRegistro);

                // Iniciar la YourPlantsActivity
                Intent intent = new Intent(getActivity(), YourPlantsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            } else {
                // Si hubo un error, avisar al usuario
                Toast.makeText(getActivity(), "Error al registrar usuario", Toast.LENGTH_SHORT).show();
            }
        }


        private void saveUserToSharedPreferences(Usuario usuario) {
            // Comprobamos que se recibe un usuario y su contenido
            if (usuario != null) {
                Log.d("UserInfo", "Usuario recibido: " + usuario.toString());

                // Convertimos el usuario a JSON
                Gson gson = new Gson();
                String usuarioJson = gson.toJson(usuario);

                // Guardamos el JSON en las preferencias compartidas
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // Verificar si ya hay un usuario guardado
                String usuarioGuardado = sharedPreferences.getString("user", "");
                if (!usuarioGuardado.isEmpty()) {
                    // Eliminar el usuario existente
                    editor.remove("user");
                    Log.d("UserInfo", "Usuario anterior eliminado de SharedPreferences");
                }

                // Guardar el nuevo usuario
                editor.putString("user", usuarioJson);
                editor.apply();

                // Verificar que se guarda correctamente en las preferencias compartidas
                usuarioGuardado = sharedPreferences.getString("user", "");
                if (!usuarioGuardado.isEmpty()) {
                    Log.d("UserInfo", "Usuario guardado correctamente en SharedPreferences: " + usuarioGuardado);
                } else {
                    Log.e("UserInfo", "Error: No se pudo guardar el usuario en SharedPreferences");
                }
            } else {
                Log.e("UserInfo", "Error: No se recibió ningún usuario");
            }
        }
    }


}