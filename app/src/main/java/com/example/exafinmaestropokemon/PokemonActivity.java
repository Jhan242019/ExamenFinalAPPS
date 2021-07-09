package com.example.exafinmaestropokemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.exafinmaestropokemon.adapters.PokemonAdapter;
import com.example.exafinmaestropokemon.entities.Pokemon;
import com.example.exafinmaestropokemon.services.PokemonService;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        RecyclerView rv = findViewById(R.id.rvPokemons);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://upn.lumenes.tk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PokemonService service = retrofit.create(PokemonService.class);
        Call<List<Pokemon>> pokemon = service.getAll();

        pokemon.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                if (response.code() == 200) {
                    List<Pokemon> pokemones = response.body();
                    PokemonAdapter adapter = new PokemonAdapter(pokemones);
                    adapter.OnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(PokemonActivity.this, DetallePokemon.class);
                            String clase =  new Gson().toJson(pokemones.get(rv.getChildAdapterPosition(v)));
                            intent.putExtra("clase",clase);
                            startActivity(intent);
                        }
                    });
                    rv.setAdapter(adapter);
                } else {
                    Log.i("Web", "Conexion fallida");
                }
            }

            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {
                Log.i("Web", "Error del servidor");
            }
        });
    }
}