package com.example.palplants.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Usuario;

public class DeleteGuideUserTask extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private Usuario usuario;

    public DeleteGuideUserTask(Context context, Usuario usuario) {
        this.context = context;
        this.usuario = usuario;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            BotanicaCC botanicaCC = new BotanicaCC();
            int cambios = botanicaCC.eliminarGuia(usuario.getUsuarioID());
            return cambios == 1;
        } catch (ExcepcionBotanica e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {

        } else {

        }
    }
}
