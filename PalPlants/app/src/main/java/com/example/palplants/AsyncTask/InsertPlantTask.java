package com.example.palplants.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.palplants.Activity.PlantsActivity;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Usuario;

public class InsertPlantTask extends AsyncTask<Void, Void, Integer> {

    private Usuario usuario;
    private int plantIdToCheck;
    private PlantsActivity activity;

    public InsertPlantTask(PlantsActivity activity, Usuario usuario, int plantIdToCheck) {
        this.activity = activity;
        this.usuario = usuario;
        this.plantIdToCheck = plantIdToCheck;
    }

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

    @Override
    protected void onPostExecute(Integer registrosAfectados) {
        super.onPostExecute(registrosAfectados);
        if (registrosAfectados != null && registrosAfectados == 1) {
            // Éxito: mostrar mensaje o realizar alguna acción adicional si es necesario
            Toast.makeText(activity, "Planta insertada correctamente", Toast.LENGTH_SHORT).show();
            activity.recreate();
        } else {
            // Error al insertar la planta
            Toast.makeText(activity, "Error al insertar la planta", Toast.LENGTH_SHORT).show();
        }
    }
}
