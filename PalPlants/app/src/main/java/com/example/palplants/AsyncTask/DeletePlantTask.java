package com.example.palplants.AsyncTask;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.palplants.Adapter.PlantAdapter;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Planta;
import pojosbotanica.Usuario;

public class DeletePlantTask extends AsyncTask<Object, Void, Boolean> {
    private PlantAdapter adapter;
    private int position;

    public DeletePlantTask(PlantAdapter adapter, int position) {
        this.adapter = adapter;
        this.position = position;
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        Usuario user = (Usuario) params[0];
        Planta planta = (Planta) params[1];
        int cambios = 0;
        try {
            BotanicaCC bcc = new BotanicaCC();
            cambios = bcc.eliminarUsuarioPlanta(user.getUsuarioID(), planta.getPlantaId());
            if (cambios == 1) {
                return true;
            } else {
                return false;
            }
        } catch (ExcepcionBotanica ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            adapter.removePlantAt(position);
        } else {
            Toast.makeText(adapter.getContext(), "Error al eliminar la planta", Toast.LENGTH_SHORT).show();
            adapter.notifyItemChanged(position);
        }
    }
}
