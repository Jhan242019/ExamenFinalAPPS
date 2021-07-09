package com.example.exafinmaestropokemon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.exafinmaestropokemon.entities.Pokemon;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class DetallePokemon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pokemon);
        ImageView imagen = findViewById(R.id.imagenPokemon);
        TextView nombre = findViewById(R.id.nombreP);
        TextView tipo = findViewById(R.id.tipoP);

        Intent intent = getIntent();
        String pokemones = intent.getStringExtra("clase");
        Pokemon poke = new Gson().fromJson(pokemones,Pokemon.class);

        nombre.setText(poke.getNombre());
        tipo.setText(poke.getTipo());
        Picasso.get().load(poke.getUrl_imagen()).into(imagen);
    }
}