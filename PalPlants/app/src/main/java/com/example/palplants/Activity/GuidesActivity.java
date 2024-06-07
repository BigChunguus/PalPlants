package com.example.palplants.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.palplants.AsyncTask.DeleteReviewUserTask;
import com.example.palplants.AsyncTask.InsertReviewTask;
import com.example.palplants.AsyncTask.ModifyReviewTask;
import com.example.palplants.R;
import com.example.palplants.Adapter.ReviewAdapter;
import com.example.palplants.AsyncTask.ReadReviewsTask;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import pojosbotanica.Guia;
import pojosbotanica.Resena;
import pojosbotanica.Usuario;

// Esta clase representa la actividad principal para mostrar y gestionar las guías en la aplicación.
// Permite a los usuarios ver y explorar diferentes guías disponibles.
// Los usuarios también pueden acceder a funciones adicionales, como crear nuevas guías o editar las existentes.
public class GuidesActivity extends AppCompatActivity {

    private TextView titleTextView, contentGuideTextView, userNameTextView;
    private RatingBar ratingBar;
    private RecyclerView reviewRecyclerView;
    private ImageButton buttonAddReview, mButtonDropdownMenu;
    private ArrayList<Resena> resenaList;
    private ReviewAdapter reviewAdapter;
    private Usuario usuario;
    private Guia guia;
    private Resena resenaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Establecer el tema basado en las preferencias compartidas
        SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        int themeId = preferences.getInt("current_theme", R.style.Theme_App_Light_NoActionBar);
        setTheme(themeId);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        // Inicializar componentes de la interfaz de usuario
        titleTextView = findViewById(R.id.title);
        contentGuideTextView = findViewById(R.id.contentGuide);
        userNameTextView = findViewById(R.id.userName);
        ratingBar = findViewById(R.id.ratingBar);
        reviewRecyclerView = findViewById(R.id.reviewRecyclerView);
        buttonAddReview = findViewById(R.id.buttonAddReview);
        mButtonDropdownMenu = findViewById(R.id.mButtonDropdownMenu);

        //Recargar la pagina
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            recreate();
            swipeRefreshLayout.setRefreshing(false);
        });

        // Configurar vista de anuncios
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // Configurar botón de retroceso
        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Configurar botón de menú desplegable
        mButtonDropdownMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view, resenaUsuario);
            }
        });

        // Configurar navegación inferior
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
                        recreate();
                        finish();
                    }
                    return true;
                } else if (item.getItemId() == R.id.SearchActivityButton) {
                    if (!(getApplicationContext() instanceof SearchActivity)) {
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        finish();
                    } else {
                        recreate();
                        finish();
                    }
                    return true;
                }
                return false;
            }
        });

        // Obtener la guía del intento
        guia = (Guia) getIntent().getSerializableExtra("GUIDE");

        // Configurar RecyclerView
        resenaList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(this, resenaList);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.setAdapter(reviewAdapter);

        // Obtener el usuario de las preferencias compartidas
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String usuarioJson = sharedPreferences.getString("user", "");
        if (!usuarioJson.isEmpty()) {
            Gson gson = new Gson();
            usuario = gson.fromJson(usuarioJson, Usuario.class);
        }

        // Configurar el botón para agregar una reseña
        buttonAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar si la actividad está activa
                if (!GuidesActivity.this.isFinishing()) {
                    // Crear un diálogo emergente
                    Dialog dialog = new Dialog(GuidesActivity.this);
                    dialog.setContentView(R.layout.dialog_insert_review);

                    // Obtener referencias a los elementos del diálogo
                    RatingBar ratingBar = dialog.findViewById(R.id.ratingBarReview);
                    EditText editTextComment = dialog.findViewById(R.id.editTextComment);
                    Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
                    Button buttonPublish = dialog.findViewById(R.id.buttonPublish);

                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                    // Configurar el botón Cancelar para cerrar el diálogo
                    buttonCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    // Configurar el botón Publicar para llamar a InsertReviewTask
                    buttonPublish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Obtener la calificación y el comentario ingresados por el usuario
                            int rating = (int) ratingBar.getRating();
                            String comment = editTextComment.getText().toString();

                            // Crear una nueva Reseña con los datos proporcionados
                            Resena resena = new Resena();
                            resena.setCalificacion(rating);
                            resena.setComentario(comment);
                            resena.setUsuarioId(usuario);
                            resena.setGuiaId(guia);

                            // Ejecutar la tarea AsyncTask para insertar la reseña
                            new InsertReviewTask(GuidesActivity.this, resena).execute();
                        }
                    });

                    // Mostrar el diálogo
                    dialog.show();
                }
            }
        });

        // Establecer los detalles de la guía
        titleTextView.setText(guia.getTitulo());
        contentGuideTextView.setText(guia.getContenido());
        userNameTextView.setText(guia.getUsuarioId().getNombreUsuario());
        ratingBar.setRating(guia.getCalificacionMedia().floatValue());

        // Cargar las reseñas
        ReadReviewsTask readReviewsTask = new ReadReviewsTask(this, guia.getGuiaId(), usuario.getUsuarioID(), reviewRecyclerView, buttonAddReview, resenaList, reviewAdapter, mButtonDropdownMenu);
        readReviewsTask.setOnResenaUsuarioReceivedListener(new ReadReviewsTask.OnResenaUsuarioReceivedListener() {
            @Override
            public void onResenaUsuarioReceived(Resena resena) {
                resenaUsuario = resena;
            }
        });
        readReviewsTask.execute();
    }

    // Mostrar el menú emergente para editar o eliminar una reseña
    public void showPopupMenu(View view, final Resena resenaUsuario) {
        PopupMenu popup = new PopupMenu(GuidesActivity.this, view);
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.getMenuInflater().inflate(R.menu.menu_more_options, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle().equals("Eliminar")) {
                    showConfirmationDialog(resenaUsuario);
                    return true;
                } else {
                    showEditGuideDialog(resenaUsuario);
                    return true;
                }
            }
        });
        popup.show();
    }
    // Mostrar diálogo de confirmación para eliminar una reseña
    private void showConfirmationDialog(final Resena resenaUsuario) {
        new AlertDialog.Builder(GuidesActivity.this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Está seguro de que desea eliminar esta guía?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new DeleteReviewUserTask(GuidesActivity.this, resenaUsuario).execute();
                        recreate();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    // Mostrar diálogo para editar una reseña
    private void showEditGuideDialog(final Resena resenaUsuario) {
        if (!isFinishing()) {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_insert_review);

            RatingBar ratingBar = dialog.findViewById(R.id.ratingBarReview);
            EditText editTextComment = dialog.findViewById(R.id.editTextComment);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
            Button buttonPublish = dialog.findViewById(R.id.buttonPublish);

            ratingBar.setRating(resenaUsuario.getCalificacion());
            editTextComment.setText(resenaUsuario.getComentario());
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            buttonPublish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int rating = (int) ratingBar.getRating();
                    String comment = editTextComment.getText().toString();

                    if (rating != 0 && !comment.isEmpty()) {
                        Resena resena = new Resena();
                        resena.setCalificacion(rating);
                        resena.setComentario(comment);
                        resena.setResenaId(resenaUsuario.getResenaId());
                        new ModifyReviewTask(GuidesActivity.this, resena).execute();
                        recreate();
                    } else {
                        dialog.dismiss();
                    }
                }
            });

            dialog.show();
        }
    }

}
