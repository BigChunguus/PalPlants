package com.example.palplants.AsyncTask;

import android.os.AsyncTask;

import com.example.palplants.Utility.InsertUserCallback;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Usuario;

// AsyncTask encargado de insertar un nuevo usuario en la base de datos del servidor.
// Después de la inserción, llama al método de callback para informar sobre el resultado.
public class InsertUserTask extends AsyncTask<Usuario, Void, Boolean> {
    private InsertUserCallback callback;

    // Constructor
    public InsertUserTask(InsertUserCallback callback) {
        this.callback = callback;
    }

    // Método doInBackground
    // Este método se ejecuta en segundo plano y realiza la inserción del nuevo usuario en la base de datos.
    @Override
    protected Boolean doInBackground(Usuario... params) {
        Usuario usuario = params[0];
        try {
            // Insertar el usuario en la base de datos
            BotanicaCC bcc = new BotanicaCC();
            int resultado = bcc.insertarUsuario(usuario);
            return resultado == 1;
        } catch (ExcepcionBotanica ex) {
            // Capturar excepción si ocurre
            ex.printStackTrace();
            return false;
        }
    }

    // Método onPostExecute
    // Este método se ejecuta en el hilo principal después de que doInBackground haya terminado.
    // Llama al método de callback para informar sobre el resultado de la inserción.
    @Override
    protected void onPostExecute(Boolean success) {
        if (callback != null) {
            callback.onInsertUserResult(success);
        }
    }
}
