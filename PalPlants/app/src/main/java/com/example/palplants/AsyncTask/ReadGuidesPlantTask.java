package com.example.palplants.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.palplants.Adapter.GuideAdapter;

import java.util.ArrayList;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Guia;

public class ReadGuidesPlantTask extends AsyncTask<Void, Void, ArrayList<Guia>> {

    private int plantIdToCheck;
    private RecyclerView mRecyclerView;
    private Context mContext;

    public ReadGuidesPlantTask(Context context, int plantIdToCheck, RecyclerView recyclerView) {
        mContext = context;
        this.plantIdToCheck = plantIdToCheck;
        mRecyclerView = recyclerView;
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
        }
    }
}
