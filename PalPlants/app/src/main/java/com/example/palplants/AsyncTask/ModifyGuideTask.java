package com.example.palplants.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Guia;

// AsyncTask encargado de modificar una guía existente en la base de datos del servidor.
// Recibe como parámetros el contexto y la guía que se va a modificar.
public class ModifyGuideTask extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private Guia guia;

    // Constructor
    public ModifyGuideTask(Context context, Guia guia) {
        this.context = context;
        this.guia = guia;
    }

    // Método doInBackground
    // Este método se ejecuta en segundo plano y realiza la modificación de la guía en la base de datos.
    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            // Modificar la guía en la base de datos
            BotanicaCC botanicaCC = new BotanicaCC();
            botanicaCC.modificarGuia(guia.getGuiaId(), guia);
            return true; // Indicar éxito
        } catch (ExcepcionBotanica e) {
            // Capturar excepción si ocurre
            e.printStackTrace();
            return false; // Indicar fallo
        }
    }
}
