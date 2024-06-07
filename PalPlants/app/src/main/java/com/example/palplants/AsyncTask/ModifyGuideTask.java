package com.example.palplants.AsyncTask;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Guia;

public class ModifyGuideTask extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private Guia guia;
    private Dialog dialog;

    public ModifyGuideTask(Context context, Guia guia, Dialog dialog) {
        this.context = context;
        this.guia = guia;
        this.dialog = dialog;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            BotanicaCC botanicaCC = new BotanicaCC();
            botanicaCC.modificarGuia(guia.getGuiaId(), guia);
            return true;
        } catch (ExcepcionBotanica e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {

        dialog.dismiss();
    }
}
