package com.example.palplants.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.palplants.AsyncTask.DeleteUserTask;
import com.example.palplants.AsyncTask.ModifyUserTask;
import com.example.palplants.AsyncTask.ReadInteresesTask;
import com.example.palplants.Utility.AlarmReceiver;
import com.example.palplants.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;

import pojosbotanica.InteresBotanico;
import pojosbotanica.Usuario;

// Esta clase representa la actividad de configuración en la aplicación.
// Los usuarios pueden acceder a esta actividad para ajustar diferentes configuraciones y preferencias de la aplicación.
// Algunos ejemplos de configuraciones comunes que los usuarios pueden ajustar incluyen el tema de la aplicación (claro u oscuro), la notificación de recordatorios, la frecuencia de actualización de datos, entre otros.
// La actividad SettingsActivity proporciona una interfaz de usuario intuitiva para que los usuarios puedan modificar estas configuraciones según sus preferencias.
// Además, la actividad también puede incluir funciones para restablecer la configuración predeterminada de la aplicación o para realizar copias de seguridad y restaurar la configuración del usuario.
// En resumen, esta actividad es crucial para brindar a los usuarios control sobre la experiencia de la aplicación y adaptarla a sus necesidades individuales.
public class SettingsActivity extends AppCompatActivity {

    private int themeId;
    private AlertDialog alertDialogDelete, alertDialogUpdate;
    private LinearLayout editUserDataLayout;
    private LinearLayout deleteAccountPopup;
    private ArrayList<InteresBotanico> interesesBotanicos = new ArrayList<>();
    private Button btnEditUserData, btnLogout, btnDeleteAccount, btnChangeTheme, btnPowerOffAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Cargar el tema actual
        SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        themeId = preferences.getInt("current_theme", R.style.Theme_App_Light_NoActionBar);
        setTheme(themeId);

        setContentView(R.layout.activity_settings);

