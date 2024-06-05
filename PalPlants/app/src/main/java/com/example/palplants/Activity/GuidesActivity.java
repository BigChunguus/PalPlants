package com.example.palplants.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.palplants.AsyncTask.InsertReviewTask;
import com.example.palplants.R;
import com.example.palplants.Adapter.ReviewAdapter;
import com.example.palplants.AsyncTask.ReadReviewsTask;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;

import pojosbotanica.Guia;
import pojosbotanica.Resena;
import pojosbotanica.Usuario;

public class GuidesActivity extends AppCompatActivity {

    private TextView titleTextView, contentGuideTextView, userNameTextView;
    private RatingBar ratingBar;
    private RecyclerView reviewRecyclerView;
    private ImageButton buttonAddReview;
    private ArrayList<Resena> resenaList;
    private ReviewAdapter reviewAdapter;
    private Usuario usuario;
    private Guia guia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        int themeId = preferences.getInt("current_theme", R.style.Theme_App_Light_NoActionBar);
        setTheme(themeId);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);


        // Obtener referencias a los elementos de la interfaz de usuario
        titleTextView = findViewById(R.id.title);
        contentGuideTextView = findViewById(R.id.contentGuide);
        userNameTextView = findViewById(R.id.userName);
        ratingBar = findViewById(R.id.ratingBar);
        reviewRecyclerView = findViewById(R.id.reviewRecyclerView);
        buttonAddReview = findViewById(R.id.buttonAddReview);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresa a la actividad anterior
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.NoneActivityButton);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.YourPlantsActivityButton) {
                    if (!(getApplicationContext() instanceof YourPlantsActivity)) {
                        startActivity(new Intent(getApplicationContext(), YourPlantsActivity.class));
                    } else {
                        // Si ya estamos en la actividad, la recreamos
                        recreate();
                    }
                    return true;
                } else if (item.getItemId() == R.id.SearchActivityButton) {
                    if (!(getApplicationContext() instanceof SearchActivity)) {
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                    } else {
                        // Si ya estamos en la actividad, la recreamos
                        recreate();
                    }
                    return true;
                }
                return false;
            }
        });

        // Obtener la Guia pasada desde el adaptador
        guia = (Guia) getIntent().getSerializableExtra("GUIDE");

        // Configurar el RecyclerView
        resenaList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(this, resenaList);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.setAdapter(reviewAdapter);

        // Obtener usuario de las SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String usuarioJson = sharedPreferences.getString("user", "");
        if (!usuarioJson.isEmpty()) {
            Gson gson = new Gson();
            usuario = gson.fromJson(usuarioJson, Usuario.class);
        }
        // En el método onCreate() de tu Activity GuidesActivity
        buttonAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un diálogo emergente
                Dialog dialog = new Dialog(GuidesActivity.this);
                dialog.setContentView(R.layout.dialog_insert_review);

                // Obtener referencias a los elementos del diálogo

                RatingBar ratingBar = dialog.findViewById(R.id.ratingBarReview);
                EditText editTextComment = dialog.findViewById(R.id.editTextComment);
                Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
                Button buttonPublish = dialog.findViewById(R.id.buttonPublish);

                // Configurar el botón Cancelar para cerrar el diálogo
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                // Configurar el botón Publicar para llamar a InsertReviewTask
                buttonPublish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Obtener la calificación y el comentario ingresados por el usuario
                        int rating = (int) ratingBar.getRating();
                        String comment = editTextComment.getText().toString();



                        // Crear una nueva Reseña con los datos proporcionados
                        Resena resena = new Resena();
                        resena.setCalificacion(rating);
                        resena.setComentario(comment);
                        resena.setUsuarioId(usuario);
                        resena.setGuiaId(guia);

                        // Ejecutar la tarea AsyncTask para insertar la reseña
                        new InsertReviewTask(GuidesActivity.this, resena, dialog).execute();
                    }
                });

                // Mostrar el diálogo
                dialog.show();
            }
        });



        // Establecer los datos en los campos correspondientes
        titleTextView.setText(guia.getTitulo());
        contentGuideTextView.setText(guia.getContenido());
        userNameTextView.setText(guia.getUsuarioId().getNombreUsuario());
        ratingBar.setRating(guia.getCalificacionMedia().floatValue());

        // Ejecutar el AsyncTask para cargar las reseñas
        new ReadReviewsTask(this, guia.getGuiaId(), usuario.getUsuarioID(), reviewRecyclerView, buttonAddReview, resenaList, reviewAdapter).execute();
    }
}
