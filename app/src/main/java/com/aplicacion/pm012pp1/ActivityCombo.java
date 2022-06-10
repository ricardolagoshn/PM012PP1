package com.aplicacion.pm012pp1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.aplicacion.pm012pp1.Procesos.Empleados;
import com.aplicacion.pm012pp1.Procesos.SQLiteConexion;
import com.aplicacion.pm012pp1.Procesos.Transacciones;

import java.util.ArrayList;

public class ActivityCombo extends AppCompatActivity {

    SQLiteConexion conexion;
    Spinner comboEmpleado;
    EditText nombres, apellidos, edad, correo;

    ArrayList<Empleados> listaempleados;
    ArrayList<String> ArregloEmpleados;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo);

        nombres = (EditText) findViewById(R.id.txtaconnombres);
        apellidos = (EditText) findViewById(R.id.txtaconapellidos);
        edad = (EditText) findViewById(R.id.txtaconedad);
        correo = (EditText) findViewById(R.id.txtaconemail);
        comboEmpleado = (Spinner) findViewById(R.id.comboPersona);

        conexion = new SQLiteConexion(this, Transacciones.NameDataBase, null, 1);

        ObtenerListaEmpleados();

        ArrayAdapter<CharSequence> adp = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ArregloEmpleados);
        comboEmpleado.setAdapter(adp);
    }


    private void ObtenerListaEmpleados()
    {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Empleados empleado = null;
        listaempleados = new ArrayList<Empleados>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Transacciones.tablaEmpleados, null);

        while(cursor.moveToNext())
        {
            empleado = new Empleados();
            empleado.setId(cursor.getInt(0));
            empleado.setNombres(cursor.getString(1));
            empleado.setApellidos(cursor.getString(2));
            empleado.setEdad(cursor.getInt(3));
            empleado.setCorreo(cursor.getString(4));
            listaempleados.add(empleado);
        }

        cursor.close();
        fllList();

    }

    private void fllList()
    {
        ArregloEmpleados = new ArrayList<String>();

        for(int i = 0; i<listaempleados.size(); i ++)
        {
            ArregloEmpleados.add(listaempleados.get(i).getId()  + " + "
                    + listaempleados.get(i).getNombres()  + " + "
                    + listaempleados.get(i).getApellidos());
        }
    }
}