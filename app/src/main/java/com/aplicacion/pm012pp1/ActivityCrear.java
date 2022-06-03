package com.aplicacion.pm012pp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aplicacion.pm012pp1.Procesos.SQLiteConexion;
import com.aplicacion.pm012pp1.Procesos.Transacciones;

public class ActivityCrear extends AppCompatActivity {

    Button btnagregar;
    EditText txtnombres, txtapellidos, txtedad, txtcorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);

        /* Casteo de elementos de la interfaz grafica */
        init();

        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarEmpleado();
            }
        });

     }

     private void init()
     {
         txtnombres = (EditText) findViewById(R.id.txtnombres);
         txtapellidos = (EditText) findViewById(R.id.txtacapellidos);
         txtedad = (EditText) findViewById(R.id.txtedad);
         txtcorreo = (EditText) findViewById(R.id.txtcorreo);
         btnagregar = (Button) findViewById(R.id.btnagregar);
     }

    private void AgregarEmpleado()
    {
        /* Conexion e Inserccion a la base de datos */
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDataBase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.nombres, txtnombres.getText().toString());
        valores.put(Transacciones.apellidos, txtapellidos.getText().toString());
        valores.put(Transacciones.edad, txtedad.getText().toString());
        valores.put(Transacciones.correo, txtcorreo.getText().toString());

        Long resultado = db.insert(Transacciones.tablaEmpleados, Transacciones.id, valores);

        Toast.makeText(getApplicationContext(), "Registro Ingresado", Toast.LENGTH_LONG).show();

        db.close();

        ClearScreen();
    }

    private void ClearScreen()
    {
        txtnombres.setText("");
        txtapellidos.setText("");
        txtedad.setText("");
        txtcorreo.setText("");
    }
}