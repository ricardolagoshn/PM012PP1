package com.aplicacion.pm012pp1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityPhoto extends AppCompatActivity
{
    static  final int REQUESTCODECAMARA = 100;
    static  final int REQUESTTAKEPHOTO = 101;
    ImageView ObjectImage;
    Button btnfoto;
    String PathFoto;
    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ObjectImage = (ImageView) findViewById(R.id.Fotografia);
        btnfoto = (Button) findViewById(R.id.btnfoto);

        btnfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OtorgarPermisos();
            }
        });
    }

    private void OtorgarPermisos()
    {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.CAMERA }, REQUESTCODECAMARA);
        }
        else
        {
            //TomarFotografia();
            dispatchTakePictureIntent();
        }
    }

    private void TomarFotografia()
    {
        Intent tomarfoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(tomarfoto,REQUESTTAKEPHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUESTCODECAMARA)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                //TomarFotografia();
                dispatchTakePictureIntent();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Permiso denegado", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == REQUESTTAKEPHOTO && resultCode == RESULT_OK)
        {
            /*
            Bundle extraerfoto = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extraerfoto.get("data");
            ObjectImage.setImageBitmap(imageBitmap);
             */

            File foto = new File(currentPhotoPath);
            ObjectImage.setImageURI(Uri.fromFile(foto));
            galleryAddPic();
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.aplicacion.pm012pp1.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUESTTAKEPHOTO);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

}