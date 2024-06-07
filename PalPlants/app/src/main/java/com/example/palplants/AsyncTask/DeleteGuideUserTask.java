package com.example.palplants.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Guia;

// Esta clase representa una tarea asíncrona para eliminar una guía de usuario.
// Al eliminar una guía, se comunica con el servidor para realizar los cambios necesarios en la base de datos remota.
// La eliminación de la guía se realiza en segundo plano, en un hilo separado del hilo principal de la interfaz de usuario, para no bloquear la UI mientras se lleva a cabo la operación.
public class DeleteGuideUserTask extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private Guia guia;

    // Constructor de la clase DeleteGuideUserTask
    public DeleteGuideUserTask(Context context, Guia guia) {
        this.context = context;
        this.guia = guia;
    }

    // Método doInBackground: realiza la eliminación de la guía en segundo plano
    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            // Obtiene el ID de la guía a eliminar
            int guiaId = guia.getGuiaId();
            Log.e("GuiaId", "Guia id: " + guiaId);
            // Crea una instancia de BotanicaCC para interactuar con el servidor
            BotanicaCC botanicaCC = new BotanicaCC();
            // Intenta eliminar la guía y devuelve true si se realizó con éxito
            int cambios = botanicaCC.eliminarGuia(guiaId);
            return cambios == 1;
        } catch (ExcepcionBotanica e) {
            e.printStackTrace();
            // En caso de excepción, devuelve false
            return false;
        }
    }
}
