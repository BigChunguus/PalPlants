package com.example.palplants.AsyncTask;

import android.os.AsyncTask;
import java.util.List;

import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Insecto;
import botanicacc.BotanicaCC;
import pojosbotanica.Planta;

public class SelectAllInsectsPlantTask extends AsyncTask<Void, Void, List<Insecto>> {


    private OnInsectsLoadedListener listener;
    private Planta plant;

    public SelectAllInsectsPlantTask(Planta plant, OnInsectsLoadedListener listener) {
        this.plant = plant;
        this.listener = listener;

    }

    @Override
    protected List<Insecto> doInBackground(Void... voids) {
        // Realiza la llamada al método de botanicaCC para obtener la lista de insectos
        try {
            BotanicaCC bcc = new BotanicaCC();
            return bcc.leerInsectos(plant.getPlantaId());
        }catch (ExcepcionBotanica e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Insecto> insectList) {
        if (listener != null) {
            listener.onInsectsLoaded(insectList);
        }
    }

    // Interfaz para manejar la lista de insectos cargada
    public interface OnInsectsLoadedListener {
        void onInsectsLoaded(List<Insecto> insectList);
    }
}
