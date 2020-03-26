package com.example.entrenamientosinterfaces;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;
import java.util.Random;
import java.util.regex.Pattern;

public class AddActivity extends AppCompatActivity {

    int id = -1;
    protected static final int CODIGO_ADITION_TRAINING = 100;
    protected static final int CODIGO_EDITION_TRAINING = 102;
    private int color;
    private Random randomGenerator = new Random();
    private TextView initialTextView;
    private String fcha;

    final Calendar c = Calendar.getInstance();
    final int mesFin = c.get(Calendar.MONTH)+1;
    final int diaFin = c.get(Calendar.DAY_OF_MONTH);
    final int anioFin = c.get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        this.gestorDB = new DBManager(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

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
        final ImageButton btBack = (ImageButton) this.findViewById(R.id.imageButton);
        

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

        btFecha.setText(diaFin + "/" + mesFin + "/" + anioFin);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddActivity.this.finish();
            }
        });

            final Intent transitionIntent = this.getIntent();
            try {
                final int id = transitionIntent.getExtras().getInt("id", -1);
                this.id = id;
            }catch(Exception e){
                this.id=-1;
            }

            if(this.id!=-1) {
            final String nombre2 = transitionIntent.getExtras().getString("nombre", "ERROR");
            final String fecha2 = transitionIntent.getExtras().getString("fecha", "1/1/1999");
            final int horas2 = transitionIntent.getExtras().getInt("horas", 0);
            final int minutos2 = transitionIntent.getExtras().getInt("minutos", 0);
            final int segundos2 = transitionIntent.getExtras().getInt("segundos", 0);
            final int kilometros2 = transitionIntent.getExtras().getInt("kilometros", 0);
            final int metros2 = transitionIntent.getExtras().getInt("metros", 0);

        edNombre.setText(nombre2);

                btFecha.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v){

                        String parts[] = fecha2.trim().split("/",3);

                        final int part0 = Integer.parseInt(parts[0]);
                        final int part1 = Integer.parseInt(parts[1])-1;
                        final int part2 = Integer.parseInt(parts[2]);

                        DatePickerDialog dlg = new DatePickerDialog(AddActivity.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker dp, int y, int m, int d) {

                                        final String date = d + "/" + (m+1) + "/" + y;
                                        fcha = date;

                                        btFecha.setText("Fecha: "+ date);

                                    }
                                },
                                part2, part1, part0
                        );
                        dlg.show();
                    }});

        edHoras.setText(String.valueOf(horas2));
        edMinutos.setText(String.valueOf(minutos2));
        edSegundos.setText(String.valueOf(segundos2));
        edKilometros.setText(String.valueOf(kilometros2));
        edMetros.setText(String.valueOf(metros2));

        }

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
                    final int horas = Integer.parseInt(edHoras.getText().toString());
                    final int minutos = Integer.parseInt(edMinutos.getText().toString());
                    final int segundos = Integer.parseInt(edSegundos.getText().toString());
                    final int kilometros = Integer.parseInt(edKilometros.getText().toString());
                    final int metros = Integer.parseInt(edMetros.getText().toString());
                    final String tipo = String.valueOf(resultTipo.getText());

                    final Intent retData = new Intent();

                    if (id==-1) {
                        retData.putExtra("name", nombre);
                        retData.putExtra("color", color);
                        gestorDB.insertaEntrenamiento(nombre,fcha,horas,minutos,segundos,kilometros,metros,tipo);
                        gestorDB.close();
                        AddActivity.this.setResult(CODIGO_ADITION_TRAINING, retData);
                        AddActivity.this.finish();
                    } else {
                        retData.putExtra("id", id);
                        retData.putExtra("name", nombre);
                        retData.putExtra("fecha", fcha);
                        retData.putExtra("horas", horas);
                        retData.putExtra("minutos", minutos);
                        retData.putExtra("segundos", segundos);
                        retData.putExtra("kilometros", kilometros);
                        retData.putExtra("metros", metros);
                        retData.putExtra("tipo", tipo);
                        gestorDB.close();
                        AddActivity.this.setResult(CODIGO_EDITION_TRAINING , retData);
                        AddActivity.this.finish();
                    }

                }
            }
        });

    }

    private DBManager gestorDB;

}
