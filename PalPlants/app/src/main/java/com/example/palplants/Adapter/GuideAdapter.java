package com.example.palplants.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.palplants.R;

import java.util.ArrayList;
import pojosbotanica.Guia;
import pojosbotanica.Usuario;

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.GuideViewHolder> {

    private Context context;
    private ArrayList<Guia> guiasList;

    public GuideAdapter(Context context, ArrayList<Guia> guiasList) {
        this.context = context;
        this.guiasList = guiasList;
    }

    @NonNull
    @Override
    public GuideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.guide_view_layout, parent, false);
        return new GuideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideViewHolder holder, int position) {
        Guia guia = guiasList.get(position);
        holder.textTitulo.setText(guia.getTitulo());
        Usuario usuario = guia.getUsuarioId();
        holder.textUserName.setText(usuario.getNombreUsuario());
        holder.textAverageRate.setText(String.valueOf(guia.getCalificacionMedia()));
    }

    @Override
    public int getItemCount() {
        return guiasList.size();
    }

    public static class GuideViewHolder extends RecyclerView.ViewHolder {
        TextView textTitulo, textUserName, textAverageRate;

        public GuideViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitulo = itemView.findViewById(R.id.textTitulo);
            textUserName = itemView.findViewById(R.id.textUserName);
            textAverageRate = itemView.findViewById(R.id.textAverageRate);
        }
    }
}
