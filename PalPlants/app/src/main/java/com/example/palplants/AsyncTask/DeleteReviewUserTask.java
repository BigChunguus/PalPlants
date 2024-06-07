package com.example.palplants.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Resena;
import pojosbotanica.Usuario;

public class DeleteReviewUserTask extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private Resena resena;

    public DeleteReviewUserTask(Context context,Resena resena) {
        this.context = context;
        this.resena = resena;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            BotanicaCC botanicaCC = new BotanicaCC();
            int cambios = botanicaCC.eliminarResena(resena.getResenaId());
            return cambios == 1;
        } catch (ExcepcionBotanica e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {

        } else {

        }
    }
}
