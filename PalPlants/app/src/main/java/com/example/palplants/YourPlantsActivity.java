package com.example.palplants;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Outline;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import java.util.ArrayList;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Planta;
import pojosbotanica.Usuario;

public class YourPlantsActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private ImageButton buttonSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        int themeId = preferences.getInt("current_theme", R.style.Theme_App_Light_NoActionBar);
        setTheme(themeId);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yourplants);

        buttonSettings = findViewById(R.id.buttonSettings);
        linearLayout = findViewById(R.id.linearLayout);

        String nombreUsuario = "";
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String usuarioJson = sharedPreferences.getString("user", "");

        // Comprobar si se pudo obtener el JSON del usuario de las preferencias compartidas
        if (!usuarioJson.isEmpty()) {
            // Convertir el JSON a objeto Usuario
            Gson gson = new Gson();
            Usuario usuario = gson.fromJson(usuarioJson, Usuario.class);

            // Obtener el nombre de usuario del objeto Usuario
            nombreUsuario = usuario.getNombreUsuario();
        } else {
            Log.e("UserInfo", "Error: No se pudo obtener el usuario desde SharedPreferences");
        }

        new ConnectBotanicaTask().execute(nombreUsuario);

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YourPlantsActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }



    // AsyncTask para conectar con el servidor y cargar las plantas del usuario
    private class ConnectBotanicaTask extends AsyncTask<String, Void, ArrayList<Planta>> {
        @Override
        protected ArrayList<Planta> doInBackground(String... params) {
            String username = params[0];
            try {
                BotanicaCC bcc = new BotanicaCC();
                return bcc.leerUsuariosPlantas(username);
            } catch (ExcepcionBotanica ex) {
                ex.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Planta> listaPlantas) {
            if (listaPlantas != null && !listaPlantas.isEmpty()) {
                // Recorre la lista de plantas y crea las tarjetas
                for (Planta planta : listaPlantas) {
                    // Llama al método createCardView para crear la tarjeta de planta y agrégala al LinearLayout
                    ConstraintLayout cardView = createCardView(planta);
                    linearLayout.addView(cardView);
                }
            } else {
                // Maneja la situación de error aquí
                Toast.makeText(YourPlantsActivity.this, "Error al cargar las plantas", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private ConstraintLayout createCardView(Planta planta) {
        // Crear el ConstraintLayout que actuará como tarjeta
        ConstraintLayout cardView = new ConstraintLayout(this);
        cardView.setLayoutParams(new ConstraintLayout.LayoutParams(
                dpToPx(341),  // Ancho deseado en dp convertido a píxeles
                dpToPx(105)
        ));
        cardView.setClipChildren(true);
        // Configurar el fondo personalizado
        cardView.setBackground(ContextCompat.getDrawable(this, R.drawable.custom_edittext));

        int desiredWidth = 100;
        int desiredHeight = 100;

        // Crear un conjunto de parámetros de diseño para la ImageView con tamaño fijo para la anchura y la altura
        ConstraintLayout.LayoutParams layoutParamsSingleImage = new ConstraintLayout.LayoutParams(dpToPx(desiredWidth), dpToPx(desiredHeight));

        ImageView imageView = new ImageView(this);
        imageView.setId(View.generateViewId());
        imageView.setLayoutParams(layoutParamsSingleImage); // Establecer los parámetros de diseño
        imageView.setMaxWidth(dpToPx(desiredWidth)); // Establecer el tamaño máximo para la anchura
        imageView.setMaxHeight(dpToPx(desiredHeight)); // Establecer el tamaño máximo para la altura
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setClipToOutline(true);// Escalar la imagen para que se ajuste al tamaño de la ImageView
        imageView.setPadding(7, 7, 0, 7);

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);

        // Especifica los radios para los bordes redondeados
        gradientDrawable.setCornerRadii(new float[]{dpToPx(40), dpToPx(30), 0, 0, 0, 0, dpToPx(40), dpToPx(30)});
        imageView.setBackground(gradientDrawable);

        Log.e("Nombre Planta", planta.getNombreCientificoPlanta());
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
        textViewTop.setText(planta.getNombreCientificoPlanta()); // Texto deseado
        cardView.addView(textViewTop);

        // Crear y agregar el TextView para el texto inferior
        TextView textViewBottom = new TextView(this);
        textViewBottom.setId(View.generateViewId());
        textViewBottom.setLayoutParams(new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        textViewBottom.setPadding(0, 0, 0, dpToPx(40)); // Padding inferior en dp convertido a píxeles
        textViewBottom.setText(planta.getNombreComunPlanta()); // Texto deseado
        cardView.addView(textViewBottom);

        // Crear y agregar el ImageButton
        ImageButton button = new ImageButton(this);
        button.setId(View.generateViewId());
        ConstraintLayout.LayoutParams buttonParams = new ConstraintLayout.LayoutParams(
                dpToPx(30),  // Ancho deseado en dp convertido a píxeles
                dpToPx(30)   // Altura deseada en dp convertido a píxeles
        );
        buttonParams.rightMargin = 50;
        button.setLayoutParams(buttonParams);
        button.setImageResource(R.drawable.baseline_delete_outline_24); // Establecer el icono
        button.setBackground(ContextCompat.getDrawable(this, R.drawable.boton_eliminar)); // Fondo deseado
        cardView.addView(button);

        // Crear y aplicar restricciones
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(cardView);

        // Restricciones para la ImageView
        constraintSet.connect(imageView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(imageView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(imageView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.setVerticalBias(imageView.getId(), 0.515f);

        // Restricciones para el TextView superior
        constraintSet.connect(textViewTop.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.END);
        constraintSet.connect(textViewTop.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(textViewTop.getId(), ConstraintSet.END, button.getId(), ConstraintSet.START);
        constraintSet.setHorizontalBias(textViewTop.getId(), 0.083f);
        constraintSet.setVerticalBias(textViewTop.getId(), 0.64f);

        // Restricciones para el TextView inferior
        constraintSet.connect(textViewBottom.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.END);
        constraintSet.connect(textViewBottom.getId(), ConstraintSet.TOP, textViewTop.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(textViewBottom.getId(), ConstraintSet.END, button.getId(), ConstraintSet.START);
        constraintSet.connect(textViewBottom.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.setHorizontalBias(textViewBottom.getId(), 0.056f);


        // Restricciones para el ImageButton
        constraintSet.connect(button.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.connect(button.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(button.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.setHorizontalBias(button.getId(), 0.779f);
        constraintSet.setVerticalBias(button.getId(), 0.505f);

        constraintSet.applyTo(cardView);

        // Margen inferior para separar las tarjetas
        int marginBottom = dpToPx(8); // Margen inferior deseado en dp convertido a píxeles
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) cardView.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, marginBottom);
        cardView.setLayoutParams(layoutParams);

        // Manejar clics en la tarjeta
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al hacer clic en la tarjeta
                // Por ejemplo, iniciar una nueva actividad con detalles de la planta
                Intent intent = new Intent(getApplicationContext(), PlantsActivity.class);
                intent.putExtra("PLANT", planta);
                startActivity(intent);
            }
        });

        return cardView;
    }

    // Método para convertir dp a píxeles
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }



}
