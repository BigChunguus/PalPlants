package com.example.palplants.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.palplants.R;
import java.util.List;
import pojosbotanica.Insecto;

public class InsectAdapter extends RecyclerView.Adapter<InsectAdapter.ViewHolder> {

    private List<Insecto> insectList;
    private Context context;

    public InsectAdapter(List<Insecto> insectList, Context context) {
        this.insectList = insectList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.insect_view_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Insecto insect = insectList.get(position);
        holder.bind(insect);
    }

    @Override
    public int getItemCount() {
        return insectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCientificNameInsect;
        TextView textViewComunNameInsect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCientificNameInsect = itemView.findViewById(R.id.textViewCientificNameInsect);
            textViewComunNameInsect = itemView.findViewById(R.id.textViewComunNameInsect);
        }

        public void bind(Insecto insect) {
            textViewCientificNameInsect.setText(insect.getNombreCientificoInsecto());
            textViewComunNameInsect.setText(insect.getNombreComunInsecto());
        }
    }
}
