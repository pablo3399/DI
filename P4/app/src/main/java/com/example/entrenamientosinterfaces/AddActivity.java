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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.niwattep.materialslidedatepicker.SlideDatePickerDialog;
import com.niwattep.materialslidedatepicker.SlideDatePickerDialogCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.regex.Pattern;

public class AddActivity extends AppCompatActivity implements SlideDatePickerDialogCallback {

    int id = -1;
    protected static final int CODIGO_ADITION_TRAINING = 100;
    protected static final int CODIGO_EDITION_TRAINING = 102;
    private int color;
    private Random randomGenerator = new Random();
    private TextView initialTextView;
    private String fcha;
    private final Calendar c = Calendar.getInstance();
    private final int mesFin = c.get(Calendar.MONTH) + 1;
    private final int diaFin = c.get(Calendar.DAY_OF_MONTH);
    private final int anioFin = c.get(Calendar.YEAR);
    private Button btFecha;

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
         btFecha = (Button) this.findViewById(R.id.fecha);
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
        btFecha.setText(diaFin+"/"+mesFin+"/"+anioFin);
        edHoras.setFilters(limiteTiempo);
        edMinutos.setFilters(limiteTiempo);
        edSegundos.setFilters(limiteTiempo);
        edKilometros.setFilters(limiteDistancia);
        edMetros.setFilters(limiteDistancia);

        btFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar date= Calendar.getInstance();
                date.set(Calendar.YEAR, 2030);

                SlideDatePickerDialog.Builder builder = new SlideDatePickerDialog.Builder();
                builder.setEndDate(date);
                SlideDatePickerDialog dialog = builder.build();
                dialog.show(getSupportFragmentManager(), "Dialog");

            }
        });

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
            final String fecha2 = transitionIntent.getExtras().getString("fecha", "1-1-1999");
            final int horas2 = transitionIntent.getExtras().getInt("horas", 0);
            final int minutos2 = transitionIntent.getExtras().getInt("minutos", 0);
            final int segundos2 = transitionIntent.getExtras().getInt("segundos", 0);
            final int kilometros2 = transitionIntent.getExtras().getInt("kilometros", 0);
            final int metros2 = transitionIntent.getExtras().getInt("metros", 0);

            edNombre.setText(nombre2);

            btFecha.setText(fecha2);
                btFecha.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Calendar date= Calendar.getInstance();
                        date.set(Calendar.YEAR, 2030);

                        SlideDatePickerDialog.Builder builder = new SlideDatePickerDialog.Builder();
                        builder.setEndDate(date);
                        SlideDatePickerDialog dialog = builder.build();
                        dialog.show(getSupportFragmentManager(), "Dialog");

                    }
                });

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
                    Integer.valueOf(edMinutos.getText().toString());
                    Integer.valueOf(edSegundos.getText().toString());
                    Integer.valueOf(edKilometros.getText().toString());
                    Integer.valueOf(edMetros.getText().toString());

                }catch(NumberFormatException e){
                    noParseo=false;
                }

                if (edNombre.getText().toString().trim().isEmpty()
                        || noParseo==false) {

                    AlertDialog.Builder err = new AlertDialog.Builder(AddActivity.this);

                    try {
                        if (edNombre.getText().toString().trim().isEmpty()) {
                            err.setTitle("Error, No name");
                        } else if (Integer.valueOf(edHoras.getText().toString()) > 24) {
                            err.setTitle("Error, max 24H");
                        } else if (Integer.valueOf(edMinutos.getText().toString()) > 60) {
                            err.setTitle("Error, max 60M");
                        } else if (Integer.valueOf(edSegundos.getText().toString()) > 60) {
                            err.setTitle("Error, max 60S");
                        } else if (Integer.valueOf(edKilometros.getText().toString()) > 999) {
                            err.setTitle("Error, max 999KM");
                        } else if (Integer.valueOf(edMetros.getText().toString()) > 999) {
                            err.setTitle("Error, max 999M");
                        }

                    }catch (NumberFormatException e){
                        AlertDialog.Builder err2 = new AlertDialog.Builder(AddActivity.this);
                        err2.setMessage("Campo vacio");
                        err2.setNeutralButton("OK", null);
                        err2.create().show();
                    }

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

    @Override
    public void onPositiveClick(int i, int i1, int i2, Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        btFecha.setText(dateFormat.format(calendar.getTime()));
        fcha=btFecha.getText().toString();
    }
}
