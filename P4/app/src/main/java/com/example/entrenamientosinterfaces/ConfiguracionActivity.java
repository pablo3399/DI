package com.example.entrenamientosinterfaces;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;


public class ConfiguracionActivity extends AppCompatActivity {

    private DBManager gestorDB;
    private int id;
    private String nombre;
    private int edad;
    private String nacionalidad;
    private String lenguaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_activity);

        this.gestorDB = new DBManager(this);

        final EditText edNombre = (EditText) findViewById(R.id.nombreC);
        final EditText edEdad = (EditText) findViewById(R.id.edadC);
        final EditText edNacionalidad = (EditText) findViewById(R.id.nacionalidadC);
        final RadioButton radioEsp = (RadioButton) this.findViewById(R.id.radioEspañol);
        final RadioButton radioIng = (RadioButton) this.findViewById(R.id.radioIngles);
        final Button btCancelar = (Button) this.findViewById(R.id.cancelar);
        final Button btAceptar = (Button) this.findViewById(R.id.aceptar);
        final ImageButton imgBtn = (ImageButton) this.findViewById(R.id.imageButton);

        Cursor cursor = this.gestorDB.getConf();
        if(cursor!=null){
            while(cursor.moveToNext()){
                 id = cursor.getInt((cursor.getColumnIndex(DBManager.CONFIGURATION_COL_ID)));
                nombre=cursor.getString((cursor.getColumnIndex(DBManager.CONFIGURATION_COL_NOMBRE )));
                edad=cursor.getInt((cursor.getColumnIndex(DBManager.CONFIGURATION_COL_EDAD )));
                nacionalidad=cursor.getString((cursor.getColumnIndex(DBManager.CONFIGURATION_COL_NACIONALIDAD )));
                lenguaje=cursor.getString((cursor.getColumnIndex(DBManager.CONFIGURATION_COL_LENGUAJE )));
            }
        }

        edNombre.setText(nombre);
        edEdad.setText(String.valueOf(edad));
        edNacionalidad.setText(nacionalidad);

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfiguracionActivity.this.setResult(Activity.RESULT_CANCELED);
                ConfiguracionActivity.this.finish();
            }
        });

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfiguracionActivity.this.finish();
            }
        });

        btAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = edNombre.getText().toString();
                edad = Integer.valueOf(edEdad.getText().toString());
                nacionalidad = edNacionalidad.getText().toString();
                if(radioEsp.isChecked()) {
                    lenguaje = "español";
                } else if (radioIng.isChecked()){
                    lenguaje="ingles";
                }
                gestorDB.modificarConfiguration(id, nombre, edad, nacionalidad, lenguaje);
                gestorDB.close();
                ConfiguracionActivity.this.finish();
            }
        });

    }
}
