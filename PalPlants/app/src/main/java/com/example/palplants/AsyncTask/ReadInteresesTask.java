package com.example.palplants.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.InteresBotanico;

import java.util.ArrayList;

// AsyncTask para leer los intereses botánicos desde la base de datos y cargarlos en un Spinner.
// Recibe el contexto de la aplicación, el Spinner donde se cargarán los intereses y una lista para almacenar los intereses leídos.
public class ReadInteresesTask extends AsyncTask<Void, Void, ArrayList<InteresBotanico>> {
    private Context context;
    private Spinner spinner;
    private ArrayList<InteresBotanico> interesesBotanicos;

    // Constructor
    public ReadInteresesTask(Context context, Spinner spinner, ArrayList<InteresBotanico> interesesBotanicos) {
        this.context = context;
        this.spinner = spinner;
        this.interesesBotanicos = interesesBotanicos;
    }

    // Método doInBackground
    // Este método se ejecuta en segundo plano y realiza la lectura de los intereses botánicos.
    @Override
    protected ArrayList<InteresBotanico> doInBackground(Void... voids) {
        try {
            BotanicaCC bcc = new BotanicaCC();
            return bcc.leerIntereses();
        } catch (ExcepcionBotanica ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // Método onPostExecute
    // Este método se ejecuta después de que doInBackground ha finalizado.
    @Override
    protected void onPostExecute(ArrayList<InteresBotanico> listaBotanica) {
        if (listaBotanica != null && !listaBotanica.isEmpty()) {
            ArrayList<String> intereses = new ArrayList<>();
            intereses.add("Ninguno");
            for (InteresBotanico interes : listaBotanica) {
                intereses.add(interes.getNombreInteres());
            }

            // Actualiza la variable interesesBotanicos
            interesesBotanicos.clear();
            interesesBotanicos.addAll(listaBotanica);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, intereses);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        } else {
            Toast.makeText(context, "Error al cargar los intereses", Toast.LENGTH_SHORT).show();
        }
    }
}
