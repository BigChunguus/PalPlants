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
        View view = inflater.inflate(R.layout.fragment_signup_tab, container, false);

        editTextUsername = view.findViewById(R.id.usernameRegister);
        editTextEmail = view.findViewById(R.id.emailRegister);
        editTextPassword = view.findViewById(R.id.passwordRegister);
        editTextConfirmPassword = view.findViewById(R.id.confirmPasswordRegister);
        buttonRegister = view.findViewById(R.id.registerButton);

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

        if (!username.matches("[a-zA-Z0-9]+")) {
            editTextUsername.setError("Nombre de usuario debe contener solo caracteres alfanuméricos");
            editTextUsername.requestFocus();
            usuarioRegistro = new Usuario();
            return;
        }

        usuarioRegistro.setNombreUsuario(username);

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Correo electrónico inválido");
            editTextEmail.requestFocus();
            usuarioRegistro = new Usuario();
            return;
        }

        usuarioRegistro.setEmail(email);

        if (password.length() < 8) {
            editTextPassword.setError("Contraseña debe tener al menos 8 caracteres");
            editTextPassword.requestFocus();
            usuarioRegistro = new Usuario();
            return;
        }

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
                BotanicaCC bcc = new BotanicaCC();
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
                saveUserToSharedPreferences(usuarioRegistro);
                Intent intent = new Intent(getActivity(), YourPlantsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            } else {
                Toast.makeText(getActivity(), "Error al registrar usuario", Toast.LENGTH_SHORT).show();
            }
        }


        private void saveUserToSharedPreferences(Usuario usuario) {
            if (usuario != null) {
                Log.d("UserInfo", "Usuario recibido: " + usuario.toString());

                Gson gson = new Gson();
                String usuarioJson = gson.toJson(usuario);

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                String usuarioGuardado = sharedPreferences.getString("user", "");
                if (!usuarioGuardado.isEmpty()) {
                    editor.remove("user");
                    Log.d("UserInfo", "Usuario anterior eliminado de SharedPreferences");
                }

                editor.putString("user", usuarioJson);
                editor.apply();

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
