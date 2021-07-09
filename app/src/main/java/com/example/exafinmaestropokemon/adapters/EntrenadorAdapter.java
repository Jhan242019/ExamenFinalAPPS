package com.example.exafinmaestropokemon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exafinmaestropokemon.R;
import com.example.exafinmaestropokemon.entities.Entrenador;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EntrenadorAdapter
        extends RecyclerView.Adapter<EntrenadorAdapter.EntrenadorViewHolder>
        implements View.OnClickListener {

    List<Entrenador> Data;
    private View.OnClickListener clickListener;

    public  EntrenadorAdapter (List<Entrenador> Data){
        this.Data = Data;
    }

    public void OnClickListener(View.OnClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null)
            clickListener.onClick(v);
    }
    @NonNull
    @Override
    public EntrenadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entrenador,parent,false);
        v.setOnClickListener(this);
        EntrenadorAdapter.EntrenadorViewHolder viewHolder = new EntrenadorAdapter.EntrenadorViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EntrenadorViewHolder holder, int position) {
        TextView name = holder.itemView.findViewById(R.id.nombreE);
        TextView pueblo = holder.itemView.findViewById(R.id.pueblo);
        ImageView image = holder.itemView.findViewById(R.id.imagenEtrenador);
        name.setText(String.valueOf(Data.get(position).getNombres()));
        pueblo.setText(String.valueOf(Data.get(position).getPueblo()));
        Picasso.get().load(String.valueOf(Data.get(position).getImagen())).into(image);
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class EntrenadorViewHolder extends RecyclerView.ViewHolder {
        public EntrenadorViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
