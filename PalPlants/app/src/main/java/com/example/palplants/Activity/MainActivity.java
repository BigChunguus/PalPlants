package com.example.palplants.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.palplants.R;
import com.example.palplants.Adapter.ViewPagerAdapter;
import com.example.palplants.AsyncTask.VerifyUserTask;
import com.example.palplants.Utility.VerifyUserCallback;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import pojosbotanica.Usuario;

// Esta clase representa la actividad principal de la aplicación.
// Funciona como la puerta de entrada principal para los usuarios después de iniciar sesión.
// Aquí, los usuarios pueden acceder a diversas funciones de la aplicación, como ver sus plantas, explorar guías y realizar otras acciones relacionadas con la jardinería y las plantas.
// Además, esta actividad maneja la verificación del usuario, asegurándose de que el usuario esté autenticado antes de acceder al contenido principal de la aplicación.
public class MainActivity extends AppCompatActivity implements VerifyUserCallback {

    private static final int REQUEST_NOTIFICATION_PERMISSION = 1;

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter adapter;
    private TextView textViewSignupOrLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isNetworkAvailable()) {
            showNoConnectionDialog();
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_NOTIFICATION_PERMISSION);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String usuarioJson = sharedPreferences.getString("user", "");

        if (!usuarioJson.isEmpty()) {
            Gson gson = new Gson();
            Usuario usuario = gson.fromJson(usuarioJson, Usuario.class);
            new VerifyUserTask(this).execute(usuario.getNombreUsuario());
        } else {
            setupDefaultView();
        }
    }

    // Método para manejar el resultado de la verificación del usuario
    @Override
    public void onVerifyUserResult(boolean result) {
        if (result) {
            // Si el usuario está verificado, inicia YourPlantsActivity
            Intent intent = new Intent(MainActivity.this, YourPlantsActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Si hay un error al verificar, muestra un mensaje y restablece la vista predeterminada
            Toast.makeText(MainActivity.this, "Error al verificar el usuario. Por favor, intente de nuevo.", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            setupDefaultView();
        }
    }

    // Método para verificar la disponibilidad de la red
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // Método para mostrar un diálogo de conexión no disponible
    private void showNoConnectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sin conexión a Internet")
                .setMessage("No hay conexión a Internet. Por favor, inténtelo de nuevo más tarde.")
                .setPositiveButton("Reintentar", (dialog, which) -> {
                    dialog.dismiss();
                    recreate();
                })
                .setNegativeButton("Cerrar", (dialog, which) -> {
                    finish();
                })
                .setCancelable(false)
                .show();
    }

    // Método para manejar el resultado de la solicitud de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "El permiso para notificaciones es necesario", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Método para configurar la vista predeterminada
    private void setupDefaultView() {
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        textViewSignupOrLogin = findViewById(R.id.textViewSignupOrLogin);

        // Añadir pestañas al TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Loguear"));
        tabLayout.addTab(tabLayout.newTab().setText("Registrar"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new ViewPagerAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(adapter);

        // Cambiar el texto según la pestaña seleccionada
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    textViewSignupOrLogin.setText("¡Bienvenido de vuelta!\nLogueate");
                } else {
                    textViewSignupOrLogin.setText("¡Encantados de tenerte!\nRegistrate");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
