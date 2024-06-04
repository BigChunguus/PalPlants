package com.example.palplants.AsyncTask;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Guia;
import pojosbotanica.Resena;

public class InsertReviewTask extends AsyncTask<Void, Void, Void> {

    private Context mContext;
    private Resena mResena;
    private Dialog mDialog;
    private Exception mException;

    public InsertReviewTask(Context context, Resena resena, Dialog dialog) {
        mContext = context;
        mResena = resena;
        mDialog = dialog;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Log.e("Reseñas", mResena.toString());
            BotanicaCC botanicaCC = new BotanicaCC();
            botanicaCC.insertarResena(mResena);

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
