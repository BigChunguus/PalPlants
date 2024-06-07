package com.example.palplants.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Resena;

// Esta clase representa una tarea asíncrona para eliminar una reseña.
// Al eliminar una reseña, se comunica con el servidor para realizar los cambios necesarios en la base de datos remota.
// La eliminación de la reseña se realiza en segundo plano, en un hilo separado del hilo principal de la interfaz de usuario, para no bloquear la UI mientras se lleva a cabo la operación.
public class DeleteReviewUserTask extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private Resena resena;

    // Constructor de la clase DeleteReviewUserTask
    public DeleteReviewUserTask(Context context, Resena resena) {
        this.context = context;
        this.resena = resena;
    }

    // Método doInBackground: realiza la eliminación de la reseña en segundo plano
    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            // Crea una instancia de BotanicaCC para interactuar con el servidor
            BotanicaCC botanicaCC = new BotanicaCC();
            // Intenta eliminar la reseña y devuelve true si se realizó con éxito
            int cambios = botanicaCC.eliminarResena(resena.getResenaId());
            return cambios == 1;
        } catch (ExcepcionBotanica e) {
            e.printStackTrace();
            // En caso de excepción, devuelve false
            return false;
        }
    }
}
