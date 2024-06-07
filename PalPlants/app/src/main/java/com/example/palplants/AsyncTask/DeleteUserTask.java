package com.example.palplants.AsyncTask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.palplants.Activity.MainActivity;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;

public class DeleteUserTask extends AsyncTask<String, Void, Boolean> {
    private Context context;

    public DeleteUserTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String username = params[0];
        try {
            BotanicaCC botanicaCC = new BotanicaCC();
            int cambios = botanicaCC.eliminarUsuario(username);
            Log.e("UserDelete", cambios + "");
            return cambios == 1;
        } catch (ExcepcionBotanica e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            SharedPreferences preferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            Toast.makeText(context, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error al eliminar el usuario", Toast.LENGTH_SHORT).show();
        }
    }
}
