package com.example.palplants.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Guia;

public class DeleteGuideUserTask extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private Guia guia;

    public DeleteGuideUserTask(Context context, Guia guia) {
        this.context = context;
        this.guia = guia;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            int guiaId = guia.getGuiaId();
            Log.e("GuiaId", "Guia id: " + guiaId);
            BotanicaCC botanicaCC = new BotanicaCC();
            int cambios = botanicaCC.eliminarGuia(guiaId);
            return cambios == 1;
        } catch (ExcepcionBotanica e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            // Aquí puedes agregar código para manejar el éxito
        } else {
            // Aquí puedes agregar código para manejar el fallo
        }
    }
}
