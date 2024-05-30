package com.example.palplants.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.palplants.Adapter.PlantAdapter;
import com.example.palplants.Adapter.PlantSearchAdapter;

import java.text.Normalizer;
import java.util.ArrayList;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Planta;
import pojosbotanica.Usuario;

public class SelectAllPlants extends AsyncTask<Void, Void, ArrayList<Planta>> {
    private Context context;
    private RecyclerView recyclerView;
    private TextView emptyMessage;
    private String searchText; // Agregar texto de búsqueda

    public SelectAllPlants(Context context, RecyclerView recyclerView, TextView emptyMessage, String searchText) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.emptyMessage = emptyMessage;
        this.searchText = searchText; // Inicializar texto de búsqueda
    }

    @Override
    protected ArrayList<Planta> doInBackground(Void... voids) {
        try {
            BotanicaCC bcc = new BotanicaCC();
            ArrayList<Planta> allPlants = bcc.leerPlantas();
            // Filtrar las plantas según el texto de búsqueda
            if (!TextUtils.isEmpty(searchText)) {
                ArrayList<Planta> filteredPlants = new ArrayList<>();
                String searchLower = searchText.toLowerCase();
                for (Planta planta : allPlants) {
                    // Buscar en el nombre común y científico, ignorando las tildes
                    String nombreComun = quitarTildes(planta.getNombreComunPlanta()).toLowerCase();
                    String nombreCientifico = quitarTildes(planta.getNombreCientificoPlanta()).toLowerCase();
                    if (nombreComun.contains(searchLower) || nombreCientifico.contains(searchLower)) {
                        filteredPlants.add(planta);
                    }
                }
                return filteredPlants;
            } else {
                return allPlants;
            }
        } catch (ExcepcionBotanica ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private String quitarTildes(String texto) {
        String normalized = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    @Override
    protected void onPostExecute(ArrayList<Planta> listaPlantas) {
        if (listaPlantas != null && !listaPlantas.isEmpty()) {
            PlantSearchAdapter adapter = new PlantSearchAdapter(listaPlantas, context);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(ViewGroup.VISIBLE);
            emptyMessage.setVisibility(ViewGroup.GONE);
        } else {
            recyclerView.setVisibility(ViewGroup.GONE);
            emptyMessage.setVisibility(ViewGroup.VISIBLE);
            emptyMessage.setText("Planta no encontrada");
        }
    }
}
