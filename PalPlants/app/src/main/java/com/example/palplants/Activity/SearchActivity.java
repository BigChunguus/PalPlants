package com.example.palplants.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.palplants.R;

import java.util.ArrayList;

import botanicacc.BotanicaCC;
import pojosbotanica.ExcepcionBotanica;
import pojosbotanica.Planta;

public class SearchActivity extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        int themeId = preferences.getInt("current_theme", R.style.Theme_App_Light_NoActionBar);
        setTheme(themeId);

        setContentView(R.layout.activity_search);
        linearLayout = findViewById(R.id.linearLayout);
        new ReadAllPlants().execute();
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
        textViewTopParams.leftMargin = dpToPx(16);
        textViewTopParams.topMargin = dpToPx(20);
        textViewTop.setLayoutParams(textViewTopParams);
        textViewTop.setText(planta.getNombreCientificoPlanta());
        cardView.addView(textViewTop);

        TextView textViewBottom = new TextView(this);
        textViewBottom.setId(View.generateViewId());
        ConstraintLayout.LayoutParams textViewBottomParams = new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        textViewBottomParams.leftMargin = dpToPx(16);
        textViewBottom.setLayoutParams(textViewBottomParams);
        textViewBottom.setText(planta.getNombreComunPlanta());
        cardView.addView(textViewBottom);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(cardView);

        // Constraints for the ImageView
        constraintSet.connect(imageView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, ConstraintSet.START);
        constraintSet.connect(imageView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, dpToPx(8));
        constraintSet.connect(imageView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, dpToPx(8));

        // Constraints for the top TextView
        constraintSet.connect(textViewTop.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.END, dpToPx(16));
        constraintSet.connect(textViewTop.getId(), ConstraintSet.TOP, imageView.getId(), ConstraintSet.TOP);
        constraintSet.connect(textViewTop.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, dpToPx(16));

        // Constraints for the bottom TextView
        constraintSet.connect(textViewBottom.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.END, dpToPx(16));
        constraintSet.connect(textViewBottom.getId(), ConstraintSet.TOP, textViewTop.getId(), ConstraintSet.BOTTOM, dpToPx(8));
        constraintSet.connect(textViewBottom.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, dpToPx(16));

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

    private class ReadAllPlants extends AsyncTask<Void, Void, ArrayList<Planta>> {

        @Override
        protected ArrayList<Planta> doInBackground(Void... voids) {
            try {
                BotanicaCC botanicaCC = new BotanicaCC();
                return botanicaCC.leerPlantas();
            } catch (ExcepcionBotanica e) {
                throw new RuntimeException(e);
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
                ConstraintLayout emptyCardView = new ConstraintLayout(SearchActivity.this);
                emptyCardView.setLayoutParams(new ConstraintLayout.LayoutParams(
                        dpToPx(340),
                        dpToPx(50)
                ));

                emptyCardView.setBackground(ContextCompat.getDrawable(SearchActivity.this, R.drawable.custom_edittext));

                TextView textView = new TextView(SearchActivity.this);
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
}
