package com.example.palplants.AsyncTask;

import android.os.AsyncTask;

import com.example.palplants.Utility.VerifyUserCallback;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Usuario;

// AsyncTask para verificar la existencia de un usuario en la base de datos.
public class VerifyUserTask extends AsyncTask<String, Void, Boolean> {
    private VerifyUserCallback callback;

    // Constructor
    public VerifyUserTask(VerifyUserCallback callback) {
        this.callback = callback;
    }

    // Método doInBackground
    // Este método se ejecuta en segundo plano y verifica si el usuario existe en la base de datos.
    @Override
    protected Boolean doInBackground(String... params) {
        String username = params[0];
        try {
            BotanicaCC bcc = new BotanicaCC();
            Usuario usuario = bcc.leerUsuario(username);
            return usuario != null && usuario.getNombreUsuario() != null;
        } catch (ExcepcionBotanica ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Método onPostExecute
    // Este método se ejecuta después de que doInBackground ha finalizado.
    @Override
    protected void onPostExecute(Boolean result) {
        // Llama al callback con el resultado de la verificación del usuario
        if (callback != null) {
            callback.onVerifyUserResult(result);
        }
    }
}
