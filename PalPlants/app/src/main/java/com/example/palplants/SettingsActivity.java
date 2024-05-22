package com.example.palplants;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.InteresBotanico;
import pojosbotanica.Usuario;

public class SettingsActivity extends AppCompatActivity {


    private int themeId;
    private AlertDialog alertDialogDelete, alertDialogUpdate;
    private LinearLayout editUserDataLayout;
    private LinearLayout deleteAccountPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Cargar tema desde preferencias
        SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        themeId = preferences.getInt("current_theme", R.style.Theme_App_Light_NoActionBar);
        setTheme(themeId);

        setContentView(R.layout.activity_settings);

        // Configurar los listeners de los botones
        Button btnEditUserData = findViewById(R.id.btn_edit_user_data);
        btnEditUserData.setOnClickListener(v -> showEditUserDataPopup());

        Button btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(v -> showLogoutConfirmation());

        Button btnDeleteAccount = findViewById(R.id.btn_delete_account);
        btnDeleteAccount.setOnClickListener(v -> showDeleteAccountPopup());

        Button btnChangeTheme = findViewById(R.id.btn_change_theme);
        btnChangeTheme.setOnClickListener(v -> changeTheme());
    }

    private void changeTheme() {
        // Cambiar tema
        if (themeId == R.style.Theme_App_Light_NoActionBar) {
            themeId = R.style.Theme_App_Dark_NoActionBar;
        } else {
            themeId = R.style.Theme_App_Light_NoActionBar;
        }

        // Guardar tema en preferencias
        SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("current_theme", themeId);
        editor.apply();

        // Reiniciar todas las actividades para aplicar nuevo tema
        restartAllActivities();
    }

    private void restartAllActivities() {
        Intent intent = new Intent(this, YourPlantsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void showDeleteAccountPopup(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View deleteUserView = inflater.inflate(R.layout.dialog_btn_delete_account, null);
        Button btnCancel = deleteUserView.findViewById(R.id.btn_cancel);
        Button btnConfirm = deleteUserView.findViewById(R.id.btn_confirm);

        btnCancel.setOnClickListener(v -> onCancelDeleteUser());
        btnConfirm.setOnClickListener(v -> onConfirmDeleteUser());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(deleteUserView);
        alertDialogDelete = builder.create(); // Inicializa alertDialog
        alertDialogDelete.show();
    }

    private void onCancelDeleteUser(){
        // Cierra el diálogo si está inicializado
        if(alertDialogDelete != null && alertDialogDelete.isShowing()) {
            alertDialogDelete.dismiss();
        }
    }

    private void onConfirmDeleteUser() {
        // Obtener SharedPreferences
        SharedPreferences preferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String usuarioJson = preferences.getString("user", null);

        if (!usuarioJson.isEmpty()) {
            // Convertir el JSON a objeto Usuario
            Gson gson = new Gson();
            Usuario usuario = gson.fromJson(usuarioJson, Usuario.class);

            // Verificar el usuario en la base de datos
            new DeleteUserTask().execute(usuario.getNombreUsuario());
        }
    }
    private class DeleteUserTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String username = params[0];
            try {
                BotanicaCC botanicaCC = new BotanicaCC();
                int cambios = botanicaCC.eliminarUsuario(username);
                if(cambios == 1)
                    return true;
                else
                    return false;
            } catch (ExcepcionBotanica e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                SharedPreferences preferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();

                // Navegar a MainActivity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                // Finalizar la actividad actual
                finish();

            } else {
                // Mostrar un mensaje de error o realizar otras acciones necesarias
                Toast.makeText(getApplicationContext(), "Error al eliminar el usuario", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void showEditUserDataPopup() {
        // Inflar el diseño XML de edición de datos de usuario
        LayoutInflater inflater = LayoutInflater.from(this);
        View editUserDataView = inflater.inflate(R.layout.dialog_edit_user_data, null);

        // Obtener referencias a los elementos de la interfaz de usuario dentro de ese diseño
        TextInputEditText editTextRealNameUpdate = editUserDataView.findViewById(R.id.editTextRealNameUpdate);
        TextInputEditText editTextFirstSurnameUpdate = editUserDataView.findViewById(R.id.editTextFirstSurnameUpdate);
        TextInputEditText editTextSecondSurnameUpdate = editUserDataView.findViewById(R.id.editTextSecondSurnameUpdate);
        TextInputEditText editTextDNIUpdate = editUserDataView.findViewById(R.id.editTextDNIUpdate);
        Spinner spinnerInterest = editUserDataView.findViewById(R.id.spinnerInterest); // Referenciar el Spinner aquí
        Button btnCancel = editUserDataView.findViewById(R.id.btn_cancel);
        Button btnSave = editUserDataView.findViewById(R.id.btn_save);

        // Configurar los listeners de los botones
        btnCancel.setOnClickListener(v -> onCancelEditUserData());


        // Mostrar el diálogo de edición de datos de usuario
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(editUserDataView);
        alertDialogUpdate = builder.create();
        alertDialogUpdate.show();

        // Configurar el listener para el botón de guardar
        btnSave.setOnClickListener(v -> {
            // Realizar las acciones para modificar los datos del usuario
            // Obtener los datos modificados de los campos de entrada
            String realName = editTextRealNameUpdate.getText().toString();
            String firstSurname = editTextFirstSurnameUpdate.getText().toString();
            String secondSurname = editTextSecondSurnameUpdate.getText().toString();
            String dni = editTextDNIUpdate.getText().toString();
            String selectedInterest = spinnerInterest.getSelectedItem().toString();

            // Crear una instancia de Usuario y establecer los datos modificados
            Usuario usuario = new Usuario();
            usuario.setNombre(realName);
            usuario.setApellido1(firstSurname);
            usuario.setApellido2(secondSurname);
            usuario.setDni(dni);
            InteresBotanico interesBotanico = new InteresBotanico(1, selectedInterest); // Id de valor por defecto, se arregla en el servidor
            usuario.setInteres(interesBotanico);

            new ModifyUserTask().execute(usuario);

            // Cerrar el diálogo después de guardar los cambios
            alertDialogUpdate.dismiss();
        });
    }

    private void onCancelEditUserData() {
        if(alertDialogUpdate != null && alertDialogUpdate.isShowing()) {
            alertDialogUpdate.dismiss();
        }
    }


    private void showLogoutConfirmation() {
        // Inflar el diseño XML de confirmación de cierre de sesión
        LayoutInflater inflater = LayoutInflater.from(this);
        View confirmationView = inflater.inflate(R.layout.dialog_btn_logout, null);

        // Configurar y mostrar la ventana emergente de confirmación
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(confirmationView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // Configurar los listeners de los botones en el diálogo de confirmación
        Button btnCancel = confirmationView.findViewById(R.id.btn_cancel_logout);
        btnCancel.setOnClickListener(v -> alertDialog.dismiss());

        Button btnConfirm = confirmationView.findViewById(R.id.btn_confirm_logout);
        btnConfirm.setOnClickListener(v -> {
            // Realizar las acciones para cerrar sesión
            logout();
            alertDialog.dismiss();
        });
    }

    private void logout() {
        // Eliminar las SharedPreferences de UserInfo
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Redirigir al ActivityMain
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private class ModifyUserTask extends AsyncTask<Usuario, Void, Void> {
        @Override
        protected Void doInBackground(Usuario... usuarios) {
            if (usuarios.length > 0) {
                Usuario usuario = usuarios[0];
                SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                try {
                    BotanicaCC botanicaCC = new BotanicaCC();
                    botanicaCC.modificarUsuario(username, usuario);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class ConnectBotanicaInteresTask extends AsyncTask<Void, Void, ArrayList<InteresBotanico>> {
        private Spinner spinner;

        public ConnectBotanicaInteresTask(Spinner spinner) {
            this.spinner = spinner;
        }

        @Override
        protected ArrayList<InteresBotanico> doInBackground(Void... voids) {
            try {
                BotanicaCC bcc = new BotanicaCC();
                return bcc.leerIntereses();
            } catch (ExcepcionBotanica ex) {
                ex.printStackTrace(); // Maneja el error apropiadamente
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<InteresBotanico> listaBotanica) {
            if (listaBotanica != null && !listaBotanica.isEmpty()) {
                ArrayList<String> intereses = new ArrayList<>();
                // Recorre la lista de intereses y obtén los nombres de los intereses
                for (InteresBotanico interes : listaBotanica) {
                    intereses.add(interes.getNombreInteres()); // Suponiendo que hay un método para obtener el nombre del interés
                }

                // Crea un adaptador para el Spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<>(SettingsActivity.this, android.R.layout.simple_spinner_item, intereses);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Establece el adaptador en el Spinner
                spinner.setAdapter(adapter);
            } else {
                // Maneja la situación de error aquí
                Toast.makeText(SettingsActivity.this, "Error al cargar los intereses", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
