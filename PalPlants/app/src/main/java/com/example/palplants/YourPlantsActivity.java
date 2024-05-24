package com.example.palplants;

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
import android.graphics.Outline;
import android.graphics.drawable.GradientDrawable;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.palplants.PlantsActivity;
import com.example.palplants.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Planta;
import pojosbotanica.Usuario;

public class YourPlantsActivity extends AppCompatActivity {

    private static final int REQUEST_NOTIFICATION_PERMISSION = 2;
    private LinearLayout linearLayout;
    private ImageButton buttonSettings, buttonAlarm, buttonAdd;
    private Usuario usuarioGeneral;
    private Button buttonOrder;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ScrollView scrollView;
    private String nombreUsuario, strAux;
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
        linearLayout = findViewById(R.id.linearLayout);
        buttonAlarm = findViewById(R.id.buttonAlarm);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        scrollView = findViewById(R.id.scrollView);
        buttonOrder = findViewById(R.id.buttonOrder);
        buttonAdd = findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);

                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String usuarioJson = sharedPreferences.getString("user", "");

        if (!usuarioJson.isEmpty()) {
            Gson gson = new Gson();
            Usuario usuario = gson.fromJson(usuarioJson, Usuario.class);
            usuarioGeneral = usuario;
            nombreUsuario = usuario.getNombreUsuario();
        } else {
            Log.e("UserInfo", "Error: No se pudo obtener el usuario desde SharedPreferences");
        }

        strAux = "defecto";
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoBoton = buttonOrder.getText().toString();

                if (textoBoton.equals("Orden: Ascendente")) {
                    strAux = "Descendente";
                    buttonOrder.setText("Orden: Descendente");
                } else if (textoBoton.equals("Orden: Descendente")) {
                    strAux = "Defecto";
                    buttonOrder.setText("Orden: Pordefecto");
                } else {
                    strAux = "Ascendente";
                    buttonOrder.setText("Orden: Ascendente");
                }

                new ConnectBotanicaTask().execute(nombreUsuario, strAux);
            }
        });
        new ConnectBotanicaTask().execute(nombreUsuario, strAux);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        buttonAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });


        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YourPlantsActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private class ConnectBotanicaTask extends AsyncTask<String, Void, ArrayList<Planta>> {
        @Override
        protected ArrayList<Planta> doInBackground(String... params) {
            String username = params[0];
            String order = params.length > 1 ? params[1] : null;
            try {
                Log.e("Orden", order);
                BotanicaCC bcc = new BotanicaCC();
                ArrayList<Planta> plantas = bcc.leerUsuariosPlantas(username);
                if (order != null && !order.isEmpty()) {
                    if (order.equalsIgnoreCase("Ascendente")) {
                        plantas.sort(Comparator.comparing(Planta::getNombreComunPlanta));
                        Log.e("Orden", plantas.toString());
                    } else if (order.equalsIgnoreCase("Descendente")) {
                        plantas.sort(Comparator.comparing(Planta::getNombreComunPlanta));
                        Collections.reverse(plantas);
                        Log.e("Orden", plantas.toString());
                    }
                }

                return plantas;
            } catch (ExcepcionBotanica ex) {
                ex.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Planta> listaPlantas) {

            linearLayout.removeAllViews();
            if (listaPlantas != null && !listaPlantas.isEmpty()) {

                for (Planta planta : listaPlantas) {
                    ConstraintLayout cardView = createCardView(planta);
                    linearLayout.addView(cardView);
                }
            } else {
                ConstraintLayout emptyCardView = new ConstraintLayout(YourPlantsActivity.this);
                emptyCardView.setLayoutParams(new ConstraintLayout.LayoutParams(
                        dpToPx(340),
                        dpToPx(50)
                ));

                emptyCardView.setBackground(ContextCompat.getDrawable(YourPlantsActivity.this, R.drawable.custom_edittext));

                TextView textView = new TextView(YourPlantsActivity.this);
                textView.setId(View.generateViewId());
                textView.setText("Aún no has añadido ninguna planta");

                emptyCardView.addView(textView);

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(emptyCardView);
                constraintSet.connect(textView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                constraintSet.connect(textView.getId(), ConstraintSet.END,                ConstraintSet.PARENT_ID, ConstraintSet.END);
                constraintSet.connect(textView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                constraintSet.connect(textView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                constraintSet.applyTo(emptyCardView);

                linearLayout.addView(emptyCardView);
            }
        }
    }


    private ConstraintLayout createCardView(Planta planta) {
        ConstraintLayout cardView = new ConstraintLayout(this);
        cardView.setLayoutParams(new ConstraintLayout.LayoutParams(
                dpToPx(341),
                dpToPx(105)
        ));
        cardView.setClipChildren(true);
        cardView.setBackground(ContextCompat.getDrawable(this, R.drawable.custom_edittext));

        int desiredWidth = 100;
        int desiredHeight = 100;

        ConstraintLayout.LayoutParams layoutParamsSingleImage = new ConstraintLayout.LayoutParams(dpToPx(desiredWidth), dpToPx(desiredHeight));

        ImageView imageView = new ImageView(this);
        imageView.setId(View.generateViewId());
        imageView.setLayoutParams(layoutParamsSingleImage);
        imageView.setMaxWidth(dpToPx(desiredWidth));
        imageView.setMaxHeight(dpToPx(desiredHeight));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setClipToOutline(true);
        imageView.setPadding(7, 7, 0, 7);

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);

        gradientDrawable.setCornerRadii(new float[]{dpToPx(40), dpToPx(30), 0, 0, 0, 0, dpToPx(40), dpToPx(30)});
        imageView.setBackground(gradientDrawable);

        // Mariana
        // Url de ejemplo: https://drive.google.com/uc?export=view&id=1MlDb1H-V2DyI32ncplsbtyYaI_KFGD8S

        Glide.with(this)
                .load(planta.getImagen())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(imageView);
        cardView.addView(imageView);



        TextView textViewTop = new TextView(this);
        textViewTop.setId(View.generateViewId());

        ConstraintLayout.LayoutParams textViewTopParams = new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        textViewTopParams.topMargin = 50;
        textViewTop.setLayoutParams(textViewTopParams);
        textViewTop.setText(planta.getNombreCientificoPlanta());
        cardView.addView(textViewTop);

        TextView textViewBottom = new TextView(this);
        textViewBottom.setId(View.generateViewId());
        textViewBottom.setLayoutParams(new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        textViewBottom.setPadding(0, 0, 0, dpToPx(40));
        textViewBottom.setText(planta.getNombreComunPlanta());
        cardView.addView(textViewBottom);

        ImageButton buttonDelete = new ImageButton(this);
        buttonDelete.setId(View.generateViewId());
        ConstraintLayout.LayoutParams buttonParams = new ConstraintLayout.LayoutParams(
                dpToPx(30),
                dpToPx(30)
        );
        buttonParams.rightMargin = 50;
        buttonDelete.setLayoutParams(buttonParams);
        buttonDelete.setImageResource(R.drawable.baseline_delete_outline_24);
        buttonDelete.setBackground(ContextCompat.getDrawable(this, R.drawable.boton_eliminar));

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DeletePlantTask().execute(usuarioGeneral, planta);
            }
        });

        cardView.addView(buttonDelete);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(cardView);

        constraintSet.connect(imageView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(imageView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(imageView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.setVerticalBias(imageView.getId(), 0.515f);

        constraintSet.connect(textViewTop.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.END);
        constraintSet.connect(textViewTop.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(textViewTop.getId(), ConstraintSet.END, buttonDelete.getId(), ConstraintSet.START);
        constraintSet.setHorizontalBias(textViewTop.getId(), 0.083f);
        constraintSet.setVerticalBias(textViewTop.getId(), 0.64f);

        constraintSet.connect(textViewBottom.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.END);
        constraintSet.connect(textViewBottom.getId(), ConstraintSet.TOP, textViewTop.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(textViewBottom.getId(), ConstraintSet.END, buttonDelete.getId(), ConstraintSet.START);
        constraintSet.connect(textViewBottom.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.setHorizontalBias(textViewBottom.getId(), 0.056f);


        constraintSet.connect(buttonDelete.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.connect(buttonDelete.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(buttonDelete.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.setHorizontalBias(buttonDelete.getId(), 0.779f);
        constraintSet.setVerticalBias(buttonDelete.getId(), 0.505f);

        constraintSet.applyTo(cardView);

        int marginBottom = dpToPx(8);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) cardView.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, marginBottom);
        cardView.setLayoutParams(layoutParams);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlantsActivity.class);
                intent.putExtra("PLANT", planta);
                startActivity(intent);
            }
        });

        return cardView;
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
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
    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        int themeId = preferences.getInt("current_theme", R.style.Theme_App_Light_NoActionBar);

        // Determina el estilo del TimePickerDialog según el tema seleccionado
        int timePickerDialogThemeId;
        if (themeId == R.style.Theme_App_Light_NoActionBar) {
            timePickerDialogThemeId = R.style.TimePickerDialogTheme_Light;
        } else {
            timePickerDialogThemeId = R.style.TimePickerDialogTheme_Dark;
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                timePickerDialogThemeId, // Aquí aplicas el estilo personalizado
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        setDailyAlarm(hourOfDay, minute);
                    }
                },
                hour,
                minute,
                true
        );

        timePickerDialog.show();
    }



    private void setDailyAlarm(int hour, int minute) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(this, "Alarma configurada para regar. ", Toast.LENGTH_SHORT).show();
    }

    private class DeletePlantTask extends AsyncTask<Object, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Object... params) {
            Usuario user = (Usuario) params[0];
            Planta planta = (Planta) params[1];
            try {
                BotanicaCC bcc = new BotanicaCC();
                int cambios = bcc.eliminarUsuarioPlanta(user.getUsuarioID(), planta.getPlantaId());
                if(cambios == 1)
                    return true;
                else
                    return false;
            } catch (ExcepcionBotanica ex) {
                ex.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                recreate();
            } else {
                Toast.makeText(YourPlantsActivity.this, "Error al eliminar la planta", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

