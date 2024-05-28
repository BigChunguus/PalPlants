package com.example.palplants.Activity;

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

import com.example.palplants.Utility.AlarmReceiver;
import com.example.palplants.R;
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
    private ArrayList<InteresBotanico> interesesBotanicos;
    private Button btnEditUserData,btnLogout,btnDeleteAccount,btnChangeTheme,btnPowerOffAlarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        themeId = preferences.getInt("current_theme", R.style.Theme_App_Light_NoActionBar);
        setTheme(themeId);

        setContentView(R.layout.activity_settings);

        btnEditUserData = findViewById(R.id.btn_edit_user_data);
        btnEditUserData.setOnClickListener(v -> showEditUserDataPopup());

        btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(v -> showLogoutConfirmation());

        btnDeleteAccount = findViewById(R.id.btn_delete_account);
        btnDeleteAccount.setOnClickListener(v -> showDeleteAccountPopup());

        btnChangeTheme = findViewById(R.id.btn_change_theme);
        btnChangeTheme.setOnClickListener(v -> changeTheme());
        if (themeId == R.style.Theme_App_Light_NoActionBar) {
            btnChangeTheme.setText("Cambiar Tema: Claro");
        } else {
            btnChangeTheme.setText("Cambiar Tema: Oscuro");
        }
        btnPowerOffAlarm = findViewById(R.id.btn_poweroff_alarm);
        btnPowerOffAlarm.setOnClickListener(v -> poweroffAlarm());
    }

    private void poweroffAlarm(){
        Intent cancelAlarmIntent = new Intent(this, AlarmReceiver.class);
        cancelAlarmIntent.setAction("CANCEL_ALARM_ACTION");
        sendBroadcast(cancelAlarmIntent);
        Toast.makeText(this, "Alarma cancelada", Toast.LENGTH_SHORT).show();
    }
    private void changeTheme() {
        if (themeId == R.style.Theme_App_Light_NoActionBar) {
            btnChangeTheme.setText("Cambiar Tema: Claro");
            themeId = R.style.Theme_App_Dark_NoActionBar;
        } else {
            themeId = R.style.Theme_App_Light_NoActionBar;
            btnChangeTheme.setText("Cambiar Tema: Oscuro");
        }

        SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("current_theme", themeId);
        editor.apply();

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
        alertDialogDelete = builder.create();
        alertDialogDelete.show();
    }

    private void onCancelDeleteUser(){
        if(alertDialogDelete != null && alertDialogDelete.isShowing()) {
            alertDialogDelete.dismiss();
        }
    }

    private void onConfirmDeleteUser() {
        SharedPreferences preferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String usuarioJson = preferences.getString("user", null);

        if (!usuarioJson.isEmpty()) {
            Gson gson = new Gson();
            Usuario usuario = gson.fromJson(usuarioJson, Usuario.class);

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
                return cambios == 1;
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

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                finish();

            } else {
                Toast.makeText(getApplicationContext(), "Error al eliminar el usuario", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void showEditUserDataPopup() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View editUserDataView = inflater.inflate(R.layout.dialog_edit_user_data, null);

        TextInputEditText editTextRealNameUpdate = editUserDataView.findViewById(R.id.editTextRealNameUpdate);
        TextInputEditText editTextFirstSurnameUpdate = editUserDataView.findViewById(R.id.editTextFirstSurnameUpdate);
        TextInputEditText editTextSecondSurnameUpdate = editUserDataView.findViewById(R.id.editTextSecondSurnameUpdate);
        TextInputEditText editTextDNIUpdate = editUserDataView.findViewById(R.id.editTextDNIUpdate);
        Spinner spinnerInterest = editUserDataView.findViewById(R.id.spinnerInterest);
        Button btnCancel = editUserDataView.findViewById(R.id.btn_cancel);
        Button btnSave = editUserDataView.findViewById(R.id.btn_save);

        btnCancel.setOnClickListener(v -> onCancelEditUserData());
        new ConnectBotanicaInteresTask(spinnerInterest).execute();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(editUserDataView);
        alertDialogUpdate = builder.create();
        alertDialogUpdate.show();

        btnSave.setOnClickListener(v -> {
            String realName = editTextRealNameUpdate.getText().toString();
            String firstSurname = editTextFirstSurnameUpdate.getText().toString();
            String secondSurname = editTextSecondSurnameUpdate.getText().toString();
            String dni = editTextDNIUpdate.getText().toString();
            String selectedInterest = spinnerInterest.getSelectedItem().toString();

            Usuario usuario = new Usuario();
            if (!realName.isEmpty())
                usuario.setNombre(realName);
            else
                realName = null;

            if (!firstSurname.isEmpty())
                usuario.setApellido1(firstSurname);
            else
                firstSurname = null;

            if (!secondSurname.isEmpty())
                usuario.setApellido2(secondSurname);
            else
                secondSurname = null;

            if (!dni.isEmpty())
                usuario.setDni(dni);
            else
                dni = null;





            InteresBotanico interesBotanico = new InteresBotanico();

            if(!selectedInterest.equals("Ninguno")){
                for(InteresBotanico interesBotanicoAux : interesesBotanicos){
                    if(selectedInterest.equals(interesBotanicoAux.getNombreInteres())){
                        interesBotanico = new InteresBotanico(interesBotanicoAux.getInteresId(), interesBotanicoAux.getNombreInteres());
                    } else
                        interesBotanico = new InteresBotanico(1, "Por Defecto");
                }
                usuario.setInteres(interesBotanico);
            }

            if (usuario.getNombre() != null || usuario.getApellido1() != null || usuario.getApellido2() != null || usuario.getDni() != null || usuario.getInteres() != null)
                new ModifyUserTask().execute(usuario);




            alertDialogUpdate.dismiss();
        });
    }

    private void onCancelEditUserData() {
        if(alertDialogUpdate != null && alertDialogUpdate.isShowing()) {
            alertDialogUpdate.dismiss();
        }
    }


    private void showLogoutConfirmation() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View confirmationView = inflater.inflate(R.layout.dialog_btn_logout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(confirmationView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button btnCancel = confirmationView.findViewById(R.id.btn_cancel_logout);
        btnCancel.setOnClickListener(v -> alertDialog.dismiss());

        Button btnConfirm = confirmationView.findViewById(R.id.btn_confirm_logout);
        btnConfirm.setOnClickListener(v -> {
            logout();
            alertDialog.dismiss();
        });
    }

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private class  ModifyUserTask extends AsyncTask<Usuario, Void, Void> {
        @Override
        protected Void doInBackground(Usuario... usuarios) {
            if (usuarios.length > 0) {
                Usuario usuario = usuarios[0];
                String nombreUsuario = null;
                SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                String usuarioJson = sharedPreferences.getString("user", "");

                if (!usuarioJson.isEmpty()) {
                    Gson gson = new Gson();
                    Usuario usuarioAux = gson.fromJson(usuarioJson, Usuario.class);
                    nombreUsuario = usuarioAux.getNombreUsuario();
                } else {
                    Log.e("UserInfo", "Error: No se pudo obtener el usuario desde SharedPreferences");
                }
                try {
                    BotanicaCC botanicaCC = new BotanicaCC();
                    Log.e("Cambios", nombreUsuario + usuario.toString());
                    botanicaCC.modificarUsuario(nombreUsuario, usuario);
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
                interesesBotanicos = bcc.leerIntereses();
                return interesesBotanicos;
            } catch (ExcepcionBotanica ex) {
                ex.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<InteresBotanico> listaBotanica) {
            if (listaBotanica != null && !listaBotanica.isEmpty()) {
                ArrayList<String> intereses = new ArrayList<>();
                intereses.add("Ninguno");
                for (InteresBotanico interes : listaBotanica) {
                    intereses.add(interes.getNombreInteres());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(SettingsActivity.this, android.R.layout.simple_spinner_item, intereses);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(adapter);
            } else {
                Toast.makeText(SettingsActivity.this, "Error al cargar los intereses", Toast.LENGTH_SHORT).show();
            }
        }
    }


}

