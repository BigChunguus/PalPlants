package com.example.palplants.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.palplants.Utility.VerifyUserLoginCallback;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Usuario;

// AsyncTask para verificar el inicio de sesión del usuario en la base de datos.
public class VerifyUserLoginTask extends AsyncTask<String, Void, Usuario> {
    private VerifyUserLoginCallback callback;

    // Constructor
    public VerifyUserLoginTask(VerifyUserLoginCallback callback) {
        this.callback = callback;
    }

    // Método doInBackground
    // Este método se ejecuta en segundo plano y verifica si el usuario está registrado en la base de datos.
    @Override
    protected Usuario doInBackground(String... params) {
        String username = params[0];
        try {
            BotanicaCC bcc = new BotanicaCC();
            return bcc.leerUsuario(username);
        } catch (ExcepcionBotanica ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // Método onPostExecute
    // Este método se ejecuta después de que doInBackground ha finalizado.
    @Override
    protected void onPostExecute(Usuario resultado) {
        // Llama al callback con el resultado de la verificación del inicio de sesión del usuario
        if (callback != null) {
            callback.onVerifyUserLoginResult(resultado);
            Log.e("UsuarioTask", resultado + "");
        }
    }
}
