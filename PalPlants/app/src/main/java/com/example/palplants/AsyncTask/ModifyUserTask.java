package com.example.palplants.AsyncTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import botanicacc.BotanicaCC;
import pojosbotanica.Usuario;

// AsyncTask encargado de modificar la información de un usuario en la base de datos del servidor.
// Recibe como parámetro el contexto de la aplicación.
public class ModifyUserTask extends AsyncTask<Usuario, Void, Void> {
    private Context context;

    // Constructor
    public ModifyUserTask(Context context) {
        this.context = context;
    }

    // Método doInBackground
    // Este método se ejecuta en segundo plano y realiza la modificación del usuario en la base de datos.
    @Override
    protected Void doInBackground(Usuario... usuarios) {
        if (usuarios.length > 0) {
            // Obtener el primer usuario del array
            Usuario usuario = usuarios[0];
            String nombreUsuario = null;

            // Obtener el nombre de usuario actual del SharedPreferences
            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            String usuarioJson = sharedPreferences.getString("user", "");

            // Verificar si se pudo obtener el usuario del SharedPreferences
            if (!usuarioJson.isEmpty()) {
                Gson gson = new Gson();
                Usuario usuarioAux = gson.fromJson(usuarioJson, Usuario.class);
                nombreUsuario = usuarioAux.getNombreUsuario();
            } else {
                Log.e("UserInfo", "Error: No se pudo obtener el usuario desde SharedPreferences");
            }

            try {
                // Modificar el usuario en la base de datos
                BotanicaCC botanicaCC = new BotanicaCC();
                Log.e("Cambios", nombreUsuario + usuario.toString());
                botanicaCC.modificarUsuario(nombreUsuario, usuario);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Método onPostExecute
    // Este método se ejecuta después de que doInBackground ha finalizado.
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
