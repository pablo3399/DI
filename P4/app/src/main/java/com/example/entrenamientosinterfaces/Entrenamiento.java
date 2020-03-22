package com.example.entrenamientosinterfaces;

import java.util.Date;

public class Entrenamiento {

    private int id;
    private String nombre;
    private Date fecha;
    private int horas;
    private int minutos;
    private int segundos;
    private int kilometros;
    private int metros;
    private String tipo;
    private int color_resource;

    public Entrenamiento(String nombre, Date fecha, int horas, int minutos, int segundos, int kilometros, int metros, String tipo) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.horas = horas;
        this.minutos = minutos;
        this.segundos = segundos;
        this.kilometros = kilometros;
        this.metros = metros;
        this.tipo = tipo;
    }

    public Entrenamiento(){
        super();
    }

    public Entrenamiento(String nombre){

        this.nombre = nombre;
        this.fecha = fecha;
        this.horas = horas;
        this.minutos = minutos;
        this.segundos = segundos;
        this.kilometros = kilometros;
        this.metros = metros;
        this.tipo = tipo;
    }

    public float minutosPorKilometro (){
        return this.horas/this.kilometros;
    }

    public float kilometrosPorHora(){
        return this.kilometros/this.horas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public int getSegundos() {
        return segundos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

    public int getKilometros() {
        return kilometros;
    }

    public void setKilometros(int kilometros) {
        this.kilometros = kilometros;
    }

    public int getMetros() {
        return metros;
    }

    public void setMetros(int metros) {
        this.metros = metros;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public int getColorResource() {
        return color_resource;
    }

    public void setColorResource(int color_resource) {
        this.color_resource = color_resource;
    }

    @Override
    public String toString() {
        return "Nombre: " +nombre +" ; Tiempo: " + horas +" ; Distancia: "+ kilometros;
    }


}
