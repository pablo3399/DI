package com.example.entrenamientosinterfaces;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EstadisticasActivity extends AppCompatActivity {

    private DBManager gestorDB;
    private String nombre;
    private int edad;
    private String nacionalidad;
    private ArrayList<Entrenamiento> listaEntrenamientos = new ArrayList<Entrenamiento>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_activity);
        this.gestorDB = new DBManager(this);

        TextView tvNombre = (TextView) findViewById(R.id.tvNombre);
        TextView tvEdad = (TextView) findViewById(R.id.tvEdad);
        TextView tvNacionalidad = (TextView) findViewById(R.id.tvNacionalidadStats);
        TextView tvTotales = (TextView) findViewById(R.id.tvTotales);
        TextView tvVelocidadMedia = (TextView) findViewById(R.id.tvVelocidadMedia);
        TextView tvDistancia = (TextView) findViewById(R.id.tvDistanciaTotal);
        TextView tvTiempo = (TextView) findViewById(R.id.tvTiempoTotal);
        Button btAceptar = (Button) findViewById(R.id.aceptar);
        final ImageButton imgBtn = (ImageButton) this.findViewById(R.id.imageButton);

        getLista();

        Cursor cursor = this.gestorDB.getConf();
        if (cursor != null) {
            while (cursor.moveToNext()) {

                nombre = cursor.getString((cursor.getColumnIndex(DBManager.CONFIGURATION_COL_NOMBRE)));
                edad = cursor.getInt(cursor.getColumnIndex(DBManager.CONFIGURATION_COL_EDAD));
                nacionalidad = cursor.getString((cursor.getColumnIndex(DBManager.CONFIGURATION_COL_NACIONALIDAD)));

            }
        }

        tvNombre.setText(nombre);
        tvEdad.setText(String.valueOf(edad));
        tvNacionalidad.setText(nacionalidad);
        tvTotales.setText("Total: " + String.valueOf(listaEntrenamientos.size()));
        tvVelocidadMedia.setText(velocidadMedia());
        tvTiempo.setText(tiempoTotal());
        tvDistancia.setText(distanciaTotal());

        btAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EstadisticasActivity.this.setResult(Activity.RESULT_CANCELED);
                EstadisticasActivity.this.finish();
            }
        });

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EstadisticasActivity.this.finish();
            }
        });
    }

    public void getLista() {
        Cursor cursor = this.gestorDB.getEntrenamientos();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_ID)));
                String dato1 = cursor.getString((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_NOMBRE)));
                String dato2 = cursor.getString(cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_FECHA));
                int dato3 = cursor.getInt((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_HORAS)));
                int dato4 = cursor.getInt((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_MINUTOS)));
                int dato5 = cursor.getInt((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_SEGUNDOS)));
                int dato6 = cursor.getInt((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_KILOMETROS)));
                int dato7 = cursor.getInt((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_METROS)));
                String dato8 = cursor.getString((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_TIPO)));
                Entrenamiento entrenamiento = new Entrenamiento(dato1, dato2, dato3, dato4, dato5, dato6, dato7, dato8);
                entrenamiento.setId(id);
                listaEntrenamientos.add(entrenamiento);
            }
        }else{

        }
    }

    public String tiempoTotal() {

        int totalHoras=0;
        int horas=0;
        int totalMinutos=0;
        int minAHoras = 0;
        int minutos=0;
        int totalSegundos=0;
        int segundos=0;
        int segsAMin = 0;

        for (int x = 0; x<listaEntrenamientos.size(); x++) {
            horas = horas + listaEntrenamientos.get(x).getHoras();
            minutos = minutos + listaEntrenamientos.get(x).getMinutos();
            segundos = segundos + listaEntrenamientos.get(x).getSegundos();

        }

            if(segundos>60){
                while (segundos>60){
                   segsAMin++;
                   segundos=segundos/60;
                }
            }

            totalSegundos = totalSegundos+segundos;
            minutos=minutos+segsAMin;

            if(minutos>60){
                while(minutos>60){
                    minAHoras++;
                    minutos=minutos/60;
                }
            }
            totalMinutos = totalMinutos+minutos;
            horas=horas+minAHoras;
            totalHoras=totalHoras+horas;

        return "H: " + totalHoras + " M: " + totalMinutos + " S: " + totalSegundos;
    }

    public String distanciaTotal() {

        int totalKilometros=0;
        int kilometros=0;
        int totalMetros=0;
        int metros = 0;
        int metrosAKm=0;

        for (int x = 0; x<listaEntrenamientos.size(); x++) {
            kilometros = kilometros + listaEntrenamientos.get(x).getKilometros();
            metros = metros + listaEntrenamientos.get(x).getMetros();
        }

        if(metros>1000){
            while(metros>1000){
                metrosAKm++;
                metros=metros-1000;
            }
        }

        totalMetros = totalMetros+metros;
        kilometros=kilometros+metrosAKm;
        totalKilometros=totalKilometros+kilometros;

        return "K: " + totalKilometros + " M: " + totalMetros;
    }

    public String velocidadMedia(){

        float segundos=0;
        float metros=0;
        float minutos=0;
        float kilometros=0;
        float horas=0;
        float totalKilometros;
        float totalMetros;
        float totalHoras;
        float totalMinutos;
        float totalSegundos;
        float velocidadMedia;

        for(int x = 0; x<listaEntrenamientos.size();x++){
            kilometros = kilometros + listaEntrenamientos.get(x).getKilometros();
            metros = metros + listaEntrenamientos.get(x).getMetros();
            horas = horas + listaEntrenamientos.get(x).getHoras();
            minutos = minutos + listaEntrenamientos.get(x).getMinutos();
            segundos = segundos + listaEntrenamientos.get(x).getSegundos();
        }

        totalSegundos = segundos/60;
        totalMinutos=(minutos+totalSegundos)/60;

        totalHoras=(horas+totalMinutos);

        totalMetros = metros/1000;
        totalKilometros=(kilometros+totalMetros);

        velocidadMedia = (totalKilometros/totalHoras)/listaEntrenamientos.size();
        return "VM: " + velocidadMedia;
    }
}
