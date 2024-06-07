package com.example.palplants.Utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.palplants.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // Obtener la hora actual
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String horaActual = sdf.format(calendar.getTime());

        // Construir la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "watering_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("¡Hora de regar tus plantas!")
                .setContentText("¡Es hora de regar tus plantas!")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // Administrador de notificaciones
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // Mostrar la notificación
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Verificar si se tienen los permisos necesarios
            // Si no se tienen los permisos, retornar sin mostrar la notificación
            return;
        }
        notificationManager.notify(1, builder.build());
    }

    // Método para cancelar la alarma
    public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
