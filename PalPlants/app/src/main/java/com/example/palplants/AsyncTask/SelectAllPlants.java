package com.example.palplants.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.palplants.R;
import com.example.palplants.Adapter.PlantAdapter;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Planta;
import pojosbotanica.Usuario;

public class SelectAllPlants extends AsyncTask<Object, Void, ArrayList<Planta>> {
    private Context context;
    private RecyclerView recyclerView;
    private TextView emptyMessage;
    private Usuario usuarioPlantas;
    public SelectAllPlants(Context context, RecyclerView recyclerView, TextView emptyMessage) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.emptyMessage = emptyMessage;
    }

    @Override
    protected ArrayList<Planta> doInBackground(Object... params) {
        usuarioPlantas = (Usuario) params[0];
        String order = (String) params[1];
        try {
            Log.e("Orden", order);
            BotanicaCC bcc = new BotanicaCC();
            ArrayList<Planta> plantas = bcc.leerUsuariosPlantas(usuarioPlantas.getNombreUsuario());
            if (order != null && !order.isEmpty()) {
                if (order.equalsIgnoreCase("Ascendente")) {
                    plantas.sort(Comparator.comparing(Planta::getNombreComunPlanta));
                    Log.e("Orden", plantas.toString());
                } else if (order.equalsIgnoreCase("Descendente")) {
                    plantas.sort(Comparator.comparing(Planta::getNombreComunPlanta));
                    Collections.reverse(plantas);
                    Log.e("Orden", plantas.toString());
                }
            }

            return plantas;
        } catch (ExcepcionBotanica ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Planta> listaPlantas) {
        if (listaPlantas != null && !listaPlantas.isEmpty()) {
            PlantAdapter adapter = new PlantAdapter(listaPlantas, context,usuarioPlantas);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(ViewGroup.VISIBLE);
            emptyMessage.setVisibility(ViewGroup.GONE);
        } else {
            recyclerView.setVisibility(ViewGroup.GONE);
            emptyMessage.setVisibility(ViewGroup.VISIBLE);
            emptyMessage.setText("Aún no has añadido ninguna planta");
        }
    }
}
