package com.example.palplants.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.palplants.Activity.YourPlantsActivity;
import com.example.palplants.AsyncTask.InsertUserTask;
import com.example.palplants.AsyncTask.VerifyUserLoginTask;
import com.example.palplants.R;
import com.example.palplants.Utility.InsertUserCallback;
import com.example.palplants.Utility.VerifyUserLoginCallback;
import com.google.gson.Gson;

import pojosbotanica.Usuario;

public class SignupTabFragment extends Fragment implements InsertUserCallback, VerifyUserLoginCallback {

    private EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword;
    private Button buttonRegister;
    private Usuario usuarioRegistro = new Usuario();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_tab, container, false);

        // Inicializa las vistas
        editTextUsername = view.findViewById(R.id.usernameRegister);
        editTextEmail = view.findViewById(R.id.emailRegister);
        editTextPassword = view.findViewById(R.id.passwordRegister);
        editTextConfirmPassword = view.findViewById(R.id.confirmPasswordRegister);
        buttonRegister = view.findViewById(R.id.registerButton);

        // Configura el click listener para el botón de registro
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        return view;
    }

    // Método para registrar al usuario
    private void registerUser() {
        // Obtiene los valores de los campos de entrada
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        // Verifica si el nombre de usuario contiene caracteres no permitidos
        if (!username.matches("[a-zA-Z0-9]+")) {
            editTextUsername.setError("Nombre de usuario debe contener solo caracteres alfanuméricos");
            editTextUsername.requestFocus();
            usuarioRegistro = new Usuario();
            return;
        }

        // Verifica si el correo electrónico es válido
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Correo electrónico inválido");
            editTextEmail.requestFocus();
            usuarioRegistro = new Usuario();
            return;
        }

        // Verifica si la contraseña tiene al menos 8 caracteres
        if (password.length() < 8) {
            editTextPassword.setError("Contraseña debe tener al menos 8 caracteres");
            editTextPassword.requestFocus();
            usuarioRegistro = new Usuario();
            return;
        }

        // Verifica si las contraseñas coinciden
        if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.setError("Las contraseñas no coinciden");
            editTextConfirmPassword.requestFocus();
            usuarioRegistro = new Usuario();
            return;
        }

        // Crea un objeto Usuario con los datos ingresados por el usuario
        usuarioRegistro.setNombreUsuario(username);
        usuarioRegistro.setEmail(email);
        usuarioRegistro.setContrasena(password);

        // Inicia una tarea asincrónica para insertar el usuario en la base de datos
        new InsertUserTask(this).execute(usuarioRegistro);
    }

    // Método de devolución de llamada para manejar el resultado de la inserción del usuario
    @Override
    public void onInsertUserResult(boolean success) {
        if (success) {
            // Si el usuario se registró correctamente, se inicia una tarea asincrónica para verificar el inicio de sesión
            new VerifyUserLoginTask(this).execute(usuarioRegistro.getNombreUsuario());
        } else {
            // Si hubo un error al registrar al usuario, se muestra un mensaje de error
            Toast.makeText(getActivity(), "Error al registrar usuario", Toast.LENGTH_SHORT).show();
        }
    }

    // Método de devolución de llamada para manejar el resultado de la verificación del inicio de sesión del usuario
    @Override
    public void onVerifyUserLoginResult(Usuario user) {
        if (user != null) {
            // Si el usuario se verificó correctamente, se guarda en SharedPreferences
            saveUserToSharedPreferences(user);
            // Se inicia la actividad YourPlantsActivity
            Intent intent = new Intent(getActivity(), YourPlantsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            // Se finaliza la actividad actual
            getActivity().finish();
        } else {
            // Si hubo un error al verificar el usuario, se muestra un mensaje de error
            Toast.makeText(getActivity(), "Error al verificar usuario", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para guardar el usuario en SharedPreferences
    private void saveUserToSharedPreferences(Usuario usuario) {
        if (usuario != null) {
            // Serializa el usuario a JSON
            Gson gson = new Gson();
            String usuarioJson = gson.toJson(usuario);

            // Guarda el usuario en SharedPreferences
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // Elimina cualquier usuario anterior guardado en SharedPreferences
            String usuarioGuardado = sharedPreferences.getString("user", "");
            if (!usuarioGuardado.isEmpty()) {
                editor.remove("user");
                Log.d("UserInfo", "Usuario anterior eliminado de SharedPreferences");
            }

            // Guarda el nuevo usuario en SharedPreferences
            editor.putString("user", usuarioJson);
            editor.apply();

            // Verifica si el usuario se guardó correctamente
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
