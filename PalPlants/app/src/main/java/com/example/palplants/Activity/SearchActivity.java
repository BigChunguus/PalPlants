package com.example.palplants.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.palplants.Adapter.PlantSearchAdapter;
import com.example.palplants.AsyncTask.SelectAllPlants;
import com.example.palplants.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import pojosbotanica.Planta;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlantSearchAdapter plantAdapter;
    private TextView emptyMessage;
    private EditText editTextSearch; // Agregar el EditText

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        int themeId = preferences.getInt("current_theme", R.style.Theme_App_Light_NoActionBar);
        setTheme(themeId);
        setContentView(R.layout.activity_search);

        plantAdapter = new PlantSearchAdapter(new ArrayList<>(), this);
        recyclerView = findViewById(R.id.recyclerView);
        emptyMessage = findViewById(R.id.emptyTextView);
        editTextSearch = findViewById(R.id.editTextSearch); // Obtener referencia al EditText
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.SearchActivityButton);
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

        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresa a la actividad anterior
                finish();
            }
        });
        selectAllPlants("");

        // Agregar TextWatcher para detectar cambios en el EditText
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString().trim(); // Obtener texto de búsqueda
                selectAllPlants(searchText); // Filtrar las plantas según el texto de búsqueda
            }
        });
    }

    private void selectAllPlants(String searchText) {
        new SelectAllPlants(this, recyclerView, emptyMessage, searchText).execute();
    }
}

