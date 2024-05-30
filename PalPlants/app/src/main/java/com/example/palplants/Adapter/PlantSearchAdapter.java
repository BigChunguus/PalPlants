package com.example.palplants.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.palplants.Activity.PlantsActivity;
import com.example.palplants.R;

import java.util.List;

import pojosbotanica.Planta;

public class PlantSearchAdapter extends RecyclerView.Adapter<PlantSearchAdapter.PlantSearchViewHolder> {

    private List<Planta> plantas;
    private Context context;

    public PlantSearchAdapter(List<Planta> plantas, Context context) {
        this.plantas = plantas;
        this.context = context;
    }

    @NonNull
    @Override
    public PlantSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.plant_search_view_layout, parent, false);
        return new PlantSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantSearchViewHolder holder, int position) {
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

    public class PlantSearchViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout cardView;
        private ImageView imageView;
        private TextView textViewTop, textViewBottom;

        public PlantSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            imageView = itemView.findViewById(R.id.image_view);
            textViewTop = itemView.findViewById(R.id.text_view_top);
            textViewBottom = itemView.findViewById(R.id.text_view_bottom);
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
