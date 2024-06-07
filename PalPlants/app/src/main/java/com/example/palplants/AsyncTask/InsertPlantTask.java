package com.example.palplants.AsyncTask;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.palplants.Activity.PlantsActivity;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Usuario;

// Este AsyncTask se encarga de insertar una nueva planta asociada a un usuario en la base de datos del servidor.
// Después de la inserción, recrea la actividad PlantsActivity para reflejar los cambios.
public class InsertPlantTask extends AsyncTask<Void, Void, Integer> {

    private Usuario usuario;
    private int plantIdToCheck;
    private PlantsActivity activity;

    // Constructor
    public InsertPlantTask(PlantsActivity activity, Usuario usuario, int plantIdToCheck) {
        this.activity = activity;
        this.usuario = usuario;
        this.plantIdToCheck = plantIdToCheck;
    }

    // Método doInBackground
    // Este método se ejecuta en segundo plano y realiza la inserción de la planta en la base de datos.
    @Override
    protected Integer doInBackground(Void... voids) {
        int usuarioId = usuario.getUsuarioID();
        try {
            BotanicaCC botanicaCC = new BotanicaCC();
            return botanicaCC.insertarUsuarioPlanta(usuarioId, plantIdToCheck);
        } catch (ExcepcionBotanica ex) {
            ex.printStackTrace();
            return -1; // Indicar error
        }
    }

    // Método onPostExecute
    // Este método se ejecuta en el hilo principal después de que doInBackground haya terminado.
    // Muestra un mensaje de éxito o error y recrea la actividad para reflejar los cambios.
    @Override
    protected void onPostExecute(Integer registrosAfectados) {
        super.onPostExecute(registrosAfectados);
        if (registrosAfectados != null && registrosAfectados == 1) {
            // Éxito: mostrar mensaje y recrear la actividad
            Toast.makeText(activity, "Planta insertada correctamente", Toast.LENGTH_SHORT).show();
            activity.recreate();
        } else {
            // Error al insertar la planta: mostrar mensaje de error
            Toast.makeText(activity, "Error al insertar la planta", Toast.LENGTH_SHORT).show();
        }
    }
}
