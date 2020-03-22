package com.example.entrenamientosinterfaces;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.regex.Pattern;

public class AddActivity extends AppCompatActivity {

    int id = -1;
    private int color;
    private Random randomGenerator = new Random();
    private TextView initialTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        this.gestorDB = new DBManager(getApplicationContext());

        final Button btAceptar = (Button) this.findViewById( R.id.aceptar );
        final Button btCancelar = (Button) this.findViewById( R.id.cancelar );
        final EditText edNombre = (EditText) this.findViewById( R.id.nombre );
        final Button btFecha = (Button) this.findViewById(R.id.fecha);
        final EditText edHoras = (EditText) this.findViewById( R.id.horas );
        final EditText edMinutos = (EditText) this.findViewById( R.id.minutos );
        final EditText edSegundos = (EditText) this.findViewById( R.id.segundos );
        final EditText edKilometros = (EditText) this.findViewById( R.id.kilometros );
        final EditText edMetros = (EditText) this.findViewById( R.id.metros );
        final RadioGroup rgTipo = (RadioGroup) this.findViewById( R.id.tipo );
        int selectedId= rgTipo.getCheckedRadioButtonId();
        final RadioButton resultTipo = (RadioButton) this.findViewById(selectedId);
        

        InputFilter[] limiteNombre = new InputFilter[1];
        limiteNombre[0] = new InputFilter.LengthFilter(30);
        InputFilter[] limiteTiempo = new InputFilter[1];
        limiteTiempo[0] = new InputFilter.LengthFilter(2);
        InputFilter[] limiteDistancia = new InputFilter[1];
        limiteDistancia[0] = new InputFilter.LengthFilter(3);


        edNombre.setFilters(limiteNombre);
        edHoras.setFilters(limiteTiempo);
        edMinutos.setFilters(limiteTiempo);
        edSegundos.setFilters(limiteTiempo);
        edKilometros.setFilters(limiteDistancia);
        edMetros.setFilters(limiteDistancia);


       /* final Intent transitionIntent = this.getIntent();
        final int id = transitionIntent.getExtras().getInt("id", 0);
        final String nombre = transitionIntent.getExtras().getString("nombre", "ERROR");
        final String horas = transitionIntent.getExtras().getString("horas", "ERROR");
        final String minutos = transitionIntent.getExtras().getString("minutos", "ERROR");
        final String segundos = transitionIntent.getExtras().getString("segundos", "ERROR");
        final String kilometros = transitionIntent.getExtras().getString("kilometros", "ERROR");
        final String metros = transitionIntent.getExtras().getString("metros", "ERROR");


        edNombre.setText(nombre);

        btFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Aqui va el datepicker
            }
        });

        edHoras.setText(horas);
        edMinutos.setText(minutos);
        edSegundos.setText(segundos);
        edKilometros.setText(kilometros);
        edMetros.setText(metros);

        */


        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddActivity.this.setResult(Activity.RESULT_CANCELED);
                AddActivity.this.finish();
            }
        });


        btAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean noParseo = true;
                try{
                    Integer.parseInt(edHoras.getText().toString());
                }catch(NumberFormatException e){
                    noParseo=false;
                }

                if (edNombre.getText().toString().trim().isEmpty()
                        || noParseo==false) {

                    AlertDialog.Builder err = new AlertDialog.Builder(AddActivity.this);

                    if (edNombre.getText().toString().trim().isEmpty()) {

                        err.setTitle("Error, campo nombre vacío");
                    } /*else if (edApellido.getText().toString().trim().isEmpty()) {
                        err.setTitle("Error, campo apellido vacío");
                    } else if (edTelefono.getText().toString().trim().isEmpty()) {
                        err.setTitle("Error, campo telefono vacío");
                    } else if (edEmail.getText().toString().trim().isEmpty()) {
                        err.setTitle("Error, campo email vacío");
                    }else if(comprobar == false) {
                        err.setTitle("Campo email no válido");
                    }else if(noParseo == false) {
                        err.setTitle("Campo telefono no válido");
                    }*/

                    err.setNeutralButton("OK", null);
                    err.create().show();

                } else {

                    final String nombre = edNombre.getText().toString();
                    final String fecha = "2007-03-21";
                    final int horas = Integer.parseInt(edHoras.getText().toString());
                    final int minutos = Integer.parseInt(edMinutos.getText().toString());
                    final int segundos = Integer.parseInt(edSegundos.getText().toString());
                    final int kilometros = Integer.parseInt(edKilometros.getText().toString());
                    final int metros = Integer.parseInt(edMetros.getText().toString());
                    final String tipo = String.valueOf(resultTipo.getText());

                    System.out.println("DATOS QUE VAN A LA BASE " + nombre + " "
                    + fecha + " "+ horas + " " + minutos + " " + segundos +" " +
                            kilometros + " " + metros + " " + tipo);
                    final Intent retData = new Intent();

                   // if (id == 0) {
                        retData.putExtra("name", nombre);
                        retData.putExtra("color", color);
                        gestorDB.insertaEntrenamiento(nombre,fecha,horas,minutos,segundos,kilometros,metros,tipo);
                        gestorDB.close();
                   /* } else {
                        retData.putExtra("name", nombre);
                        retData.putExtra("fecha", fecha);
                        retData.putExtra("horas", horas);
                        retData.putExtra("minutos", minutos);
                        retData.putExtra("segundos", segundos);
                        retData.putExtra("kilometros", kilometros);
                        retData.putExtra("metros", metros);
                        retData.putExtra("tipo", tipo);
                    }*/
                    AddActivity.this.setResult(Activity.RESULT_OK, retData);
                    AddActivity.this.finish();
                }
            }
        });

    }

    private DBManager gestorDB;

}
