package com.example.palplants.AsyncTask;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.palplants.Adapter.PlantAdapter;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Planta;
import pojosbotanica.Usuario;

// Esta clase representa una tarea asíncrona para eliminar una planta.
// Al eliminar una planta, se comunica con el servidor para realizar los cambios necesarios en la base de datos remota.
// La eliminación de la planta se realiza en segundo plano, en un hilo separado del hilo principal de la interfaz de usuario, para no bloquear la UI mientras se lleva a cabo la operación.
public class DeletePlantTask extends AsyncTask<Object, Void, Boolean> {
    private PlantAdapter adapter;
    private int position;

    // Constructor de la clase DeletePlantTask
    public DeletePlantTask(PlantAdapter adapter, int position) {
        this.adapter = adapter;
        this.position = position;
    }

    // Método doInBackground: realiza la eliminación de la planta en segundo plano
    @Override
    protected Boolean doInBackground(Object... params) {
        Usuario user = (Usuario) params[0];
        Planta planta = (Planta) params[1];
        int cambios = 0;
        try {
            // Crea una instancia de BotanicaCC para interactuar con el servidor
            BotanicaCC bcc = new BotanicaCC();
            // Intenta eliminar la planta asociada al usuario y devuelve true si se realizó con éxito
            cambios = bcc.eliminarUsuarioPlanta(user.getUsuarioID(), planta.getPlantaId());
            if (cambios == 1) {
                return true;
            } else {
                return false;
            }
        } catch (ExcepcionBotanica ex) {
            ex.printStackTrace();
            // En caso de excepción, devuelve false
            return false;
        }
    }

    // Método onPostExecute: se llama después de que doInBackground finaliza; actualiza la interfaz de usuario según el resultado de la eliminación
    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            // Si la eliminación fue exitosa, se elimina la planta de la lista en la posición dada
            adapter.removePlantAt(position);
        } else {
            // Si ocurrió un error durante la eliminación, se muestra un mensaje de error y se notifica un cambio en el elemento en la posición dada
            Toast.makeText(adapter.getContext(), "Error al eliminar la planta", Toast.LENGTH_SHORT).show();
            adapter.notifyItemChanged(position);
        }
    }
}