        // Configurar el botón de retroceso
        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresar a la actividad anterior
                Log.e("Sett", "Activado");
                finish();
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            recreate();
            swipeRefreshLayout.setRefreshing(false);
        });

        // Configurar los botones y la navegación inferior
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

        // Configurar la navegación inferior
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.NoneActivityButton);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.YourPlantsActivityButton) {
                    if (!(getApplicationContext() instanceof YourPlantsActivity)) {
                        startActivity(new Intent(getApplicationContext(), YourPlantsActivity.class));
                        finish();
                    } else {
                        // Si ya estamos en la actividad, la recreamos
                        recreate();
                        finish();
                    }
                    return true;
                } else if (item.getItemId() == R.id.SearchActivityButton) {
                    if (!(getApplicationContext() instanceof SearchActivity)) {
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        finish();
                    } else {
                        // Si ya estamos en la actividad, la recreamos
                        recreate();
                        finish();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    // Método para apagar la alarma
    private void poweroffAlarm() {
        Intent cancelAlarmIntent = new Intent(this, AlarmReceiver.class);
        cancelAlarmIntent.setAction("CANCEL_ALARM_ACTION");
        sendBroadcast(cancelAlarmIntent);
        Toast.makeText(this, "Alarma cancelada", Toast.LENGTH_SHORT).show();
    }

    // Método para cambiar el tema de la aplicación
    private void changeTheme() {
        if (themeId == R.style.Theme_App_Light_NoActionBar) {
            btnChangeTheme.setText("Cambiar Tema: Claro");
            themeId = R.style.Theme_App_Dark_NoActionBar;
        } else {
            themeId = R.style.Theme_App_Light_NoActionBar;
            btnChangeTheme.setText("Cambiar Tema: Oscuro");
        }

        // Guardar el tema seleccionado en las preferencias compartidas
        SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("current_theme", themeId);
        editor.apply();

        // Reiniciar todas las actividades para aplicar el nuevo tema
        restartAllActivities();
    }

    // Método para reiniciar todas las actividades para aplicar el nuevo tema
    private void restartAllActivities() {
        Intent intent = new Intent(this, YourPlantsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    // Método para mostrar el cuadro de diálogo de eliminación de cuenta
    private void showDeleteAccountPopup() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View deleteUserView = inflater.inflate(R.layout.dialog_btn_delete_account, null);
        Button btnCancel = deleteUserView.findViewById(R.id.btn_cancel);
        Button btnConfirm = deleteUserView.findViewById(R.id.btn_confirm);

        // Configurar el botón Cancelar
        btnCancel.setOnClickListener(v -> onCancelDeleteUser());

        // Configurar el botón Confirmar
        btnConfirm.setOnClickListener(v -> onConfirmDeleteUser());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(deleteUserView);
        alertDialogDelete = builder.create();
        alertDialogDelete.show();
    }

    // Método para cancelar la eliminación de la cuenta
    private void onCancelDeleteUser() {
        if (alertDialogDelete != null && alertDialogDelete.isShowing()) {
            alertDialogDelete.dismiss();
        }
    }

    // Método para confirmar la eliminación de la cuenta
    private void onConfirmDeleteUser() {
        SharedPreferences preferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String usuarioJson = preferences.getString("user", null);

        if (usuarioJson != null && !usuarioJson.isEmpty()) {
            Gson gson = new Gson();
            Usuario usuario = gson.fromJson(usuarioJson, Usuario.class);

            new DeleteUserTask(this).execute(usuario.getNombreUsuario());
        }
    }

    // Método para mostrar el cuadro de diálogo de edición de datos del usuario
    private void showEditUserDataPopup() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View editUserDataView = inflater.inflate(R.layout.dialog_edit_user_data, null);

        // Obtener referencias a las vistas del diálogo
        TextInputEditText editTextRealNameUpdate = editUserDataView.findViewById(R.id.editTextRealNameUpdate);
        TextInputEditText editTextFirstSurnameUpdate = editUserDataView.findViewById(R.id.editTextFirstSurnameUpdate);
        TextInputEditText editTextSecondSurnameUpdate = editUserDataView.findViewById(R.id.editTextSecondSurnameUpdate);
        TextInputEditText editTextDNIUpdate = editUserDataView.findViewById(R.id.editTextDNIUpdate);
        Spinner spinnerInterest = editUserDataView.findViewById(R.id.spinnerInterest);
        Button btnCancel = editUserDataView.findViewById(R.id.btn_cancel);
        Button btnSave = editUserDataView.findViewById(R.id.btn_save);

        // Configurar el botón Cancelar
        btnCancel.setOnClickListener(v -> onCancelEditUserData());

        // Cargar los intereses botánicos del usuario
        new ReadInteresesTask(this, spinnerInterest, interesesBotanicos).execute();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(editUserDataView);
        alertDialogUpdate = builder.create();
        alertDialogUpdate.show();

        // Configurar el botón Guardar
        btnSave.setOnClickListener(v -> {
            // Obtener los datos ingresados por el usuario
            String realName = editTextRealNameUpdate.getText().toString();
            String firstSurname = editTextFirstSurnameUpdate.getText().toString();
            String secondSurname = editTextSecondSurnameUpdate.getText().toString();
            String dni = editTextDNIUpdate.getText().toString();
            String selectedInterest = spinnerInterest.getSelectedItem().toString();

            // Crear un objeto Usuario con los datos ingresados
            Usuario usuario = new Usuario();
            if (!realName.isEmpty()) {
                usuario.setNombre(realName);
            } else {
                realName = null;
            }

            if (!firstSurname.isEmpty()) {
                usuario.setApellido1(firstSurname);
            } else {
                firstSurname = null;
            }

            if (!secondSurname.isEmpty()) {
                usuario.setApellido2(secondSurname);
            } else {
                secondSurname = null;
            }

            if (!dni.isEmpty()) {
                usuario.setDni(dni);
            } else {
                dni = null;
            }

            InteresBotanico interesBotanico = new InteresBotanico();

            if (!selectedInterest.equals("Ninguno")) {
                for (InteresBotanico interesBotanicoAux : interesesBotanicos) {
                    if (selectedInterest.equals(interesBotanicoAux.getNombreInteres())) {
                        interesBotanico = new InteresBotanico(interesBotanicoAux.getInteresId(), interesBotanicoAux.getNombreInteres());
                        break;
                    }
                }
                usuario.setInteres(interesBotanico);
            }

            if (usuario.getNombre() != null || usuario.getApellido1() != null || usuario.getApellido2() != null || usuario.getDni() != null || usuario.getInteres() != null) {
                new ModifyUserTask(this).execute(usuario);
            }

            alertDialogUpdate.dismiss();
        });
    }

    // Método para cancelar la edición de datos del usuario
    private void onCancelEditUserData() {
        if (alertDialogUpdate != null && alertDialogUpdate.isShowing()) {
            alertDialogUpdate.dismiss();
        }
    }

    // Método para mostrar el cuadro de diálogo de confirmación de cierre de sesión
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

    // Método para cerrar sesión
    private void logout() {
        // Limpiar los datos de usuario almacenados en las preferencias compartidas
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Redirigir a la actividad principal (inicio de sesión)
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

