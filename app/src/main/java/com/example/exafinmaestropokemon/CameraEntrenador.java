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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CameraEntrenador extends AppCompatActivity {
    Button btnCamera;
    Button btnGallery;
    Button btnEnviar;
    ImageView mPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_entrenador);
        solicitarPermisos();

        btnCamera = findViewById(R.id.btnOpenCamara);
        btnGallery = findViewById(R.id.btnGallery);
        mPicture = findViewById(R.id.ivPicture);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,101);
                //startActivityForResult(intent, 101);
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
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
}