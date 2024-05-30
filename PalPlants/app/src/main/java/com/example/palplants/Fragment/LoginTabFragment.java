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
        View view = inflater.inflate(R.layout.fragment_login_tab, container, false);

        editTextUsername = view.findViewById(R.id.usernameLogin);
        editTextPassword = view.findViewById(R.id.passwordLogin);
        buttonLogin = view.findViewById(R.id.loginButton);
        textError = view.findViewById(R.id.textError);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
                Matcher matcher = pattern.matcher(username);
                Log.e("usuario", username + "");
                if (password.length() >= 8 && matcher.matches()) {
                    new VerifyUserLoginTask(LoginTabFragment.this).execute(username, password);
                } else {
                    if (password.length() < 8)
                        editTextPassword.setError("La contraseña debe tener min. 8 caracteres");
                    else
                        editTextUsername.setError("El nombre no debe contener caracteres especiales");
                }
            }

        });

        return view;
    }

    @Override
    public void onVerifyUserLoginResult(Usuario resultado) {
        Log.e("Usuario", resultado != null ? resultado.toString() : "null");
        if (resultado != null && resultado.getEmail() != null) {
            String password = editTextPassword.getText().toString();
            if (password.equals(resultado.getContrasena())) {
                Log.d("email", resultado.getEmail());
                saveUserToSharedPreferences(resultado);
                Intent intent = new Intent(getActivity(), YourPlantsActivity.class);
                startActivity(intent);
                getActivity().finish();
            } else {
                editTextPassword.setText("");
                textError.setText("Usuario o contraseña incorrectos");
            }
        } else {
            //if (resultado != null || resultado.getEmail() == null || resultado.getContrasena() == null || resultado.getNombreUsuario() == null || resultado.getInteres() == null)
              //  textError.setText("Usuario no encontrado");
            //else
              //  Toast.makeText(getActivity(), "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserToSharedPreferences(Usuario usuario) {
        if (usuario != null) {
            Log.d("UserInfo", "Usuario recibido: " + usuario.toString());
            Gson gson = new Gson();
            String usuarioJson = gson.toJson(usuario);
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user", usuarioJson);
            editor.apply();
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
