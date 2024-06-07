package com.example.palplants.AsyncTask;

import android.os.AsyncTask;

import com.example.palplants.Utility.FindPlantRegisteredCallback;

import java.util.ArrayList;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Planta;
import pojosbotanica.Usuario;

// Este AsyncTask se encarga de buscar una planta específica registrada por un usuario en la base de datos del servidor.
// Después de la búsqueda, notifica el resultado a través de un callback.

public class FindPlantRegisteredTask extends AsyncTask<Void, Void, Boolean> {
    private Usuario usuario;
    private int plantIdToCheck;
    private FindPlantRegisteredCallback callback;

    // Constructor
    public FindPlantRegisteredTask(Usuario usuario, int plantIdToCheck, FindPlantRegisteredCallback callback) {
        this.usuario = usuario;
        this.plantIdToCheck = plantIdToCheck;
        this.callback = callback;
    }

    // Método doInBackground
    // Este método se ejecuta en segundo plano y busca la planta en la lista de plantas del usuario.
    // Devuelve true si la planta está registrada por el usuario, y false si no lo está.
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

    // Método onPostExecute
    // Este método se ejecuta en el hilo principal después de que doInBackground haya terminado.
    // Notifica al callback si la planta fue encontrada o no.
    @Override
    protected void onPostExecute(Boolean plantaEncontrada) {
        if (callback != null) {
            callback.onPlantChecked(plantaEncontrada);
        }
    }
}
