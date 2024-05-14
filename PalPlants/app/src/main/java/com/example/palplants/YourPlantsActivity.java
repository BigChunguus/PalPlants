package com.example.palplants;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Outline;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yourplants);

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

        // Crear una instancia de ConnectBotanicaTask y ejecutarla
        new ConnectBotanicaTask().execute(nombreUsuario);
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
                ex.printStackTrace(); // Maneja el error apropiadamente
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
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        // Configurar el fondo personalizado
        cardView.setBackground(ContextCompat.getDrawable(this, R.drawable.custom_edittext));

        int desiredWidth = 500;
        int desiredHeight = 400;

        // Crear un conjunto de parámetros de diseño para la ImageView con tamaño fijo para la anchura y la altura
        LinearLayout.LayoutParams layoutParamsSingleImage = new LinearLayout.LayoutParams(desiredWidth, desiredHeight);

        ImageView imageView = new ImageView(this);
        imageView.setId(View.generateViewId());
        imageView.setLayoutParams(layoutParamsSingleImage); // Establecer los parámetros de diseño
        imageView.setMaxWidth(desiredWidth); // Establecer el tamaño máximo para la anchura
        imageView.setMaxHeight(desiredHeight); // Establecer el tamaño máximo para la altura
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP); // Escalar la imagen para que se ajuste al tamaño de la ImageView

        Log.e("Nombre Planta", planta.getNombreCientificoPlanta());
        Glide.with(this)
                .load(planta.getImagen())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(imageView);
        cardView.addView(imageView);

        // Agrega el radio a la esquina izquierda de la ImageView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setClipToOutline(true);
            imageView.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    int radius = getResources().getDimensionPixelSize(R.dimen.left_corner_radius);
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
                }
            });
        }

        // Crear y agregar un TextView para mostrar el nombre común
        TextView nombreComunTextView = new TextView(this);
        nombreComunTextView.setId(View.generateViewId());
        nombreComunTextView.setLayoutParams(new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        nombreComunTextView.setTextSize(20);
        nombreComunTextView.setText(planta.getNombreComunPlanta());
        cardView.addView(nombreComunTextView);

        // Crear y agregar un TextView para mostrar el nombre científico
        TextView nombreCientificoTextView = new TextView(this);
        nombreCientificoTextView.setId(View.generateViewId());
        nombreCientificoTextView.setLayoutParams(new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        nombreCientificoTextView.setTextSize(16);
        nombreCientificoTextView.setText(planta.getNombreCientificoPlanta());
        cardView.addView(nombreCientificoTextView);


        ImageButton button = new ImageButton(this);
        button.setId(View.generateViewId());
        button.setLayoutParams(new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.boton_eliminar));
        } else {
            button.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.boton_eliminar));
        }
        button.setImageResource(R.drawable.baseline_delete_outline_24);
        cardView.addView(button);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(cardView);
        // Restricciones para la imagen
        constraintSet.connect(imageView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(imageView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(imageView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        // Restricciones para el nombre común
        constraintSet.connect(nombreComunTextView.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.END);
        constraintSet.connect(nombreComunTextView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(nombreComunTextView.getId(), ConstraintSet.END, button.getId(), ConstraintSet.START);
        // Restricciones para el nombre científico
        constraintSet.connect(nombreCientificoTextView.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.END);
        constraintSet.connect(nombreCientificoTextView.getId(), ConstraintSet.TOP, nombreComunTextView.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(nombreCientificoTextView.getId(), ConstraintSet.END, button.getId(), ConstraintSet.START);
        // Restricciones para el botón
        constraintSet.connect(button.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.connect(button.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(button.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.applyTo(cardView);



        int padding = getResources().getDimensionPixelSize(R.dimen.card_padding);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) cardView.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, padding);
        cardView.setLayoutParams(layoutParams);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para iniciar PlantsActivity
                Intent intent = new Intent(getApplicationContext(), PlantsActivity.class);
                // Pasar el ID de la planta como extra en el Intent
                intent.putExtra("PLANT", planta);
                startActivity(intent);
            }
        });

        return cardView;
    }

}
