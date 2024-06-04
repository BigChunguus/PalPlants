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

public class ReadGuidesPlantTask extends AsyncTask<Void, Void, ArrayList<Guia>> {

    private int plantIdToCheck;
    private int userIdToCheck;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private ImageButton mButtonAddGuide;

    public ReadGuidesPlantTask(Context context, int plantIdToCheck, int userIdToCheck, RecyclerView recyclerView, ImageButton buttonAddGuide) {
        mContext = context;
        this.plantIdToCheck = plantIdToCheck;
        this.userIdToCheck = userIdToCheck;
        mRecyclerView = recyclerView;
        mButtonAddGuide = buttonAddGuide;
    }

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

    @Override
    protected void onPostExecute(ArrayList<Guia> listaGuias) {
        super.onPostExecute(listaGuias);

        if (listaGuias != null && !listaGuias.isEmpty()) {
            GuideAdapter adapter = new GuideAdapter(mContext, listaGuias);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

            for (Guia guia : listaGuias) {
                if (guia.getUsuarioId().getUsuarioID() == userIdToCheck) {
                    mButtonAddGuide.setVisibility(View.INVISIBLE);
                    mButtonAddGuide.setEnabled(false);
                    break;
                } else {
                    mButtonAddGuide.setVisibility(View.VISIBLE);
                    mButtonAddGuide.setEnabled(true);
                }
            }
        }
    }
}
