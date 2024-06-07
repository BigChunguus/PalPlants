package com.example.palplants.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.palplants.Adapter.PlantAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Planta;
import pojosbotanica.Usuario;

// AsyncTask para seleccionar todas las plantas de un usuario de la base de datos y mostrarlas en un RecyclerView,
// con la opción de ordenarlas ascendente o descendentemente por el nombre común.
public class SelectAllPlantsByUser extends AsyncTask<Object, Void, ArrayList<Planta>> {
    private Context context;
    private RecyclerView recyclerView;
    private TextView emptyMessage;
    private Usuario usuarioPlantas;

    // Constructor
    public SelectAllPlantsByUser(Context context, RecyclerView recyclerView, TextView emptyMessage) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.emptyMessage = emptyMessage;
    }

    // Método doInBackground
    // Este método se ejecuta en segundo plano y selecciona todas las plantas del usuario de la base de datos,
    // ordenándolas ascendente o descendentemente según el parámetro de orden.
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

    // Método onPostExecute
    // Este método se ejecuta después de que doInBackground ha finalizado.
    @Override
    protected void onPostExecute(ArrayList<Planta> listaPlantas) {
        if (listaPlantas != null && !listaPlantas.isEmpty()) {
            // Crear un adaptador y establecerlo en el RecyclerView
            PlantAdapter adapter = new PlantAdapter(listaPlantas, context, usuarioPlantas);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(ViewGroup.VISIBLE);
            emptyMessage.setVisibility(ViewGroup.GONE);
        } else {
            // Mostrar un mensaje si el usuario no tiene plantas agregadas
            recyclerView.setVisibility(ViewGroup.GONE);
            emptyMessage.setVisibility(ViewGroup.VISIBLE);
            emptyMessage.setText("Aún no has añadido ninguna planta");
        }
    }
}
