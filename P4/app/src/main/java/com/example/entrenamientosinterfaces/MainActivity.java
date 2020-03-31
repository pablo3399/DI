package com.example.entrenamientosinterfaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DBManager gestorDB;

    private static final String DEBUG_TAG = "AppCompatActivity";
    protected static final int CODIGO_ADITION_TRAINING = 100;
    protected static final int CODIGO_EDITION_TRAINING = 102;
    protected static final int CODIGO_CONFIGURACION = 103;
    private int positionF;

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_COLOR = "color";

    public static final String TRANSITION_FAB = "fab_transition";
    public static final String TRANSITION_INITIAL = "initial_transition";
    public static final String TRANSITION_NAME = "name_transition";
    public static final String TRANSITION_DELETE_BUTTON = "delete_button_transition";

    private RecycleViewAdapter adapter;
    private ArrayList<Entrenamiento> listaEntrenamientos = new ArrayList<>();
    private int[] colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        this.gestorDB= new DBManager(this);

       // NOMBRES DE LA BASE names = getResources().getStringArray(R.array.names_array);
        colors = getResources().getIntArray(R.array.initial_colors);

        initCards();

        if (adapter == null) {
            adapter = new RecycleViewAdapter(this, listaEntrenamientos);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair<View, String> pair = Pair.create(v.findViewById(R.id.fab), TRANSITION_FAB);

                ActivityOptionsCompat options;
                Activity act = MainActivity.this;
                options = ActivityOptionsCompat.makeSceneTransitionAnimation(act, pair);

                Intent transitionIntent = new Intent(act, AddActivity.class);
                act.startActivityForResult(transitionIntent, adapter.getItemCount(), options.toBundle());
            }
        });
    }

    @Override
    public void onPause()
    {
        super.onPause();
        listaEntrenamientos.clear();
        this.gestorDB.close();
    }

    @Override
    public void onResume(){
        super.onResume();
        listaEntrenamientos.clear();
        initCards();
    }

    //method to do the scroll smoother
    public void doSmoothScroll(int position) {
        recyclerView.smoothScrollToPosition(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(DEBUG_TAG, "requestCode is " + requestCode);
        // if adapter.getItemCount() is request code, that means we are adding a new position
        // anything less than adapter.getItemCount() means we are editing a particular position
            if (resultCode == CODIGO_ADITION_TRAINING) {
                // Make sure the Add request was successful
                // if add name, insert name in list
                String name = data.getStringExtra(EXTRA_NAME);
                int color = data.getIntExtra(EXTRA_COLOR, 0);
                adapter.addCard(name, color);
                adapter.notifyDataSetChanged();
            }

        if(resultCode == CODIGO_EDITION_TRAINING){
            // Anything other than adapter.getItemCount() means editing a particular list item
            // the requestCode is the list item position
                // Make sure the Update request was successful
            int adapterId=0;

            int id = data.getExtras().getInt("id", 0);
            String nombre = data.getExtras().getString( "name", "ERROR" );
            String fecha = data.getExtras().getString("fecha", "1/1/1999");
            int horas = data.getExtras().getInt("horas", 0);
            int minutos = data.getExtras().getInt("minutos", 0);
            int segundos = data.getExtras().getInt("segundos", 0);
            int kilometros = data.getExtras().getInt("kilometros", 0);
            int metros = data.getExtras().getInt("metros", 0);
            String tipo = data.getExtras().getString("tipo", "ERROR");

            this.gestorDB.modificaEntrenamiento(id, nombre, fecha, horas,minutos,segundos,kilometros,metros, tipo );

            listaEntrenamientos.clear();
            initCards();

            for (int x = 0; x<listaEntrenamientos.size();x++){
                if(id == listaEntrenamientos.get(x).getId()){
                    adapterId=x;
                }
            }
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(adapterId);

                    viewHolder.itemView.setVisibility(View.INVISIBLE);
                    adapter.updateCard(nombre, adapterId);
            adapter.notifyDataSetChanged();

        }

    }

    private void initCards() {

            Cursor cursor = this.gestorDB.getEntrenamientos();
            if(cursor!=null){
            while(cursor.moveToNext()){
                int id = cursor.getInt((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_ID)));
                String dato1=cursor.getString((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_NOMBRE )));
                String dato2 = cursor.getString(cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_FECHA));
                int dato3=cursor.getInt((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_HORAS )));
                int dato4=cursor.getInt((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_MINUTOS )));
                int dato5=cursor.getInt((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_SEGUNDOS )));
                int dato6=cursor.getInt((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_KILOMETROS )));
                int dato7=cursor.getInt((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_METROS )));
                String dato8=cursor.getString((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_TIPO )));
                Entrenamiento entrenamiento = new Entrenamiento(dato1, dato2, dato3, dato4, dato5, dato6, dato7, dato8);
                entrenamiento.setId(id);
                listaEntrenamientos.add(entrenamiento);
            }
        }
    }

    //menu de opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu( menu );
        this.getMenuInflater().inflate( R.menu.actions_menu, menu );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        boolean toret = false;
        positionF=0;
        final String[] listItems = new String[listaEntrenamientos.size()];
        for(int x = 0; x<listaEntrenamientos.size(); x++){

            listItems[x]=listaEntrenamientos.get(x).getNombre();
        }

        switch( menuItem.getItemId() ) {
            case R.id.añadir:
                Intent transitionIntent = new Intent(this, AddActivity.class);
                MainActivity.this.startActivity(transitionIntent);
                toret = true;
                break;
            case R.id.modificar:

                //dialogo de alerta que muestra por pantalla los posibles objetos para modificar
                AlertDialog.Builder getMod = new AlertDialog.Builder(MainActivity.this);
                getMod.setTitle("Cual quieres cambiar?");

                final int checkedItem = 0;
                getMod.setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        positionF=which;
                    }
                });

                getMod.setPositiveButton("go", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.edit(positionF);
                        positionF=0;
                    }
                });

                getMod.setNegativeButton("cancel",null);

                getMod.create().show();

                toret = true;
                break;
            case R.id.eliminar:

                //dialogo de alerta para eliminar un elemento de la lista
                AlertDialog.Builder getDel = new AlertDialog.Builder(MainActivity.this);
                getDel.setTitle("Cual quieres eliminar?");

                for(int x = 0; x<listaEntrenamientos.size(); x++){

                    listItems[x]=listaEntrenamientos.get(x).getNombre();
                }

                final int checkedIt = 0;
                getDel.setSingleChoiceItems(listItems, checkedIt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        positionF=which;
                    }
                });

                getDel.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(positionF);

                        adapter.deleteCard(viewHolder.itemView, positionF);
                        adapter.notifyDataSetChanged();

                    }
                });

                getDel.setNegativeButton("cancel",null);

                getDel.create().show();

                toret = true;
                break;

            case R.id.stats:
                //llamada a la actividad de mostrar estadisticas
                Intent subActividad = new Intent( MainActivity.this, EstadisticasActivity.class );

                MainActivity.this.startActivity( subActividad );
                toret = true;
                break;

            case R.id.config:
                //llamada a la actividad de mostrar config
                Intent subActividadConf = new Intent( MainActivity.this, ConfiguracionActivity.class );

                MainActivity.this.startActivity( subActividadConf );
                toret = true;
        }

        return toret;
    }

    public void edit(int pos){

        Intent subActividad = new Intent( MainActivity.this, AddActivity.class );
        subActividad.putExtra( "id", listaEntrenamientos.get(pos).getId() );
        subActividad.putExtra( "nombre", listaEntrenamientos.get(pos).getNombre() );
        subActividad.putExtra( "fecha", listaEntrenamientos.get(pos).getFecha() );
        subActividad.putExtra( "horas", listaEntrenamientos.get(pos).getHoras() );
        subActividad.putExtra( "minutos", listaEntrenamientos.get(pos).getMinutos() );
        subActividad.putExtra( "segundos", listaEntrenamientos.get(pos).getSegundos() );
        subActividad.putExtra( "kilometros", listaEntrenamientos.get(pos).getKilometros() );
        subActividad.putExtra( "metros", listaEntrenamientos.get(pos).getMetros() );
        subActividad.putExtra( "tipo", listaEntrenamientos.get(pos).getTipo() );

        MainActivity.this.startActivityForResult( subActividad, CODIGO_EDITION_TRAINING );

    }

}
