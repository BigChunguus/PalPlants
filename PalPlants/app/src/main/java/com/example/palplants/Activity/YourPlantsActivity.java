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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.palplants.Adapter.PlantAdapter;
import com.example.palplants.AsyncTask.SelectAllPlants;
import com.example.palplants.Utility.AlarmReceiver;
import com.example.palplants.R;
import com.google.gson.Gson;

import java.util.ArrayList;

import pojosbotanica.Planta;
import pojosbotanica.Usuario;

public class YourPlantsActivity extends AppCompatActivity {

    private static final int REQUEST_NOTIFICATION_PERMISSION = 2;
    private RecyclerView recyclerView;
    private TextView emptyMessage;
    private ImageButton buttonSettings, buttonAlarm, buttonAdd;
    private Usuario currentUser;
    private Button buttonOrder;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String username, orderBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        int themeId = preferences.getInt("current_theme", R.style.Theme_App_Light_NoActionBar);
        setTheme(themeId);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yourplants);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_NOTIFICATION_PERMISSION);
        }
        createNotificationChannel();

        buttonSettings = findViewById(R.id.buttonSettings);
        recyclerView = findViewById(R.id.recyclerView);
        buttonAlarm = findViewById(R.id.buttonAlarm);
        emptyMessage = findViewById(R.id.emptyTextView);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        buttonOrder = findViewById(R.id.buttonOrder);
        buttonAdd = findViewById(R.id.buttonAdd);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonAdd.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SearchActivity.class)));

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString("user", "");

        if (!userJson.isEmpty()) {
            Gson gson = new Gson();
            Usuario user = gson.fromJson(userJson, Usuario.class);
            currentUser = user;
            username = user.getNombreUsuario();

        } else {
            Log.e("UserInfo", "Error: No se pudo obtener el usuario desde SharedPreferences");
        }

        orderBy = "default";

        new SelectAllPlants(this, recyclerView, emptyMessage).execute(currentUser, orderBy);

        buttonOrder.setOnClickListener(v -> {
            String buttonText = buttonOrder.getText().toString();
            if (buttonText.equals("Orden: Ascendente")) {
                orderBy = "Descendente";
                buttonOrder.setText("Orden: Descendente");
            } else if (buttonText.equals("Orden: Descendente")) {
                orderBy = "Defecto";
                buttonOrder.setText("Orden: Pordefecto");
            } else {
                orderBy = "Ascendente";
                buttonOrder.setText("Orden: Ascendente");
            }
            new SelectAllPlants(this, recyclerView, emptyMessage).execute(currentUser, orderBy);
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            recreate();
            swipeRefreshLayout.setRefreshing(false);
        });

        buttonAlarm.setOnClickListener(v -> showTimePickerDialog());

        buttonSettings.setOnClickListener(v -> startActivity(new Intent(YourPlantsActivity.this, SettingsActivity.class)));
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        int themeId = preferences.getInt("current_theme", R.style.Theme_App_Light_NoActionBar);

        int timePickerDialogThemeId;
        if (themeId == R.style.Theme_App_Light_NoActionBar) {
            timePickerDialogThemeId = R.style.TimePickerDialogTheme_Light;
        } else {
            timePickerDialogThemeId = R.style.TimePickerDialogTheme_Dark;
        }

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

    private void setDailyAlarm(int hour, int minute) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Alarma configurada para regar.", Toast.LENGTH_SHORT).show();
    }

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

    public void updatePlantsList(ArrayList<Planta> plantList) {
        PlantAdapter adapter = new PlantAdapter(plantList, this, currentUser);
        recyclerView.setAdapter(adapter);
    }
}
