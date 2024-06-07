package com.example.palplants.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.example.palplants.Adapter.GuideAdapter;

import java.util.ArrayList;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Guia;

// AsyncTask para leer las guías relacionadas con una planta específica.
// Recibe el contexto de la aplicación, el ID de la planta, el ID del usuario, el RecyclerView donde se mostrarán las guías,
// y los botones para agregar guías y desplegar menú.
public class ReadGuidesPlantTask extends AsyncTask<Void, Void, ArrayList<Guia>> {

    private int plantIdToCheck;
    private int userIdToCheck;
    private Guia guiaUsuario;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private ImageButton mButtonAddGuide;
    private ImageButton mButtonDropdownMenu;
    private OnGuiaUsuarioReceivedListener listener;

    // Constructor
    public ReadGuidesPlantTask(Context context, int plantIdToCheck, int userIdToCheck, RecyclerView recyclerView, ImageButton buttonAddGuide, ImageButton buttonDropdownMenu) {
        this.mContext = context;
        this.plantIdToCheck = plantIdToCheck;
        this.userIdToCheck = userIdToCheck;
        this.mRecyclerView = recyclerView;
        this.mButtonAddGuide = buttonAddGuide;
        this.mButtonDropdownMenu = buttonDropdownMenu;
    }

    // Método doInBackground
    // Este método se ejecuta en segundo plano y realiza la lectura de las guías relacionadas con la planta.
    @Override
    protected ArrayList<Guia> doInBackground(Void... voids) {
        try {
            BotanicaCC botanicaCC = new BotanicaCC();
            return botanicaCC.leerGuias(plantIdToCheck);
        } catch (ExcepcionBotanica ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // Método onPostExecute
    // Este método se ejecuta después de que doInBackground ha finalizado.
    @Override
    protected void onPostExecute(ArrayList<Guia> listaGuias) {
        super.onPostExecute(listaGuias);

        if (listaGuias != null && !listaGuias.isEmpty()) {
            ArrayList<Guia> userGuides = new ArrayList<>();
            ArrayList<Guia> otherGuides = new ArrayList<>();

            for (Guia guia : listaGuias) {
                if (guia.getUsuarioId().getUsuarioID() == userIdToCheck) {
                    guiaUsuario = guia;
                    userGuides.add(guia);
                } else {
                    otherGuides.add(guia);
                }
            }

            if (listener != null) {
                listener.onGuiaUsuarioReceived(guiaUsuario);
            }

            if (!userGuides.isEmpty()) {
                mButtonAddGuide.setVisibility(View.INVISIBLE);
                mButtonAddGuide.setEnabled(false);
                mButtonDropdownMenu.setVisibility(View.VISIBLE);
                mButtonDropdownMenu.setEnabled(true);
            } else {
                mButtonAddGuide.setVisibility(View.VISIBLE);
                mButtonAddGuide.setEnabled(true);
                mButtonDropdownMenu.setVisibility(View.INVISIBLE);
                mButtonDropdownMenu.setEnabled(false);
            }

            userGuides.addAll(otherGuides);

            GuideAdapter adapter = new GuideAdapter(mContext, userGuides);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        }
    }

    // Método para establecer un listener para recibir la guía del usuario.
    public void setOnGuiaUsuarioReceivedListener(OnGuiaUsuarioReceivedListener listener) {
        this.listener = listener;
    }

    // Interfaz para definir el método de callback para recibir la guía del usuario.
    public interface OnGuiaUsuarioReceivedListener {
        void onGuiaUsuarioReceived(Guia guia);
    }
}
