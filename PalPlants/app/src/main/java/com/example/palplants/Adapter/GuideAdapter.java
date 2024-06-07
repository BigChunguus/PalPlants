package com.example.palplants.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.palplants.Activity.GuidesActivity;
import com.example.palplants.R;
import java.util.ArrayList;
import pojosbotanica.Guia;
import pojosbotanica.Usuario;

// Este adaptador se utiliza para mostrar las guías en un RecyclerView en diferentes contextos de la aplicación.
// Se encarga de inflar el diseño de la vista de la guía y vincular los datos de la guía a cada vista de elemento.
public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.GuideViewHolder> {

    private Context context;
    private ArrayList<Guia> guiasList;

    // Constructor de la clase GuideAdapter
    public GuideAdapter(Context context, ArrayList<Guia> guiasList) {
        this.context = context;
        this.guiasList = guiasList;
    }

    // Método llamado cuando RecyclerView necesita un nuevo ViewHolder
    @NonNull
    @Override
    public GuideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño de la vista de la guía
        View view = LayoutInflater.from(context).inflate(R.layout.guide_view_layout, parent, false);
        return new GuideViewHolder(view);
    }

    // Método llamado para mostrar los datos en la posición específica
    @Override
    public void onBindViewHolder(@NonNull GuideViewHolder holder, int position) {
        // Obtener la guía en la posición específica
        Guia guia = guiasList.get(position);
        // Establecer el título de la guía en el TextView correspondiente
        holder.textTitulo.setText(guia.getTitulo());
        // Obtener el usuario asociado a la guía
        Usuario usuario = guia.getUsuarioId();
        // Establecer el nombre de usuario en el TextView correspondiente
        holder.textUserName.setText(usuario.getNombreUsuario());
        // Convertir el valor Double a float y establecerlo en el RatingBar
        holder.ratingBar.setRating(guia.getCalificacionMedia().floatValue());

        // Configurar el OnClickListener para el elemento de la lista
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear una intención para iniciar la actividad GuidesActivity
                Intent intent = new Intent(context, GuidesActivity.class);
                // Pasar datos adicionales a la actividad si es necesario (por ejemplo, el ID de la guía)
                intent.putExtra("GUIDE", guia);
                // Iniciar la actividad
                context.startActivity(intent);
            }
        });
    }

    // Método para obtener el número total de elementos en el conjunto de datos
    @Override
    public int getItemCount() {
        return guiasList.size();
    }

    // Clase interna que representa el ViewHolder para cada elemento en la lista
    public static class GuideViewHolder extends RecyclerView.ViewHolder {
        // Declarar vistas que se mostrarán en cada elemento de la lista
        TextView textTitulo, textUserName;
        RatingBar ratingBar;

        // Constructor de la clase GuideViewHolder
        public GuideViewHolder(@NonNull View itemView) {
            super(itemView);
            // Asociar vistas con sus identificadores en el diseño XML
            textTitulo = itemView.findViewById(R.id.textTitulo);
            textUserName = itemView.findViewById(R.id.textUserName);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
