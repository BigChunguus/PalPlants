package com.example.palplants.AsyncTask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Guia;

// Este AsyncTask se encarga de insertar una nueva guía en la base de datos del servidor.
// Después de la inserción, recrea la actividad para reflejar los cambios.
public class InsertGuideTask extends AsyncTask<Void, Void, Void> {

    private Context mContext;
    private Guia mGuia;
    private Exception mException;

    // Constructor
    public InsertGuideTask(Context context, Guia guia) {
        mContext = context;
        mGuia = guia;
    }

    // Método doInBackground
    // Este método se ejecuta en segundo plano y realiza la inserción de la guía en la base de datos.
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

    // Método onPostExecute
    // Este método se ejecuta en el hilo principal después de que doInBackground haya terminado.
    // Recrea la actividad para reflejar los cambios realizados.
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        ((Activity) mContext).recreate();
    }
}
