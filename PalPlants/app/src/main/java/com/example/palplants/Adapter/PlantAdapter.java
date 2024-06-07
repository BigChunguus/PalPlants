package com.example.palplants.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.palplants.Activity.PlantsActivity;
import com.example.palplants.AsyncTask.DeletePlantTask;
import com.example.palplants.R;

import java.util.List;

import pojosbotanica.Planta;
import pojosbotanica.Usuario;

// Este adaptador se utiliza para mostrar las plantas en un RecyclerView en diferentes contextos de la aplicación.
// Se encarga de inflar el diseño de la vista de la planta y vincular los datos de la planta a cada vista de elemento.
public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

    private List<Planta> plantas;
    private Context context;
    private Usuario usuario;

    public PlantAdapter(List<Planta> plantas, Context context, Usuario usuario) {
        this.plantas = plantas;
        this.context = context;
        this.usuario = usuario;
    }

    // Método llamado cuando RecyclerView necesita un nuevo ViewHolder
    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño de la vista de la planta
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.plant_view_layout, parent, false);
        return new PlantViewHolder(view);
    }

    // Método llamado para mostrar los datos en la posición específica
    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        // Obtener la planta en la posición específica
        Planta planta = plantas.get(position);
        // Vincular los datos de la planta con el ViewHolder
        holder.bind(planta);
        // Configurar un OnClickListener para el elemento de la lista
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad PlantsActivity aquí
                Intent intent = new Intent(context, PlantsActivity.class);
                // Puedes pasar datos adicionales a la actividad si es necesario
                intent.putExtra("PLANT", planta); // Ejemplo de pasar el ID de la planta
                context.startActivity(intent);
            }
        });
    }

    // Método para obtener el número total de elementos en el conjunto de datos
    @Override
    public int getItemCount() {
        return plantas.size();
    }

    // Método para eliminar una planta en una posición específica
    public void removePlantAt(int position) {
        plantas.remove(position);
        notifyItemRemoved(position);
    }

    // Método para obtener el contexto
    public Context getContext() {
        return context;
    }

    // Clase interna que representa el ViewHolder para cada elemento en la lista
    public class PlantViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout cardView;
        private ImageView imageView;
        private TextView textViewTop, textViewBottom;
        private ImageButton buttonDelete;

        // Constructor de la clase PlantViewHolder
        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            // Asociar vistas con sus identificadores en el diseño XML
            cardView = itemView.findViewById(R.id.card_view);
            imageView = itemView.findViewById(R.id.image_view);
            textViewTop = itemView.findViewById(R.id.text_view_top);
            textViewBottom = itemView.findViewById(R.id.text_view_bottom);
            buttonDelete = itemView.findViewById(R.id.button_delete);

            // Configurar un OnClickListener para el botón de eliminación
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Crear un diálogo de alerta para confirmar la eliminación de la planta
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("¿Estás seguro de que quieres eliminar esta planta?")
                            .setCancelable(false)
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    int position = getAdapterPosition();
                                    if (position != RecyclerView.NO_POSITION) {
                                        Planta planta = plantas.get(position);
                                        // Ejecutar la tarea asincrónica para eliminar la planta
                                        new DeletePlantTask(PlantAdapter.this, position).execute(usuario, planta);
                                    }
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    // Mostrar el diálogo de alerta
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

        }

        // Método para vincular los datos de la planta con las vistas en el ViewHolder
        public void bind(Planta planta) {
            // Configurar las opciones de carga para Glide
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image);

            // Cargar la imagen de la planta utilizando Glide
            Glide.with(context)
                    .load(planta.getImagen())
                    .apply(requestOptions)
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            // Aplicar el radio de esquina a la imagen
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                imageView.setBackground(resource);
                            } else {
                                imageView.setBackgroundDrawable(resource);
                            }
                            // Establecer el radio de esquina a la ImageView
                            imageView                                    .setClipToOutline(true);
                            imageView.setOutlineProvider(new ViewOutlineProvider() {
                                @Override
                                public void getOutline(View view, Outline outline) {
                                    // Establecer la forma de la vista de recorte como un rectángulo redondeado
                                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 20);
                                }
                            });
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                            // Realizar acciones si la carga se cancela
                        }
                    });

            // Establecer los textos de la planta en las vistas correspondientes
            textViewTop.setText(planta.getNombreCientificoPlanta());
            textViewBottom.setText(planta.getNombreComunPlanta());
        }
    }
}

