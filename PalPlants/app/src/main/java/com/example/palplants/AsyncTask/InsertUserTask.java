package com.example.palplants.AsyncTask;

import android.os.AsyncTask;

import com.example.palplants.Utility.InsertUserCallback;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Usuario;

public class InsertUserTask extends AsyncTask<Usuario, Void, Boolean> {
    private InsertUserCallback callback;

    public InsertUserTask(InsertUserCallback callback) {
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(Usuario... params) {
        Usuario usuario = params[0];
        try {
            BotanicaCC bcc = new BotanicaCC();
            int resultado = bcc.insertarUsuario(usuario);
            return resultado == 1;
        } catch (ExcepcionBotanica ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (callback != null) {
            callback.onInsertUserResult(success);
        }
    }
}
