package com.example.palplants.AsyncTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import botanicacc.BotanicaCC;
import pojosbotanica.Usuario;

public class ModifyUserTask extends AsyncTask<Usuario, Void, Void> {
    private Context context;

    public ModifyUserTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Usuario... usuarios) {
        if (usuarios.length > 0) {
            Usuario usuario = usuarios[0];
            String nombreUsuario = null;
            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            String usuarioJson = sharedPreferences.getString("user", "");

            if (!usuarioJson.isEmpty()) {
                Gson gson = new Gson();
                Usuario usuarioAux = gson.fromJson(usuarioJson, Usuario.class);
                nombreUsuario = usuarioAux.getNombreUsuario();
            } else {
                Log.e("UserInfo", "Error: No se pudo obtener el usuario desde SharedPreferences");
            }
            try {
                BotanicaCC botanicaCC = new BotanicaCC();
                Log.e("Cambios", nombreUsuario + usuario.toString());
                botanicaCC.modificarUsuario(nombreUsuario, usuario);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}