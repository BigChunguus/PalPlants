package com.example.palplants.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.palplants.Utility.VerifyUserLoginCallback;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Usuario;

public class VerifyUserLoginTask extends AsyncTask<String, Void, Usuario> {
    private VerifyUserLoginCallback callback;

    public VerifyUserLoginTask(VerifyUserLoginCallback callback) {
        this.callback = callback;
    }

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

    @Override
    protected void onPostExecute(Usuario resultado) {
        if (callback != null) {
            callback.onVerifyUserLoginResult(resultado);
            Log.e("UsuarioTask", resultado + "");
        }
    }
}
