package com.example.palplants;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private TextView textViewSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Usuario usuarioGuardado = deserializarUsuarioDeSharedPreferences();

        // Si hay datos guardados, ir directamente a YourPlantsActivity
        if (usuarioGuardado != null) {
            Intent intent = new Intent(LoginActivity.this, YourPlantsActivity.class);
            startActivity(intent);
            finish(); // Cerrar la actividad actual para que el usuario no pueda volver atrás
        }
        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.loginButton);
        textViewSignUp = findViewById(R.id.textViewSignUp);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
                Matcher matcher = pattern.matcher(username);
                if (password.length() >= 8 && matcher.matches()) {
                    new ConnectBotanicaTask().execute(username, password);
                } else {
                    Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Agregar OnClickListener al TextView
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private class ConnectBotanicaTask extends AsyncTask<String, Void, Usuario> {
        @Override
        protected Usuario doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            try {
                BotanicaCC bcc = new BotanicaCC();
                return bcc.leerUsuario(username);
            } catch (ExcepcionBotanica ex) {
                ex.printStackTrace(); // Maneja el error apropiadamente
                return null;
            }
        }

        @Override
        protected void onPostExecute(Usuario resultado) {
            if (resultado != null && resultado.getEmail() != null) {
                String password = editTextPassword.getText().toString();
                if (password.equals(resultado.getContrasena())) {
                    Log.d("email", resultado.getEmail());
                    saveUserToSharedPreferences(resultado);
                    Intent intent = new Intent(LoginActivity.this, YourPlantsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Maneja la situación de error aquí
                Toast.makeText(LoginActivity.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
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
                SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user", usuarioJson);
                editor.apply();

                // Verificamos que se guarda correctamente en las preferencias compartidas
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

    private Usuario deserializarUsuarioDeSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String usuarioJson = sharedPreferences.getString("user", "");

        if (!usuarioJson.isEmpty()) {
            Gson gson = new Gson();
            return gson.fromJson(usuarioJson, Usuario.class);
        }

        return null;
    }

}
