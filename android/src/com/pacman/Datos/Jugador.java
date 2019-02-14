package com.pacman.Datos;

import android.content.ContentValues;

public class Jugador {

    private String usuario;
    private String contraseña;
    private int maxPuntaje;

    public Jugador(String nombre, String contraseña, int puntos) {
        this.usuario = nombre;
        this.contraseña = contraseña;
        this.maxPuntaje = puntos;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public String getContraseña() {
        return this.contraseña;
    }

    public int getMaxPuntaje() {
        return this.maxPuntaje;
    }

    public ContentValues toContentValues() {
        ContentValues valores = new ContentValues();
        valores.put(JugadorContract.JugadorEntry.USUARIO, this.usuario);
        valores.put(JugadorContract.JugadorEntry.CONTRASEÑA, this.contraseña);
        valores.put(JugadorContract.JugadorEntry.PUNTAJE, this.maxPuntaje);
        return valores;
    }
}
