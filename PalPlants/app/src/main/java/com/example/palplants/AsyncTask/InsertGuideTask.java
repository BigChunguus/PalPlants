package com.example.palplants.AsyncTask;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Guia;

public class InsertGuideTask extends AsyncTask<Void, Void, Void> {

    private Context mContext;
    private Guia mGuia;
    private Dialog mDialog;
    private Exception mException;

    public InsertGuideTask(Context context, Guia guia, Dialog dialog) {
        mContext = context;
        mGuia = guia;
        mDialog = dialog;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            BotanicaCC botanicaCC = new BotanicaCC();
            botanicaCC.insertarGuia(mGuia);
        } catch (ExcepcionBotanica ex) {
            mException = ex;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        // Cerrar el diálogo si está mostrándose
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }

        // Recrear la actividad
        ((Activity) mContext).recreate();
    }
}
