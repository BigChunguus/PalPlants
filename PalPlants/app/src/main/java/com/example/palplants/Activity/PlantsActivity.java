package com.example.palplants.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.PopupMenu;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.palplants.Adapter.InsectAdapter;
import com.example.palplants.AsyncTask.DeleteGuideUserTask;
import com.example.palplants.AsyncTask.FindPlantRegisteredTask;
import com.example.palplants.AsyncTask.InsertGuideTask;
import com.example.palplants.AsyncTask.InsertPlantTask;
import com.example.palplants.AsyncTask.ModifyGuideTask;
import com.example.palplants.AsyncTask.SelectAllInsectsPlantTask;
import com.example.palplants.AsyncTask.ReadGuidesPlantTask;
import com.example.palplants.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import pojosbotanica.Guia;
import pojosbotanica.Insecto;
import pojosbotanica.Planta;
import pojosbotanica.Usuario;

// Esta clase representa la actividad que muestra los detalles de una planta específica.
// Los usuarios pueden ver información detallada sobre una planta, como su nombre científico, nombre común, imagen y otras características.
// Además, los usuarios pueden interactuar con la planta, como agregarla a su lista de plantas, escribir reseñas y ver reseñas de otros usuarios.
// La actividad también puede proporcionar opciones para que los usuarios realicen acciones relacionadas con la planta, como editarla o eliminarla si son propietarios.
// En resumen, esta actividad es fundamental para proporcionar a los usuarios información detallada y funcionalidades relacionadas con una planta específica.
public class PlantsActivity extends AppCompatActivity {

