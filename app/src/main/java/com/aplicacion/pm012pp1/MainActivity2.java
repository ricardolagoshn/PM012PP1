package com.aplicacion.pm012pp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class MainActivity2 extends AppCompatActivity {

    EditText txtresultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtresultado = (EditText) findViewById(R.id.txtresultado);

        txtresultado.setText(getIntent().getExtras().getString("nombre"));

    }
}