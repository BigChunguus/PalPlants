package com.example.palplants.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Resena;

// AsyncTask encargado de modificar una reseña existente en la base de datos del servidor.
// Recibe como parámetros el contexto y la reseña que se va a modificar.
public class ModifyReviewTask extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private Resena resena;

    // Constructor
    public ModifyReviewTask(Context context, Resena resena) {
        this.context = context;
        this.resena = resena;
    }

    // Método doInBackground
    // Este método se ejecuta en segundo plano y realiza la modificación de la reseña en la base de datos.
    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            // Modificar la reseña en la base de datos
            BotanicaCC botanicaCC = new BotanicaCC();
            botanicaCC.modificarResena(resena.getResenaId(), resena);
            return true; // Indicar éxito
        } catch (ExcepcionBotanica e) {
            // Capturar excepción si ocurre
            e.printStackTrace();
            return false; // Indicar fallo
        }
    }
}
