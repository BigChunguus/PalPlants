package com.example.palplants.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.palplants.Adapter.PlantSearchAdapter;
import com.example.palplants.AsyncTask.SelectAllPlants;
import com.example.palplants.R;

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

