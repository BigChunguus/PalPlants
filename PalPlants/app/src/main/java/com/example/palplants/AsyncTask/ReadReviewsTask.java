package com.example.palplants.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageButton;

import com.example.palplants.Adapter.ReviewAdapter;

import java.util.ArrayList;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Guia;
import pojosbotanica.Resena;

public class ReadReviewsTask extends AsyncTask<Void, Void, ArrayList<Resena>> {

    private Context context;
    private int guideIdToCheck;
    private int userIdToCheck;
    private RecyclerView recyclerView;
    private ImageButton buttonAddReview;
    private ArrayList<Resena> resenaList;
    private ReviewAdapter adapter;

    public ReadReviewsTask(Context context, int guideIdToCheck, int userIdToCheck, RecyclerView recyclerView, ImageButton buttonAddReview, ArrayList<Resena> resenaList, ReviewAdapter adapter) {
        this.context = context;
        this.guideIdToCheck = guideIdToCheck;
        this.userIdToCheck = userIdToCheck;
        this.recyclerView = recyclerView;
        this.buttonAddReview = buttonAddReview;
        this.resenaList = resenaList;
        this.adapter = adapter;
    }

    @Override
    protected ArrayList<Resena> doInBackground(Void... voids) {
        try {
            BotanicaCC botanicaCC = new BotanicaCC();
            return botanicaCC.leerResenas(guideIdToCheck);
        } catch (ExcepcionBotanica ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Resena> listaResenas) {
        super.onPostExecute(listaResenas);

        if (listaResenas != null && !listaResenas.isEmpty()) {
            // Configuración del adaptador de reseñas
            adapter = new ReviewAdapter(context, listaResenas);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            // Verificar si el usuario ya dejó una reseña
            for (Resena resena : listaResenas) {
                if (resena.getUsuarioId().getUsuarioID() == userIdToCheck) {
                    buttonAddReview.setVisibility(View.INVISIBLE);
                    buttonAddReview.setEnabled(false);
                    break;
                } else {
                    buttonAddReview.setVisibility(View.VISIBLE);
                    buttonAddReview.setEnabled(true);
                }
            }
        } else {
            // La lista de reseñas está vacía, por lo que el botón de agregar reseña debe estar visible y habilitado
            buttonAddReview.setVisibility(View.VISIBLE);
            buttonAddReview.setEnabled(true);
        }
    }


}
