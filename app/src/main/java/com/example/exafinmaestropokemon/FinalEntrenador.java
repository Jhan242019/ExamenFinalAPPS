package com.example.exafinmaestropokemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exafinmaestropokemon.adapters.EntrenadorAdapter;
import com.example.exafinmaestropokemon.entities.Entrenador;
import com.example.exafinmaestropokemon.services.EntrenadorService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FinalEntrenador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_entrenador);
        Button listar = findViewById(R.id.myList);
        Button registrar = findViewById(R.id.register);
        ImageView image = findViewById(R.id.imagenEtrenador);
        TextView nombres = findViewById(R.id.nombreE);
        TextView pueblos = findViewById(R.id.pueblo);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://upn.lumenes.tk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EntrenadorService service = retrofit.create(EntrenadorService.class);
        Call<Entrenador> entrenador2 = service.getAll();

        entrenador2.enqueue(new Callback<Entrenador>() {
            @Override
            public void onResponse(Call<Entrenador> call, Response<Entrenador> response) {
                if(response.code() == 200){
                    Log.i("Web","Conexion todo ok :D");
                    Entrenador entrenador3  = response.body();
                    nombres.setText(entrenador3.getNombres());
                    pueblos.setText(entrenador3.getPueblo());

                    Picasso.get().load( entrenador3.getImagen()).into(image);

                }else {
                    Log.i("Web","Conexion nada ok F");
                }
            }

            @Override
            public void onFailure(Call<Entrenador> call, Throwable t) {
                Log.i("Web","NO pudimos conectarnos al servidor");
            }
        });

        listar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinalEntrenador.this,PokemonActivity.class);
                startActivity(intent);
            }
        });
        registrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinalEntrenador.this,NuevoPokemon.class);
                startActivity(intent);
            }
        });
    }
}