package com.example.exafinmaestropokemon;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.exafinmaestropokemon.entities.Entrenador;
import com.example.exafinmaestropokemon.services.EntrenadorService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NuevoEntrenador extends AppCompatActivity {
    ImageView mPicture;
    String imgDecodableString;
    EditText imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_entrenador);
        EditText nombre = findViewById(R.id.name);
        EditText pueblo = findViewById(R.id.pueblo);
        imagen = findViewById(R.id.image);
        mPicture = findViewById(R.id.ivEntrenador);

        Button crear = findViewById(R.id.newPokemon);
        Button crearFoto = findViewById(R.id.newImagenEntrenador);

        crear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String name = nombre.getText().toString().trim();
                String pueblos = pueblo.getText().toString().trim();
                String image = imagen.getText().toString().trim();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://upn.lumenes.tk")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                EntrenadorService service = retrofit.create(EntrenadorService.class);

                Entrenador entrenador = new Entrenador();
                entrenador.setNombres(name);
                entrenador.setPueblo(pueblos);
                entrenador.setImagen(image);

                Call<Entrenador> call = service.create(entrenador);

                call.enqueue(new Callback<Entrenador>() {
                    @Override
                    public void onResponse(Call<Entrenador> call, Response<Entrenador> response) {

                    }
                    @Override
                    public void onFailure(Call<Entrenador> call, Throwable t) {
                    }
                });
                Intent intent = new Intent(NuevoEntrenador.this,MainActivity.class);
                startActivity(intent);
            }
        });

        crearFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,102);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("MAIN_APP", "Resultado de intent: " + requestCode);
        if(requestCode == 101 && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            mPicture.setImageBitmap(bitmap);
        }

        if(requestCode == 102 && resultCode == RESULT_OK)//es necesario para transformar la imagen a un bitmap para poder setearlo a nuestro view
        {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);

            mPicture.setImageBitmap(bitmap);
        }

        try {
            if (resultCode == RESULT_OK) {
                Uri path = data.getData();
                mPicture.setImageURI(path);

                final InputStream imageStream = getContentResolver().openInputStream(path);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imgDecodableString = encodeImage(selectedImage);
                Log.i("MY_APP", "Despues de codificar: "+imgDecodableString);

            }else {
                Toast.makeText(this, "No haz escogido una imagen",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Algo salio mal", Toast.LENGTH_LONG)
                    .show();
        }
        Log.i("MY_APP", String.valueOf(resultCode));

    }


    private void solicitarPermisos() {
        if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)// sino esta otorgado el permiso
        {
            requestPermissions(new String[] {Manifest.permission.CAMERA}, 10001);
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)// sino esta otorgado el permiso
        {
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 10001);
        }
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        imgDecodableString = Base64.encodeToString(b, Base64.DEFAULT);
        imagen.setText(imgDecodableString);
        return imgDecodableString;
    }
}