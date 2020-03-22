package com.example.entrenamientosinterfaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;


public class DBManager extends SQLiteOpenHelper {
    public static final String DB_NOMBRE = "ListaEntrenamientos";
    public static final int DB_VERSION = 1;

    public static final String TABLA_ENTRENAMIENTO = "entrenamientos";
    public static final String ENTRENAMIENTO_COL_ID = "_id";
    public static final String ENTRENAMIENTO_COL_NOMBRE = "nombre";
    public static final String ENTRENAMIENTO_COL_FECHA = "fecha";
    public static final String ENTRENAMIENTO_COL_HORAS = "horas";
    public static final String ENTRENAMIENTO_COL_MINUTOS = "minutos";
    public static final String ENTRENAMIENTO_COL_SEGUNDOS = "segundos";
    public static final String ENTRENAMIENTO_COL_KILOMETROS = "kilometros";
    public static final String ENTRENAMIENTO_COL_METROS = "metros";
    public static final String ENTRENAMIENTO_COL_TIPO = "tipo";


    public DBManager(Context context) {
        super(context, DB_NOMBRE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DBManager",
                "Creando BBDD " + DB_NOMBRE + " v" + DB_VERSION);

        try {
            db.beginTransaction();
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLA_ENTRENAMIENTO + "( "
                    + ENTRENAMIENTO_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + ENTRENAMIENTO_COL_NOMBRE + " string(30) NOT NULL, "
                    + ENTRENAMIENTO_COL_FECHA + " string(30) NOT NULL, "
                    + ENTRENAMIENTO_COL_HORAS + " int, "
                    + ENTRENAMIENTO_COL_MINUTOS + " int, "
                    + ENTRENAMIENTO_COL_SEGUNDOS + " int, "
                    + ENTRENAMIENTO_COL_KILOMETROS + " int, "
                    + ENTRENAMIENTO_COL_METROS + " int, "
                    + ENTRENAMIENTO_COL_TIPO + " string(25)  NOT NULL) ");
            db.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("DBManager.onCreate", exc.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DBManager",
                "DB: " + DB_NOMBRE + ": v" + oldVersion + " -> v" + newVersion);

        try {
            db.beginTransaction();
            db.execSQL("DROP TABLE IF EXISTS " + TABLA_ENTRENAMIENTO);
            db.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("DBManager.onUpgrade", exc.getMessage());
        } finally {
            db.endTransaction();
        }

        this.onCreate(db);
    }

    /**
     * Devuelve todas los contactos en la BD
     *
     * @return Un Cursor con los entrenamientos.
     */
    public Cursor getEntrenamientos() {
        return this.getReadableDatabase().query(TABLA_ENTRENAMIENTO,
                null, null, null, null, null, null);
    }


    public void insertaEntrenamiento(String nombre, String fecha, int horas, int minutos, int segundos, int kilometros, int metros, String tipo) {

        String insert="INSERT INTO "+TABLA_ENTRENAMIENTO
                +" ( "+ENTRENAMIENTO_COL_NOMBRE+","+ENTRENAMIENTO_COL_FECHA+","+ENTRENAMIENTO_COL_HORAS+","+ ENTRENAMIENTO_COL_MINUTOS+ ","+ENTRENAMIENTO_COL_SEGUNDOS+","+
                              ENTRENAMIENTO_COL_KILOMETROS+","+ ENTRENAMIENTO_COL_METROS+","+ENTRENAMIENTO_COL_TIPO+" ) "+
                "VALUES ( '"+nombre+"','"+fecha+"',"+horas+","+minutos+","+segundos+","+kilometros+","+metros+",'"+tipo+"'"+")";
        System.out.println(insert.toString());


        SQLiteDatabase db = this.getWritableDatabase();
        /* ContentValues values = new ContentValues();
        Cursor cursor = null;


        values.put(ENTRENAMIENTO_COL_NOMBRE, nombre);
        values.put(ENTRENAMIENTO_COL_FECHA, fecha);
        values.put(ENTRENAMIENTO_COL_HORAS, horas);
        values.put(ENTRENAMIENTO_COL_MINUTOS, minutos);
        values.put(ENTRENAMIENTO_COL_SEGUNDOS, segundos);
        values.put(ENTRENAMIENTO_COL_KILOMETROS, kilometros);
        values.put(ENTRENAMIENTO_COL_METROS, metros);
        values.put(ENTRENAMIENTO_COL_TIPO, tipo);*/

        System.out.println("VES IAGO QUE SI ETA ENTRANDO MECAGOENDIOSESTOYMAMADISIMO");

        try {
            db.beginTransaction();
            /*cursor = db.query(TABLA_ENTRENAMIENTO, null, null, null, null,
                    null, null);

            db.insert(TABLA_ENTRENAMIENTO, null, values);*/
            db.execSQL(insert);

            db.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("DBManager.inserta", exc.getMessage());
        } finally {

            db.endTransaction();
        }
    }

    /**
     * Elimina un elemento de la base de datos
     *
     * @param id El identificador del elemento.
     * @return true si se pudo eliminar, false en otro caso.
     */
    public boolean eliminarEntrenamiento(int id) {
        boolean toret = false;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.beginTransaction();
            db.delete(TABLA_ENTRENAMIENTO, ENTRENAMIENTO_COL_ID + "=?", new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();
            toret = true;
        } catch (SQLException exc) {
            Log.e("DBManager.elimina", exc.getMessage());
        } finally {
            db.endTransaction();
        }

        return toret;
    }

    public void modificaContacto(int id, String nombre, String fecha, int horas, int minutos, int segundos, int kilometros, int metros, String tipo) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor cursor = null;

        values.put(ENTRENAMIENTO_COL_NOMBRE, nombre);
        values.put(ENTRENAMIENTO_COL_FECHA, fecha);
        values.put(ENTRENAMIENTO_COL_HORAS, horas);
        values.put(ENTRENAMIENTO_COL_SEGUNDOS, minutos);
        values.put(ENTRENAMIENTO_COL_KILOMETROS, kilometros);
        values.put(ENTRENAMIENTO_COL_METROS, metros);
        values.put(ENTRENAMIENTO_COL_TIPO, tipo);

        try {
            db.beginTransaction();
            cursor = db.query(TABLA_ENTRENAMIENTO, null, null, null, null,
                    null, null);

            db.update(TABLA_ENTRENAMIENTO, values, ENTRENAMIENTO_COL_ID + "=?", new String[]{String.valueOf(id)});

            db.setTransactionSuccessful();
        } catch (SQLException exc) {
            Log.e("DBManager.inserta", exc.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }

            db.endTransaction();
        }

    }
}
