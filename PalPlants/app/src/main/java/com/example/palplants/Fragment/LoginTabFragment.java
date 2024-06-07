package com.example.palplants.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.palplants.Activity.YourPlantsActivity;
import com.example.palplants.AsyncTask.VerifyUserLoginTask;
import com.example.palplants.R;
import com.example.palplants.Utility.VerifyUserLoginCallback;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pojosbotanica.Usuario;

public class LoginTabFragment extends Fragment implements VerifyUserLoginCallback {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private TextView textError;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla el diseño del fragmento de inicio de sesión
        View view = inflater.inflate(R.layout.fragment_login_tab, container, false);

        // Obtiene las referencias a los elementos de la interfaz de usuario
        editTextUsername = view.findViewById(R.id.usernameLogin);
        editTextPassword = view.findViewById(R.id.passwordLogin);
        buttonLogin = view.findViewById(R.id.loginButton);
        textError = view.findViewById(R.id.textError);

        // Configura el click listener para el botón de inicio de sesión
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene el nombre de usuario y la contraseña ingresados por el usuario
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Define un patrón para validar el nombre de usuario (solo letras, números y guiones bajos)
                Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
                Matcher matcher = pattern.matcher(username);

                // Verifica que la contraseña tenga al menos 8 caracteres y el nombre de usuario cumpla con el patrón
                if (password.length() >= 8 && matcher.matches()) {
                    // Si las credenciales son válidas, se inicia una tarea asincrónica para verificar el inicio de sesión
                    new VerifyUserLoginTask(LoginTabFragment.this).execute(username, password);
                } else {
                    // Si las credenciales no son válidas, se muestra un mensaje de error apropiado
                    if (password.length() < 8)
                        editTextPassword.setError("La contraseña debe tener al menos 8 caracteres");
                    else
                        editTextUsername.setError("El nombre de usuario no debe contener caracteres especiales");
                }
            }

        });

        return view;
    }

    // Método de devolución de llamada para manejar el resultado de la verificación del inicio de sesión del usuario
    @Override
    public void onVerifyUserLoginResult(Usuario resultado) {
        // Verifica si se recibió un usuario válido
        if (resultado != null && resultado.getEmail() != null) {
            // Comprueba si la contraseña ingresada coincide con la contraseña del usuario
            String password = editTextPassword.getText().toString();
            if (password.equals(resultado.getContrasena())) {
                // Si las credenciales son correctas, se guarda el usuario en SharedPreferences
                saveUserToSharedPreferences(resultado);
                // Se inicia la actividad de las plantas del usuario
                Intent intent = new Intent(getActivity(), YourPlantsActivity.class);
                startActivity(intent);
                // Se finaliza la actividad actual
                getActivity().finish();
            } else {
                // Si la contraseña no coincide, se borra y se muestra un mensaje de error
                editTextPassword.setText("");
                textError.setText("Usuario o contraseña incorrectos");
            }
        } else {
            // Si no se encontró el usuario o hubo un error al conectar con el servidor, se muestra un mensaje de error
            Toast.makeText(getActivity(), "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
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
            editor.putString("user", usuarioJson);
            editor.apply();
            // Verifica si el usuario se guardó correctamente
            String usuarioGuardado = sharedPreferences.getString("user", "");
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
