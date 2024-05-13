package com.example.palplants;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.material.tabs.TabLayout;
import com.example.palplants.ViewPagerAdapter;
import com.example.palplants.LoginActivity;
import com.example.palplants.YourPlantsActivity;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter adapter;
    private TextView textViewSignupOrLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Comprobar si hay información de usuario guardada en SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Borra todas las preferencias
        editor.apply(); // Aplica los cambios
        String usuarioJson = sharedPreferences.getString("user", "");

        if (!usuarioJson.isEmpty()) {
            // Si hay información de usuario, redirigir a YourPlantsActivity
            Intent intent = new Intent(MainActivity.this, YourPlantsActivity.class);
            startActivity(intent);
            finish(); // Evitar que el usuario vuelva a MainActivity
            return; // Salir del método onCreate
        }

        // Si no hay información de usuario, continuar con el flujo normal de la actividad
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        textViewSignupOrLogin = findViewById(R.id.textViewSignupOrLogin);

        tabLayout.addTab(tabLayout.newTab().setText("Loguear"));
        tabLayout.addTab(tabLayout.newTab().setText("Registrar"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new ViewPagerAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(adapter);

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
