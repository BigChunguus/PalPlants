package com.example.palplants.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.palplants.R;

import java.util.ArrayList;

import pojosbotanica.Resena;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context context;
    private ArrayList<Resena> resenaList;

    public ReviewAdapter(Context context, ArrayList<Resena> resenaList) {
        this.context = context;
        this.resenaList = resenaList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_view_layout, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Resena resena = resenaList.get(position);
        holder.textUsername.setText(resena.getUsuarioId().getNombreUsuario());
        holder.textDate.setText(resena.getFechaResena().toString());
        holder.ratingBar.setRating(resena.getCalificacion().floatValue());
    }

    @Override
    public int getItemCount() {
        return resenaList.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView textUsername, textDate;
        RatingBar ratingBar;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            textUsername = itemView.findViewById(R.id.textUsername);
            textDate = itemView.findViewById(R.id.textDate);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
