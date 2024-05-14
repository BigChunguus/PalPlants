package com.example.palplants;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Planta;

public class PlantsActivity extends AppCompatActivity {

    Planta plantaSeleccionada = new Planta();
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants);

        // Inicializar el TextView
        textView = findViewById(R.id.textView); // Reemplaza R.id.textView con el ID real de tu TextView en activity_plants.xml

        // Recuperar el ID de la planta del intent que inició esta actividad
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("PLANT")) {
            //Linea para recoger la planta
            //try{

                Planta plantaSeleccionada = (Planta) intent.getSerializableExtra("PLANT");
                //BotanicaCC botanicaCC = new BotanicaCC(); // Instancia de BotanicaCC
                //plantaSeleccionada = botanicaCC.leerPlanta(plantId);
                textView.setText(plantaSeleccionada.toString()); // Aquí utilizo toString() para mostrar la representación de la planta en el TextView
            //} catch (ExcepcionBotanica ex) {
              //  ex.printStackTrace();
            //}
        } else {
            // Manejar el caso en el que no se haya proporcionado el extra "PLANT_ID"
            // Puedes mostrar un mensaje de error o tomar otras acciones apropiadas
        }
    }

}
