package com.example.palplants;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class LoginTabFragment extends Fragment {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private TextView textErrorPassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_tab, container, false);

        editTextUsername = view.findViewById(R.id.usernameLogin);
        editTextPassword = view.findViewById(R.id.passwordLogin);
        buttonLogin = view.findViewById(R.id.loginButton);
        textErrorPassword = view.findViewById(R.id.textErrorPassword);
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
                    textErrorPassword.setText("La contraseña debe tener min. 8 caracteres");
                }
            }
        });

        return view;
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
                ex.printStackTrace();
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
                    Intent intent = new Intent(getActivity(), YourPlantsActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Maneja la situación de error aquí
                Toast.makeText(getActivity(), "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
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
}
