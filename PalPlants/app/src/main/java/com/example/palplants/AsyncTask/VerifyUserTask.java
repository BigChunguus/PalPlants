package com.example.palplants.AsyncTask;

import android.os.AsyncTask;

import com.example.palplants.Utility.VerifyUserCallback;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Usuario;

public class VerifyUserTask extends AsyncTask<String, Void, Boolean> {
    private VerifyUserCallback callback;

    public VerifyUserTask(VerifyUserCallback callback) {
        this.callback = callback;
    }

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

    @Override
    protected void onPostExecute(Boolean result) {
        if (callback != null) {
            callback.onVerifyUserResult(result);
        }
    }
}
