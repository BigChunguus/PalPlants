package com.example.palplants.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.palplants.Activity.PlantsActivity;
import com.example.palplants.AsyncTask.DeletePlantTask;
import com.example.palplants.R;

import java.util.List;

import pojosbotanica.Planta;
import pojosbotanica.Usuario;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

    private List<Planta> plantas;
    private Context context;
    private Usuario usuario;

    public PlantAdapter(List<Planta> plantas, Context context, Usuario usuario) {
        this.plantas = plantas;
        this.context = context;
        this.usuario = usuario;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.plant_view_layout, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        Planta planta = plantas.get(position);
        holder.bind(planta);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar PlantsActivity aquí
                Intent intent = new Intent(context, PlantsActivity.class);
                // Puedes pasar datos adicionales a la actividad si es necesario
                intent.putExtra("PLANT", planta); // Ejemplo de pasar el ID de la planta
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return plantas.size();
    }

    public void removePlantAt(int position) {
        plantas.remove(position);
        notifyItemRemoved(position);
    }

    public Context getContext() {
        return context;
    }

    public class PlantViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout cardView;
        private ImageView imageView;
        private TextView textViewTop, textViewBottom;
        private ImageButton buttonDelete;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            imageView = itemView.findViewById(R.id.image_view);
            textViewTop = itemView.findViewById(R.id.text_view_top);
            textViewBottom = itemView.findViewById(R.id.text_view_bottom);
            buttonDelete = itemView.findViewById(R.id.button_delete);

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("¿Estás seguro de que quieres eliminar esta planta?")
                            .setCancelable(false)
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    int position = getAdapterPosition();
                                    if (position != RecyclerView.NO_POSITION) {
                                        Planta planta = plantas.get(position);
                                        new DeletePlantTask(PlantAdapter.this, position).execute(usuario, planta);
                                    }
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

        }

        public void bind(Planta planta) {
            Glide.with(context)
                    .load(planta.getImagen())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(imageView);
            textViewTop.setText(planta.getNombreCientificoPlanta());
            textViewBottom.setText(planta.getNombreComunPlanta());
        }
    }
}

