package com.example.palplants.AsyncTask;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Guia;
import pojosbotanica.Resena;

public class ModifyReviewTask extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private Resena resena;
    private Dialog dialog;

    public ModifyReviewTask(Context context, Resena resena, Dialog dialog) {
        this.context = context;
        this.resena = resena;
        this.dialog = dialog;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            BotanicaCC botanicaCC = new BotanicaCC();
            botanicaCC.modificarResena(resena.getResenaId(), resena);
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
