package com.example.palplants.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.palplants.R;
import com.google.gson.Gson;

import java.util.ArrayList;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Guia;
import pojosbotanica.Planta;
import pojosbotanica.Usuario;

public class PlantsActivity extends AppCompatActivity {

    private int plantIdToCheck;
    private ImageView imagePlant;
    private LinearLayout guidesLayout;
    private ImageButton buttonAdd;
    private TextView comunName, cientificName, description, specificCare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        int themeId = preferences.getInt("current_theme", R.style.Theme_App_Light_NoActionBar);
        setTheme(themeId);
        setContentView(R.layout.activity_plants);

        imagePlant = findViewById(R.id.imagePlant);
        comunName = findViewById(R.id.comunName);
        cientificName = findViewById(R.id.cientificName);
        description = findViewById(R.id.descriptionPlant);
        specificCare = findViewById(R.id.specificCare);
        buttonAdd = findViewById(R.id.buttonAdd);
        guidesLayout = findViewById(R.id.guidesLayout);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ejecutar tarea asincrónica para insertar la planta
                new InsertPlantTask().execute();
            }
        });



        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("PLANT")) {
            Planta planta = (Planta) intent.getSerializableExtra("PLANT");
            // Cargar la imagen de la planta utilizando Glide
            Glide.with(this)
                    .load(planta.getImagen())
                    .placeholder(R.drawable.placeholder_image)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .error(R.drawable.error_image)
                    .override(160,160)
                    .into(imagePlant);


                // Asignar la información de la planta a los campos correspondientes
                comunName.setText(planta.getNombreComunPlanta());
                cientificName.setText(planta.getNombreCientificoPlanta());
                String plantDescription = planta.getDescripcion();
                if (plantDescription != null && !plantDescription.isEmpty()) {
                    description.setText(plantDescription);
                } else {
                    description.setText("Sin descripción");
                }

                String plantSpecificCare = planta.getCuidadosEspecificos();
                if (plantSpecificCare != null && !plantSpecificCare.isEmpty()) {
                    specificCare.setText(plantSpecificCare);
                } else {
                    specificCare.setText("Sin cuidados específicos");
                }
                plantIdToCheck = planta.getPlantaId();

                new findPlantRegisteredTask().execute();
            }

        new ReadGuidesPlantTask().execute();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        super.onCreate(savedInstanceState);
        }

    private class findPlantRegisteredTask extends AsyncTask<Void, Void, ArrayList<Planta>> {
        @Override
        protected ArrayList<Planta> doInBackground(Void... voids) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            String usuarioJson = sharedPreferences.getString("user", "");
            String nombreUsuario;
            ArrayList<Planta> listaPlantas = new ArrayList<>();
            if (!usuarioJson.isEmpty()) {
                Gson gson = new Gson();
                Usuario usuario = gson.fromJson(usuarioJson, Usuario.class);
                nombreUsuario = usuario.getNombreUsuario();

                try {
                    BotanicaCC botanicaCC = new BotanicaCC();
                    listaPlantas = botanicaCC.leerUsuariosPlantas(nombreUsuario);
                } catch (ExcepcionBotanica ex) {
                    ex.printStackTrace();
                    return null;
                }
            } else {
                Log.e("UserInfo", "Error: No se pudo obtener el usuario desde SharedPreferences");
            }
            return listaPlantas;
        }

        @Override
        protected void onPostExecute(ArrayList<Planta> listaPlantas) {
            super.onPostExecute(listaPlantas);

            if (listaPlantas != null) {
                boolean plantaEncontrada = false;
                for (Planta planta : listaPlantas) {
                    if (planta.getPlantaId() == plantIdToCheck) {
                        plantaEncontrada = true;
                        buttonAdd.setVisibility(View.INVISIBLE);
                        buttonAdd.setEnabled(false);
                        break;
                    }
                }
                if (!plantaEncontrada) {
                    buttonAdd.setElevation(25);
                    buttonAdd.setVisibility(View.VISIBLE);
                    buttonAdd.setEnabled(true);
                }
            } else {

            }

        }
    }
    private class InsertPlantTask extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... voids) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            String usuarioJson = sharedPreferences.getString("user", "");
            int usuarioId;
            if (!usuarioJson.isEmpty()) {
                Gson gson = new Gson();
                Usuario usuario = gson.fromJson(usuarioJson, Usuario.class);
                usuarioId = usuario.getUsuarioID();

                try {
                    BotanicaCC botanicaCC = new BotanicaCC();
                    // Supongamos que tienes la planta ya guardada en una variable "planta"
                    return botanicaCC.insertarUsuarioPlanta(usuarioId, plantIdToCheck);
                } catch (ExcepcionBotanica ex) {
                    ex.printStackTrace();
                    return -1; // Indicar error
                }
            } else {
                Log.e("UserInfo", "Error: No se pudo obtener el usuario desde SharedPreferences");
                return -1;
            }
        }

        @Override
        protected void onPostExecute(Integer registrosAfectados) {
            super.onPostExecute(registrosAfectados);
            if (registrosAfectados != null && registrosAfectados > 0) {
                // Éxito: mostrar mensaje o realizar alguna acción adicional si es necesario
                Toast.makeText(PlantsActivity.this, "Planta insertada correctamente", Toast.LENGTH_SHORT).show();
            } else {
                // Error al insertar la planta
                Toast.makeText(PlantsActivity.this, "Error al insertar la planta", Toast.LENGTH_SHORT).show();
            }

            recreate();
        }
    }
    private class ReadGuidesPlantTask extends AsyncTask<Void, Void, ArrayList<Guia>> {
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

            if(listaGuias != null && !listaGuias.isEmpty()){
                for (Guia nuevaGuia : listaGuias){

                    LinearLayout cardView = addGuiaCard(nuevaGuia);
                    guidesLayout.addView(cardView);
                }
            } else {
                LinearLayout emptyCardView = new LinearLayout(PlantsActivity.this);
                LinearLayout.LayoutParams layoutParamsRoot = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                layoutParamsRoot.setMargins(50,50,50,50);
                emptyCardView.setLayoutParams(layoutParamsRoot);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );
                layoutParams.gravity = Gravity.CENTER;
                TextView textView = new TextView(PlantsActivity.this);
                textView.setId(View.generateViewId());
                textView.setText("Aún no se han añadido guias");
                // Aplicar el LayoutParams al TextView
                textView.setLayoutParams(layoutParams);

                emptyCardView.addView(textView);


                guidesLayout.addView(emptyCardView);
            }

        }
    }

    private LinearLayout addGuiaCard(Guia guia) {
        // Crear el CardView
        CardView cardView = new CardView(this);
        CardView.LayoutParams cardParams = new CardView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        cardParams.setMargins(50,50,50,50);
        cardView.setLayoutParams(cardParams);
        cardView.setRadius(16);
        cardView.setCardBackgroundColor(Color.WHITE);
        cardView.setCardElevation(8);

        // Crear el LinearLayout horizontal
        LinearLayout horizontalLayout = new LinearLayout(this);
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams horizontalParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        horizontalLayout.setLayoutParams(horizontalParams);
        horizontalLayout.setPadding(16, 16, 16, 16);

        // Crear el LinearLayout vertical (izquierda)
        LinearLayout verticalLayout = new LinearLayout(this);
        verticalLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams verticalParams = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                2f
        );
        verticalLayout.setLayoutParams(verticalParams);

        // Crear los TextView
        TextView tituloTextView = new TextView(this);
        tituloTextView.setText(guia.getTitulo());
        tituloTextView.setTextSize(18);

        Usuario usuario = guia.getUsuarioId();

        TextView userNameTextView = new TextView(this);
        userNameTextView.setText(usuario.getNombreUsuario());
        userNameTextView.setTextSize(14);

        TextView averageRateView = new TextView(this);
        averageRateView.setText(guia.getCalificacionMedia() + "");
        averageRateView.setTextSize(14);
        LinearLayout.LayoutParams averageRateParams = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
        );
        averageRateView.setLayoutParams(averageRateParams);
        averageRateView.setGravity(Gravity.END);

        // Añadir TextViews al LinearLayout vertical
        verticalLayout.addView(tituloTextView);
        verticalLayout.addView(userNameTextView);

        // Añadir LinearLayout vertical y TextView de calificación al LinearLayout horizontal
        horizontalLayout.addView(verticalLayout);
        horizontalLayout.addView(averageRateView);

        // Añadir LinearLayout horizontal al CardView
        cardView.addView(horizontalLayout);

        // Retornar el CardView dentro de un LinearLayout
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        linearLayout.addView(cardView);

        return linearLayout;
    }


}



