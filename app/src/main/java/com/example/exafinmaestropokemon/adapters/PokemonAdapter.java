package com.example.exafinmaestropokemon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exafinmaestropokemon.R;
import com.example.exafinmaestropokemon.entities.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>
        implements View.OnClickListener{

    List<Pokemon> Data;
    private View.OnClickListener clickListener;

    public  PokemonAdapter (List<Pokemon> Data){
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
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon,parent,false);
        v.setOnClickListener(this);
        PokemonAdapter.PokemonViewHolder viewHolder = new PokemonAdapter.PokemonViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        TextView name = holder.itemView.findViewById(R.id.nombrePokemon);
        TextView tipo = holder.itemView.findViewById(R.id.tipoPokemon);
        ImageView image = holder.itemView.findViewById(R.id.imagePokemon);

        name.setText((Data.get(position).getNombre()));
        tipo.setText((Data.get(position).getTipo()));
        Picasso.get().load((Data.get(position).getUrl_imagen())).into(image);

    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {
        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
