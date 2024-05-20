package com.example.palplants;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;
import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.InteresBotanico;
import pojosbotanica.Planta;
import pojosbotanica.Usuario;

public class SettingsActivity extends AppCompatActivity {

    private LinearLayout editUserDataLayout;
    private LinearLayout deleteAccountPopup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Inicializar los elementos de la interfaz
        editUserDataLayout = findViewById(R.id.edit_user_data_layout);
        deleteAccountPopup = findViewById(R.id.delete_account_popup);

        // Configurar los listeners de los botones
        Button btnEditUserData = findViewById(R.id.btn_edit_user_data);
        btnEditUserData.setOnClickListener(v -> showEditUserDataPopup());

        Button btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(v -> showLogoutConfirmation());

        Button btnDeleteAccount = findViewById(R.id.btn_delete_account);
        btnDeleteAccount.setOnClickListener(v -> showDeleteAccountPopup());

        Button btnChangeTheme = findViewById(R.id.btn_change_theme);
        btnChangeTheme.setOnClickListener(v -> changeTheme());

        Button btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(v -> hideDeleteAccountPopup());

        Button btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(v -> deleteAccount());

    }

    // Método para mostrar el diálogo de edición de datos de usuario
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
        btnSave.setOnClickListener(v -> onSaveEditUserData());

        // Mostrar el diálogo de edición de datos de usuario
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(editUserDataView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


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
            alertDialog.dismiss();
        });
    }

    private void onCancelEditUserData() {
        // Implementar lógica para cancelar la edición de datos de usuario
    }

    private void onSaveEditUserData() {

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
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Redirigir al ActivityMain
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showDeleteAccountPopup() {
        deleteAccountPopup.setVisibility(View.VISIBLE);
    }

    private void hideDeleteAccountPopup() {
        deleteAccountPopup.setVisibility(View.GONE);
    }

    private void deleteAccount() {
        // Implementa la lógica para borrar la cuenta del usuario
        Toast.makeText(this, "Cuenta borrada", Toast.LENGTH_SHORT).show();
        hideDeleteAccountPopup();
    }

    private void changeTheme() {
        // Implementa la lógica para cambiar el tema de la aplicación
        Toast.makeText(this, "Cambiar Tema", Toast.LENGTH_SHORT).show();
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
                    botanicaCC.modificarUsuario(username,usuario);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Mostrar un mensaje o realizar cualquier otra acción después de modificar el usuario
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
                // Recorre la lista de plantas y obtén los nombres de los intereses
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