    private int plantIdToCheck;
    private Planta plantaSeleccionada = new Planta();
    private ImageView imagePlant;
    private ImageButton buttonAdd, buttonAddGuide, mButtonDropdownMenu;
    private TextView comunName, cientificName, description, specificCare;
    private Usuario usuario;
    private RecyclerView recyclerView;
    private Guia guiaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        int themeId = preferences.getInt("current_theme", R.style.Theme_App_Light_NoActionBar);
        setTheme(themeId);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants);

        imagePlant = findViewById(R.id.imagePlant);
        comunName = findViewById(R.id.comunName);
        cientificName = findViewById(R.id.cientificName);
        description = findViewById(R.id.descriptionPlant);
        specificCare = findViewById(R.id.specificCare);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAddGuide = findViewById(R.id.buttonAddGuide);
        mButtonDropdownMenu = findViewById(R.id.mButtonDropdownMenu);
        recyclerView = findViewById(R.id.recyclerView);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            recreate();
            swipeRefreshLayout.setRefreshing(false);
        });

        ImageButton buttonShowInsects = findViewById(R.id.buttonShowInsects);
        buttonShowInsects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ejecutar la tarea para cargar la lista de insectos
                new SelectAllInsectsPlantTask(plantaSeleccionada, new SelectAllInsectsPlantTask.OnInsectsLoadedListener() {
                    @Override
                    public void onInsectsLoaded(List<Insecto> insectList) {
                        // Crear el diálogo personalizado
                        AlertDialog.Builder builder = new AlertDialog.Builder(PlantsActivity.this);
                        View dialogView = getLayoutInflater().inflate(R.layout.dialog_list_insects, null);
                        builder.setView(dialogView);

                        RecyclerView recyclerViewInsects = dialogView.findViewById(R.id.recyclerViewInsects);
                        recyclerViewInsects.setLayoutManager(new LinearLayoutManager(PlantsActivity.this));

                        InsectAdapter insectAdapter = new InsectAdapter(insectList, PlantsActivity.this);
                        recyclerViewInsects.setAdapter(insectAdapter);

                        // Mostrar el diálogo
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }).execute();
            }
        });

        // Configurar el menú desplegable
        mButtonDropdownMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view, guiaUsuario);
            }
        });

        // Cargar el anuncio
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // Configurar la navegación inferior
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
                        // Si ya estamos en la actividad, la recreamos
                        recreate();
                        finish();
                    }
                    return true;
                } else if (item.getItemId() == R.id.SearchActivityButton) {
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

        // Obtener usuario de las SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String usuarioJson = sharedPreferences.getString("user", "");
        if (!usuarioJson.isEmpty()) {
            Gson gson = new Gson();
            usuario = gson.fromJson(usuarioJson, Usuario.class);
        }

        // Configurar el botón de retroceso
        ImageButton buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresar a la actividad anterior
                finish();
            }
        });

        // Configurar el botón de agregar planta
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ejecutar tarea asincrónica para insertar la planta
                new InsertPlantTask(PlantsActivity.this, usuario, plantIdToCheck).execute();
            }
        });

        // Configurar el botón de agregar guía
        buttonAddGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddGuideDialog();
            }
        });

        // Obtener la planta seleccionada del intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("PLANT")) {
            plantaSeleccionada = (Planta) intent.getSerializableExtra("PLANT");
            // Cargar la imagen de la planta utilizando Glide
            Glide.with(this)
                    .load(plantaSeleccionada.getImagen())
                    .placeholder(R.drawable.placeholder_image)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .error(R.drawable.error_image)
                    .override(160, 160)
                    .into(imagePlant);

            // Asignar la información de la planta a los campos correspondientes
            comunName.setText(plantaSeleccionada.getNombreComunPlanta());
            cientificName.setText(plantaSeleccionada.getNombreCientificoPlanta());
            String plantDescription = plantaSeleccionada.getDescripcion();
            if (plantDescription != null && !plantDescription.isEmpty()) {
                description.setText(plantDescription);
            } else {
                description.setText("Sin descripción");
            }

            String plantSpecificCare = plantaSeleccionada.getCuidadosEspecificos();
            if (plantSpecificCare != null && !plantSpecificCare.isEmpty()) {
                specificCare.setText(plantSpecificCare);
            } else {
                specificCare.setText("Sin cuidados específicos");
            }
            plantIdToCheck = plantaSeleccionada.getPlantaId();

            // Verificar si la planta está registrada por el usuario actual
            new FindPlantRegisteredTask(usuario, plantIdToCheck, isPlantRegistered -> {
                // Manejar el resultado
                if (isPlantRegistered) {
                    buttonAdd.setVisibility(View.INVISIBLE);
                    buttonAdd.setEnabled(false);
                } else {
                    buttonAdd.setVisibility(View.VISIBLE);
                    buttonAdd.setEnabled(true);
                }
            }).execute();

            // Obtener las guías asociadas a la planta
            ReadGuidesPlantTask readGuidesPlantTask = new ReadGuidesPlantTask(this, plantIdToCheck, usuario.getUsuarioID(), recyclerView, buttonAddGuide, mButtonDropdownMenu);
            readGuidesPlantTask.execute();

            // Establecer un listener para la guía del usuario actual
            readGuidesPlantTask.setOnGuiaUsuarioReceivedListener(new ReadGuidesPlantTask.OnGuiaUsuarioReceivedListener() {
                @Override
                public void onGuiaUsuarioReceived(Guia guia) {
                    guiaUsuario = guia;
                }
            });
        }
    }

    // Método para mostrar el menú desplegable
    public void showPopupMenu(View view, final Guia guiaUsuario) {
        PopupMenu popup = new PopupMenu(PlantsActivity.this, view);
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
                    showConfirmationDialog(guiaUsuario);
                    return true;
                } else {
                    showEditGuideDialog(guiaUsuario);
                    return true;
                }
            }
        });
        popup.show();
    }

    // Método para mostrar el diálogo de confirmación de eliminación de guía
    private void showConfirmationDialog(final Guia guiaUsuario) {
        new AlertDialog.Builder(PlantsActivity.this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Está seguro de que desea eliminar esta guía?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new DeleteGuideUserTask(PlantsActivity.this, guiaUsuario).execute();
                        recreate();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    // Método para mostrar el diálogo de edición de guía
    private void showEditGuideDialog(final Guia guiaUsuario) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_insert_guide);

        EditText editTextTitle = dialog.findViewById(R.id.editTextTitle);
        EditText editTextContent = dialog.findViewById(R.id.editTextContent);
        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
        Button buttonPublish = dialog.findViewById(R.id.buttonPublish);

        editTextTitle.setText(guiaUsuario.getTitulo());
        editTextContent.setText(guiaUsuario.getContenido());
        // Ajustar el tamaño del diálogo
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
                String title = editTextTitle.getText().toString();
                String content = editTextContent.getText().toString();

                if (!title.isEmpty() && !content.isEmpty()) {
                    Guia guia = new Guia();
                    guia.setGuiaId(guiaUsuario.getGuiaId());
                    guia.setTitulo(title);
                    guia.setContenido(content);
                    new ModifyGuideTask(PlantsActivity.this, guia).execute();
                    recreate();
                } else {
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    // Método para mostrar el diálogo de agregar guía
    private void showAddGuideDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_insert_guide);

        EditText editTextTitle = dialog.findViewById(R.id.editTextTitle);
        EditText editTextContent = dialog.findViewById(R.id.editTextContent);
        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
        Button buttonPublish = dialog.findViewById(R.id.buttonPublish);

        // Ajustar el tamaño del diálogo
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
                String title = editTextTitle.getText().toString();
                String content = editTextContent.getText().toString();

                if (!title.isEmpty() && !content.isEmpty()) {
                    Guia guia = new Guia();
                    guia.setTitulo(title);
                    guia.setContenido(content);
                    guia.setPlantaId(plantaSeleccionada);
                    guia.setUsuarioId(usuario);
                    guia.setCalificacionMedia(null); // or any default value if necessary

                    new InsertGuideTask(PlantsActivity.this, guia).execute();
                    dialog.dismiss();
                } else {
                    // Mostrar un mensaje de error
                    Toast.makeText(PlantsActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }
}


