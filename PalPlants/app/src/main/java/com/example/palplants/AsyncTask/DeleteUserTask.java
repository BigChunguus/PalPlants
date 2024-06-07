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

// Este AsyncTask se encarga de eliminar un usuario de la base de datos del servidor.
// Después de la eliminación, realiza algunas acciones en la interfaz de usuario según el resultado.
public class DeleteUserTask extends AsyncTask<String, Void, Boolean> {
    private Context context;

    // Constructor
    public DeleteUserTask(Context context) {
        this.context = context;
    }

    // Método doInBackground
    // Este método se ejecuta en segundo plano y realiza la eliminación del usuario.
    // Recibe el nombre de usuario como parámetro y llama al método eliminarUsuario de la clase BotanicaCC.
    // Devuelve true si se eliminó correctamente y false si ocurrió algún error.
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

    // Método onPostExecute
    // Este método se ejecuta en el hilo principal después de que doInBackground haya terminado.
    // Realiza acciones en la interfaz de usuario según el resultado de la eliminación.
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
