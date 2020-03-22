package com.example.entrenamientosinterfaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DBManager gestorDB;

    private static final String DEBUG_TAG = "AppCompatActivity";

    public static final String EXTRA_UPDATE = "update";
    public static final String EXTRA_DELETE = "delete";
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_COLOR = "color";
    public static final String EXTRA_INITIAL = "initial";

    public static final String TRANSITION_FAB = "fab_transition";
    public static final String TRANSITION_INITIAL = "initial_transition";
    public static final String TRANSITION_NAME = "name_transition";
    public static final String TRANSITION_DELETE_BUTTON = "delete_button_transition";

    private RecycleViewAdapter adapter;
    private ArrayList<Entrenamiento> listaEntrenamientos = new ArrayList<>();
    private int[] colors;
    private String[] names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        if (requestCode == adapter.getItemCount()) {
            if (resultCode == RESULT_OK) {
                // Make sure the Add request was successful
                // if add name, insert name in list
                String name = data.getStringExtra(EXTRA_NAME);
                int color = data.getIntExtra(EXTRA_COLOR, 0);
                adapter.addCard(name, color);
            }
        } else {
            // Anything other than adapter.getItemCount() means editing a particular list item
            // the requestCode is the list item position
            if (resultCode == RESULT_OK) {
                // Make sure the Update request was successful
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(requestCode);
                if (data.getExtras().getBoolean(EXTRA_DELETE, false)) {
                    // if delete user delete
                    // The user deleted a contact
                    adapter.deleteCard(viewHolder.itemView, requestCode);
                } else if (data.getExtras().getBoolean(EXTRA_UPDATE)) {
                    // if name changed, update user
                    String name = data.getStringExtra(EXTRA_NAME);
                    viewHolder.itemView.setVisibility(View.INVISIBLE);
                    adapter.updateCard(name, requestCode);
                }
            }
        }
    }

    //method to start the pre created cards
    private void initCards() {

            Cursor cursor = this.gestorDB.getEntrenamientos();

            if(cursor!=null){
            while(cursor.moveToNext()){
                int id = cursor.getInt((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_ID)));
                String dato1=cursor.getString((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_NOMBRE )));
                System.out.println("AQUI ESTA LA RECUPERACION DEL NOMBRE DE LA BASE " + dato1);
                Date fecha = new Date(cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_FECHA));
                Date dato2 = fecha;
                int dato3=cursor.getInt((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_HORAS )));
                int dato4=cursor.getInt((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_MINUTOS )));
                int dato5=cursor.getInt((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_SEGUNDOS )));
                int dato6=cursor.getInt((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_KILOMETROS )));
                int dato7=cursor.getInt((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_METROS )));
                String dato8=cursor.getString((cursor.getColumnIndex(DBManager.ENTRENAMIENTO_COL_TIPO )));
                Entrenamiento entrenamiento = new Entrenamiento(dato1, dato2, dato3, dato4, dato5, dato6, dato7, dato8);
                System.out.println("ENTREN " + entrenamiento.toString());
                entrenamiento.setId(id);
                listaEntrenamientos.add(entrenamiento);
            }

        }
    }

}
