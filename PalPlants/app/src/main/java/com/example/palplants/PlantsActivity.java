package com.example.palplants;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Planta;

public class PlantsActivity extends AppCompatActivity {

    Planta plantaSeleccionada = new Planta();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_plants);

        // Recuperar el ID de la planta del intent que inició esta actividad
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("PLANT_ID")) {
            int plantId = intent.getIntExtra("PLANT_ID", -1); // -1 es un valor predeterminado en caso de que no se encuentre el extra
            try{
                BotanicaCC botanicaCC = new BotanicaCC(); // Instancia de BotanicaCC
                plantaSeleccionada = botanicaCC.leerPlanta(plantId);
            } catch (ExcepcionBotanica ex) {
                ex.printStackTrace();
            }
        } else {
            // Manejar el caso en el que no se haya proporcionado el extra "PLANT_ID"
            // Puedes mostrar un mensaje de error o tomar otras acciones apropiadas
        }
    }

}
