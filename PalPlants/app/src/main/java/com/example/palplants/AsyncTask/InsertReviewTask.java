package com.example.palplants.AsyncTask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Resena;

// AsyncTask encargado de insertar una nueva reseña en la base de datos del servidor.
// Después de la inserción, recrea la actividad actual para reflejar los cambios.
public class InsertReviewTask extends AsyncTask<Void, Void, Void> {

    private Context mContext;
    private Resena mResena;
    private Exception mException;

    // Constructor
    public InsertReviewTask(Context context, Resena resena) {
        mContext = context;
        mResena = resena;
    }

    // Método doInBackground
    // Este método se ejecuta en segundo plano y realiza la inserción de la reseña en la base de datos.
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // Registrar la información de la reseña
            Log.e("Reseñas", mResena.toString());
            // Insertar la reseña en la base de datos
            BotanicaCC botanicaCC = new BotanicaCC();
            botanicaCC.insertarResena(mResena);

        } catch (ExcepcionBotanica ex) {
            // Capturar excepción si ocurre
            mException = ex;
        }
        return null;
    }

    // Método onPostExecute
    // Este método se ejecuta en el hilo principal después de que doInBackground haya terminado.
    // Recrea la actividad actual para reflejar los cambios realizados por la inserción de la reseña.
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        // Recrear la actividad
        ((Activity) mContext).recreate();
    }
}
