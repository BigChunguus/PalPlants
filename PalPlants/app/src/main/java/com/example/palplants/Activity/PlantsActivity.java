package com.example.palplants.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.PopupMenu;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.palplants.AsyncTask.FindPlantRegisteredTask;
import com.example.palplants.AsyncTask.InsertGuideTask;
import com.example.palplants.AsyncTask.InsertPlantTask;
import com.example.palplants.AsyncTask.ReadGuidesPlantTask;
import com.example.palplants.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

import pojosbotanica.Guia;
import pojosbotanica.Planta;
import pojosbotanica.Usuario;

public class PlantsActivity extends AppCompatActivity {

    private int plantIdToCheck;
    private Planta plantaSeleccionada = new Planta();
    private ImageView imagePlant;
    private ImageButton buttonAdd, buttonAddGuide, mButtonDropdownMenu;
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
        mButtonDropdownMenu = findViewById(R.id.mButtonDropdownMenu);
        recyclerView = findViewById(R.id.recyclerView);
        ImageButton mButtonDropdownMenu = findViewById(R.id.mButtonDropdownMenu);
        mButtonDropdownMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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



        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String usuarioJson = sharedPreferences.getString("user", "");
        if (!usuarioJson.isEmpty()) {
            Gson gson = new Gson();
            usuario = gson.fromJson(usuarioJson, Usuario.class);
        }

        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresa a la actividad anterior
                finish();
            }
        });
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
            new ReadGuidesPlantTask(this, plantIdToCheck, usuario.getUsuarioID(), recyclerView, buttonAddGuide, mButtonDropdownMenu).execute();
        }
    }

    public void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(PlantsActivity.this, view);
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon",boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.getMenuInflater().inflate(R.menu.menu_more_options, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getApplicationContext(), "You Clicked : " + item.getTitle(),  Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        popup.show();
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

                    new InsertGuideTask(PlantsActivity.this, guia, dialog).execute();
                    dialog.dismiss();
                } else {
                    // Mostrar un mensaje de error
                }
            }
        });

        dialog.show();
    }

}
