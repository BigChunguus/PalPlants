package com.example.palplants.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.palplants.Utility.FindPlantRegisteredCallback;

import java.util.ArrayList;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Planta;
import pojosbotanica.Usuario;

public class FindPlantRegisteredTask extends AsyncTask<Void, Void, Boolean> {
    private Usuario usuario;
    private int plantIdToCheck;
    private FindPlantRegisteredCallback callback;

    public FindPlantRegisteredTask(Usuario usuario, int plantIdToCheck, FindPlantRegisteredCallback callback) {
        this.usuario = usuario;
        this.plantIdToCheck = plantIdToCheck;
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        ArrayList<Planta> listaPlantas;
        String nombreUsuario = usuario.getNombreUsuario();

        try {
            BotanicaCC botanicaCC = new BotanicaCC();
            listaPlantas = botanicaCC.leerUsuariosPlantas(nombreUsuario);
        } catch (ExcepcionBotanica ex) {
            ex.printStackTrace();
            return false; // En caso de excepción, retorna false
        }

        for (Planta planta : listaPlantas) {
            if (planta.getPlantaId() == plantIdToCheck) {
                return true; // La planta fue encontrada
            }
        }
        return false; // La planta no fue encontrada
    }

    @Override
    protected void onPostExecute(Boolean plantaEncontrada) {
        // Llama al callback con el resultado
        if (callback != null) {
            callback.onPlantChecked(plantaEncontrada);
        }
    }
}
