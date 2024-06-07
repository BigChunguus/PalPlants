package com.example.palplants.Activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.palplants.AsyncTask.SelectAllPlantsByUser;
import com.example.palplants.Utility.AlarmReceiver;
import com.example.palplants.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import pojosbotanica.Usuario;

// Esta clase representa la actividad "YourPlants" en la aplicación.
// En esta actividad, los usuarios pueden ver una lista de las plantas que tienen en su colección personal.
// La actividad muestra las plantas en forma de tarjetas o elementos de lista, con detalles como el nombre científico y el nombre común de la planta, así como una imagen representativa de la planta.
// Los usuarios pueden interactuar con cada planta de diversas formas, como ver detalles adicionales, configurar alarmas de riego, acceder a guías relacionadas con la planta, entre otros.
// Además, la actividad YourPlantsActivity puede incluir opciones para ordenar las plantas según diferentes criterios, como nombre, fecha de adición, etc.
// También puede permitir a los usuarios agregar nuevas plantas a su colección o acceder a la funcionalidad de búsqueda para encontrar plantas específicas.
// En resumen, esta actividad es el centro de gestión de la colección de plantas del usuario en la aplicación.
public class YourPlantsActivity extends AppCompatActivity {

    private static final int REQUEST_NOTIFICATION_PERMISSION = 2;
    private static final int YOUR_PLANTS_ACTIVITY_BUTTON_ID = R.id.YourPlantsActivityButton;
    private static final int SEARCH_ACTIVITY_BUTTON_ID = R.id.SearchActivityButton;
    private RecyclerView recyclerView;
    private TextView emptyMessage;
    private ImageButton buttonSettings, buttonAlarm, buttonAdd;
    private Usuario currentUser;
    private Button buttonOrder;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String orderBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Configuración del tema
        SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        int themeId = preferences.getInt("current_theme", R.style.Theme_App_Light_NoActionBar);
        setTheme(themeId);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yourplants);

        // Solicitar permiso de notificación
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_NOTIFICATION_PERMISSION);
        }
        // Crear el canal de notificación
        createNotificationChannel();

        // Obtener referencias a las vistas
        buttonSettings = findViewById(R.id.buttonSettings);
        recyclerView = findViewById(R.id.recyclerViewYourPlants);
        buttonAlarm = findViewById(R.id.buttonAlarm);
        emptyMessage = findViewById(R.id.emptyTextView);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        buttonOrder = findViewById(R.id.buttonOrder);
        buttonAdd = findViewById(R.id.buttonAdd);

        // Cargar el anuncio
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Redirigir a la actividad de búsqueda al hacer clic en el botón Agregar
        buttonAdd.setOnClickListener(v -> startActivity(new Intent(this, SearchActivity.class)));

        // Obtener el usuario actual desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString("user", "");
        if (!userJson.isEmpty()) {
            Gson gson = new Gson();
            Usuario user = gson.fromJson(userJson, Usuario.class);
            currentUser = user;
        } else {
            Log.e("UserInfo", "Error: No se pudo obtener el usuario desde SharedPreferences");
        }

        orderBy = "default";

        // Iniciar la tarea asincrónica para obtener todas las plantas del usuario
        new SelectAllPlantsByUser(this, recyclerView, emptyMessage).execute(currentUser, orderBy);

        // Configurar el botón de orden
        buttonOrder.setOnClickListener(v -> {
            String buttonText = buttonOrder.getText().toString();
            if (buttonText.equals("Orden: Ascendente")) {
                orderBy = "Descendente";
                buttonOrder.setText("Orden: Descendente");
            } else if (buttonText.equals("Orden: Descendente")) {
                orderBy = "Defecto";
                buttonOrder.setText("Orden: Por defecto");
            } else {
                orderBy = "Ascendente";
                buttonOrder.setText("Orden: Ascendente");
            }
            // Actualizar la lista de plantas según el orden seleccionado
            new SelectAllPlantsByUser(this, recyclerView, emptyMessage).execute(currentUser, orderBy);
        });

        // Configurar el listener para el evento de "deslizar para refrescar"
        swipeRefreshLayout.setOnRefreshListener(() -> {
            recreate();
            swipeRefreshLayout.setRefreshing(false);
        });

        // Configurar la navegación de la barra inferior
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == YOUR_PLANTS_ACTIVITY_BUTTON_ID) {
                    if (!(getApplicationContext() instanceof YourPlantsActivity)) {
                        startActivity(new Intent(getApplicationContext(), YourPlantsActivity.class));
                        finish();
                    } else {
                        // Si ya estamos en la actividad, la recreamos
                        recreate();
                        finish();
                    }
                    return true;
                } else if (item.getItemId() == SEARCH_ACTIVITY_BUTTON_ID) {
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

        // Configurar el botón de alarma para mostrar el diálogo de selección de tiempo
        buttonAlarm.setOnClickListener(v -> showTimePickerDialog());

        // Configurar el botón de ajustes para abrir la actividad de ajustes
        buttonSettings.setOnClickListener(v -> startActivity(new Intent(YourPlantsActivity.this, SettingsActivity.class)));
    }

    // Método para mostrar el diálogo de selección de tiempo para la alarma
    private void showTimePickerDialog() {
        // Obtener la hora y el minuto actuales
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Obtener el tema del diálogo de selección de tiempo según el tema de la aplicación
        SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        int themeId = preferences.getInt("current_theme", R.style.Theme_App_Light_NoActionBar);
        int timePickerDialogThemeId;
        if (themeId == R.style.Theme_App_Light_NoActionBar) {
            timePickerDialogThemeId = R.style.TimePickerDialogTheme_Light;
        } else {
            timePickerDialogThemeId = R.style.TimePickerDialogTheme_Dark;
        }

        // Crear y mostrar el diálogo de selección de tiempo
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                timePickerDialogThemeId,
                (view, hourOfDay, minute1) -> setDailyAlarm(hourOfDay, minute1),
                hour,
                minute,
                true
        );
        timePickerDialog.show();
    }

    // Método para configurar la alarma diaria
    private void setDailyAlarm(int hour, int minute) {
        // Obtener el servicio de alarma
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Crear un objeto Calendar para configurar la hora y el minuto seleccionados
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // Verificar si la hora seleccionada ya ha pasado, en cuyo caso, se programará para el día siguiente
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        // Configurar la alarma para que se repita diariamente a la hora especificada
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Alarma configurada para regar.", Toast.LENGTH_SHORT).show();
    }

    // Método para crear el canal de notificación
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "WateringChannel";
            String description = "Channel for watering notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("watering_channel", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
