package com.example.palplants.Activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.palplants.R;

import pojosbotanica.Guia;
import pojosbotanica.Usuario;

public class GuidesActivity extends AppCompatActivity {

    private TextView titleTextView, contentGuideTextView, userNameTextView;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        // Obtener referencias a los elementos de la interfaz de usuario
        titleTextView = findViewById(R.id.title);
        contentGuideTextView = findViewById(R.id.contentGuide);

        userNameTextView = findViewById(R.id.userName);
        ratingBar = findViewById(R.id.ratingBar);

        // Obtener la Guia pasada desde el adaptador
        Guia guia = (Guia) getIntent().getSerializableExtra("GUIDE");

        // Establecer los datos en los campos correspondientes
        titleTextView.setText(guia.getTitulo());
        contentGuideTextView.setText(guia.getContenido());


        Usuario usuario = guia.getUsuarioId();
        userNameTextView.setText(usuario.getNombreUsuario());

        // Convertir el valor Double a float y establecerlo en el RatingBar
        ratingBar.setRating(guia.getCalificacionMedia().floatValue());
    }
}
