package com.example.entrenamientosinterfaces;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

        private static final String DEBUG_TAG = "TrainingRecycleAdapter"; //ingles pq sino da error por ser muy grande

        public Context context;
        private DBManager gestorDB;
        public ArrayList<Entrenamiento> listaEntrenamientos;
    protected static final int CODIGO_EDITION_TRAINING = 102;

        public RecycleViewAdapter(Context context, ArrayList<Entrenamiento> listaEntrenamientos) {
            this.context = context;
            this.listaEntrenamientos = listaEntrenamientos;
             gestorDB = new DBManager(context);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            String name = listaEntrenamientos.get(position).getNombre();
            int color = listaEntrenamientos.get(position).getColorResource();
            TextView initial = viewHolder.initial;
            TextView nameTextView = viewHolder.name;
            nameTextView.setText(name);
            initial.setBackgroundColor(color);
            initial.setText(Character.toString(name.charAt(0)));
        }

        @Override
        public void onViewDetachedFromWindow(ViewHolder viewHolder) {
            super.onViewDetachedFromWindow(viewHolder);
            viewHolder.itemView.clearAnimation();
        }

        @Override
        public void onViewAttachedToWindow(ViewHolder viewHolder) {
            super.onViewAttachedToWindow(viewHolder);
            animateCircularReveal(viewHolder.itemView);
        }

        public void animateCircularReveal(View view) {
            int centerX = 0;
            int centerY = 0;
            int startRadius = 0;
            int endRadius = Math.max(view.getWidth(), view.getHeight());
            Animator animation = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
            view.setVisibility(View.VISIBLE);
            animation.start();
        }

        public void animateCircularDelete(final View view, final int list_position) {
            int centerX = view.getWidth();
            int centerY = view.getHeight();
            int startRadius = view.getWidth();
            int endRadius = 0;
            Animator animation = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);

            animation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    Log.d(DEBUG_TAG, "SampleMaterialAdapter onAnimationEnd for Edit adapter position " + list_position);
                    Log.d(DEBUG_TAG, "SampleMaterialAdapter onAnimationEnd for Edit cardId " + getItemId(list_position));

                    view.setVisibility(View.INVISIBLE);
                    gestorDB.eliminarEntrenamiento(listaEntrenamientos.get(list_position).getId());
                    listaEntrenamientos.remove(list_position);
                    notifyItemRemoved(list_position);
                }
            });
            animation.start();
        }

        public void addCard(String nombre, int color) {
            Entrenamiento entrenamiento = new Entrenamiento();
            entrenamiento.setNombre(nombre);
            entrenamiento.setColorResource(color);
            listaEntrenamientos.add(entrenamiento);
            ((MainActivity) context).doSmoothScroll(getItemCount());
            notifyItemInserted(getItemCount());
        }

        public void updateCard(String name, int list_position) {
            listaEntrenamientos.get(list_position).setNombre(name);
            Log.d(DEBUG_TAG, "list_position is " + list_position);
            notifyItemChanged(list_position);
        }

        public void deleteCard(View view, int list_position) {
            animateCircularDelete(view, list_position);
        }

        @Override
        public int getItemCount() {
            if (listaEntrenamientos.isEmpty()) {
                return 0;
            } else {
                return listaEntrenamientos.size();
            }
        }

        @Override
        public long getItemId(int position) {
            return listaEntrenamientos.get(position).getId();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater li = LayoutInflater.from(viewGroup.getContext());
            View v = li.inflate(R.layout.card_view_holder, viewGroup, false);
            return new ViewHolder(v);
        }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{
            private TextView initial;
            private TextView name;
            private Button deleteButton;

            public ViewHolder(View v) {
                super(v);
                v.setOnCreateContextMenuListener(this);
                initial = (TextView) v.findViewById(R.id.initial);
                name = (TextView) v.findViewById(R.id.name);
                deleteButton = (Button) v.findViewById(R.id.delete_button);

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        animateCircularDelete(itemView, getAdapterPosition());
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Pair<View, String> p1 = Pair.create((View) initial, MainActivity.TRANSITION_INITIAL);
                        Pair<View, String> p2 = Pair.create((View) name, MainActivity.TRANSITION_NAME);
                        Pair<View, String> p3 = Pair.create((View) deleteButton, MainActivity.TRANSITION_DELETE_BUTTON);

                        ActivityOptionsCompat options;
                        Activity act = (AppCompatActivity) context;
                        options = ActivityOptionsCompat.makeSceneTransitionAnimation(act, p1, p2, p3);

                        editCard(getAdapterPosition());
                        /*
                            int requestCode = getAdapterPosition();
                            String name = listaEntrenamientos.get(requestCode).getNombre();
                            int color = listaEntrenamientos.get(requestCode).getColorResource();

                            Log.d(DEBUG_TAG, "SampleMaterialAdapter itemView listener for Edit adapter position " + requestCode);

                            Intent transitionIntent = new Intent(context, AddActivity.class);
                            transitionIntent.putExtra(MainActivity.EXTRA_NAME, name);
                            transitionIntent.putExtra(MainActivity.EXTRA_INITIAL, Character.toString(name.charAt(0)));
                            transitionIntent.putExtra(MainActivity.EXTRA_COLOR, color);
                            transitionIntent.putExtra(MainActivity.EXTRA_UPDATE, false);
                            transitionIntent.putExtra(MainActivity.EXTRA_DELETE, false);
                            ((AppCompatActivity) context).startActivityForResult(transitionIntent, requestCode, options.toBundle());
                        */
                    }
                });
            }
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()){
                        case 1:
                            editCard(getAdapterPosition());
                            break;
                        case 2:
                            deleteCard(itemView, getAdapterPosition());
                            break;

                    }
                    return true;
                }

                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    MenuItem modificar = menu.add(Menu.NONE, 1, 1, "Modificar");
                    modificar.setOnMenuItemClickListener(this);
                    MenuItem borrar= menu.add(Menu.NONE, 2, 2,"Borrar");
                    borrar.setOnMenuItemClickListener(this);
                }

        }

    public void editCard(int pos){

        Intent subActividad = new Intent( context, AddActivity.class );
        subActividad.putExtra( "id", listaEntrenamientos.get(pos).getId() );
        subActividad.putExtra( "nombre", listaEntrenamientos.get(pos).getNombre() );
        subActividad.putExtra( "fecha", listaEntrenamientos.get(pos).getFecha() );
        subActividad.putExtra( "horas", listaEntrenamientos.get(pos).getHoras() );
        subActividad.putExtra( "minutos", listaEntrenamientos.get(pos).getMinutos() );
        subActividad.putExtra( "segundos", listaEntrenamientos.get(pos).getSegundos() );
        subActividad.putExtra( "kilometros", listaEntrenamientos.get(pos).getKilometros() );
        subActividad.putExtra( "metros", listaEntrenamientos.get(pos).getMetros() );
        subActividad.putExtra( "tipo", listaEntrenamientos.get(pos).getTipo() );

        ((AppCompatActivity) context).startActivityForResult( subActividad, CODIGO_EDITION_TRAINING );
        }
}



