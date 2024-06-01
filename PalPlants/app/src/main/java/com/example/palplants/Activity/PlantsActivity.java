package com.example.palplants.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
//import com.example.palplants.AsyncTask.InsertGuideTask;
import com.example.palplants.AsyncTask.FindPlantRegisteredTask;
import com.example.palplants.AsyncTask.InsertPlantTask;
import com.example.palplants.AsyncTask.ReadGuidesPlantTask;
import com.example.palplants.R;
import com.google.gson.Gson;

import botanicacc.BotanicaCC;
import pojosbotanica.Guia;
import pojosbotanica.Planta;
import pojosbotanica.Usuario;

public class PlantsActivity extends AppCompatActivity {

    private int plantIdToCheck;
    private Planta plantaSeleccionada = new Planta();
    private ImageView imagePlant;
    private ImageButton buttonAdd, buttonAddGuide;
    private TextView comunName, cientificName, description, specificCare;
    private Usuario usuario;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        int themeId = preferences.getInt("current_theme", R.style.Theme_App_Light_NoActionBar);
        setTheme(themeId);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants);

        imagePlant = findViewById(R.id.imagePlant);
        comunName = findViewById(R.id.comunName);
        cientificName = findViewById(R.id.cientificName);
        description = findViewById(R.id.descriptionPlant);
        specificCare = findViewById(R.id.specificCare);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAddGuide = findViewById(R.id.buttonAddGuide);
        recyclerView = findViewById(R.id.recyclerView);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String usuarioJson = sharedPreferences.getString("user", "");
        if (!usuarioJson.isEmpty()) {
            Gson gson = new Gson();
            usuario = gson.fromJson(usuarioJson, Usuario.class);
        }

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ejecutar tarea asincrónica para insertar la planta
                new InsertPlantTask(PlantsActivity.this, usuario, plantIdToCheck).execute();
            }
        });

        buttonAddGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddGuideDialog();
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("PLANT")) {
            plantaSeleccionada = (Planta) intent.getSerializableExtra("PLANT");
            // Cargar la imagen de la planta utilizando Glide
            Glide.with(this)
                    .load(plantaSeleccionada.getImagen())
                    .placeholder(R.drawable.placeholder_image)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .error(R.drawable.error_image)
                    .override(160, 160)
                    .into(imagePlant);

            // Asignar la información de la planta a los campos correspondientes
            comunName.setText(plantaSeleccionada.getNombreComunPlanta());
            cientificName.setText(plantaSeleccionada.getNombreCientificoPlanta());
            String plantDescription = plantaSeleccionada.getDescripcion();
            if (plantDescription != null && !plantDescription.isEmpty()) {
                description.setText(plantDescription);
            } else {
                description.setText("Sin descripción");
            }

            String plantSpecificCare = plantaSeleccionada.getCuidadosEspecificos();
            if (plantSpecificCare != null && !plantSpecificCare.isEmpty()) {
                specificCare.setText(plantSpecificCare);
            } else {
                specificCare.setText("Sin cuidados específicos");
            }
            plantIdToCheck = plantaSeleccionada.getPlantaId();

            new FindPlantRegisteredTask(usuario, plantIdToCheck, isPlantRegistered -> {
                // Manejar el resultado
                if (isPlantRegistered) {
                    buttonAdd.setVisibility(View.INVISIBLE);
                    buttonAdd.setEnabled(false);
                } else {
                    buttonAdd.setVisibility(View.VISIBLE);
                    buttonAdd.setEnabled(true);
                }
            }).execute();
            new ReadGuidesPlantTask(this, plantIdToCheck, usuario.getUsuarioID(), recyclerView, buttonAddGuide).execute();
        }
    }

    private void showAddGuideDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_insert_guide);

        EditText editTextTitle = dialog.findViewById(R.id.editTextTitle);
        EditText editTextContent = dialog.findViewById(R.id.editTextContent);
        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
        Button buttonPublish = dialog.findViewById(R.id.buttonPublish);

        // Ajustar el tamaño del diálogo
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        buttonPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String content = editTextContent.getText().toString();

                if (!title.isEmpty() && !content.isEmpty()) {
                    Guia guia = new Guia();
                    guia.setTitulo(title);
                    guia.setContenido(content);
                    guia.setPlantaId(plantaSeleccionada);
                    guia.setUsuarioId(usuario);
                    guia.setCalificacionMedia(null); // or any default value if necessary

                    //new InsertGuideTask(PlantsActivity.this, guia).execute();
                    dialog.dismiss();
                } else {
                    // Mostrar un mensaje de error
                }
            }
        });

        dialog.show();
    }

}
