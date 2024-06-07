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
import pojosbotanica.Resena;

// AsyncTask para leer las reseñas asociadas a una guía y mostrarlas en un RecyclerView.
// Recibe el contexto de la aplicación, el ID de la guía, el ID del usuario, el RecyclerView donde se mostrarán las reseñas,
// el botón para agregar una reseña, la lista de reseñas, el adaptador de reseñas y el botón de menú desplegable.
public class ReadReviewsTask extends AsyncTask<Void, Void, ArrayList<Resena>> {

    private Context context;
    private int guideIdToCheck;
    private int userIdToCheck;
    private RecyclerView recyclerView;
    private ImageButton buttonAddReview, mButtonDropdownMenu;
    private ArrayList<Resena> resenaList;
    private ReviewAdapter adapter;
    private OnResenaUsuarioReceivedListener listener;

    // Constructor
    public ReadReviewsTask(Context context, int guideIdToCheck, int userIdToCheck, RecyclerView recyclerView, ImageButton buttonAddReview, ArrayList<Resena> resenaList, ReviewAdapter adapter, ImageButton buttonDropdownMenu) {
        this.context = context;
        this.guideIdToCheck = guideIdToCheck;
        this.userIdToCheck = userIdToCheck;
        this.recyclerView = recyclerView;
        this.buttonAddReview = buttonAddReview;
        this.resenaList = resenaList;
        this.adapter = adapter;
        this.mButtonDropdownMenu = buttonDropdownMenu;
    }

    // Método doInBackground
    // Este método se ejecuta en segundo plano y realiza la lectura de las reseñas asociadas a la guía.
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

    // Método onPostExecute
    // Este método se ejecuta después de que doInBackground ha finalizado.
    @Override
    protected void onPostExecute(ArrayList<Resena> listaResenas) {
        super.onPostExecute(listaResenas);

        if (listaResenas != null && !listaResenas.isEmpty()) {
            ArrayList<Resena> userReviews = new ArrayList<>();
            ArrayList<Resena> otherReviews = new ArrayList<>();
            Resena userReview = null;

            for (Resena resena : listaResenas) {
                if (resena.getUsuarioId().getUsuarioID() == userIdToCheck) {
                    userReview = resena;
                    userReviews.add(resena);
                } else {
                    otherReviews.add(resena);
                }
            }

            if (listener != null) {
                listener.onResenaUsuarioReceived(userReview);
            }

            if (!userReviews.isEmpty()) {
                buttonAddReview.setVisibility(View.INVISIBLE);
                buttonAddReview.setEnabled(false);
                mButtonDropdownMenu.setVisibility(View.VISIBLE);
                mButtonDropdownMenu.setEnabled(true);
            } else {
                buttonAddReview.setVisibility(View.VISIBLE);
                buttonAddReview.setEnabled(true);
                mButtonDropdownMenu.setVisibility(View.INVISIBLE);
                mButtonDropdownMenu.setEnabled(false);
            }

            userReviews.addAll(otherReviews);

            ReviewAdapter adapter = new ReviewAdapter(context, userReviews);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            // La lista de reseñas está vacía, por lo que el botón de agregar reseña debe estar visible y habilitado
            buttonAddReview.setVisibility(View.VISIBLE);
            buttonAddReview.setEnabled(true);
        }
    }

    // Método para establecer un listener para recibir la reseña del usuario
    public void setOnResenaUsuarioReceivedListener(OnResenaUsuarioReceivedListener listener) {
        this.listener = listener;
    }

    // Interfaz para el listener de recepción de reseña del usuario
    public interface OnResenaUsuarioReceivedListener {
        void onResenaUsuarioReceived(Resena resena);
    }
}
