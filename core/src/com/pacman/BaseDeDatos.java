package com.pacman;

public interface BaseDeDatos {

    public boolean crearJugador(String nombre, String contraseña);

    public boolean logIn(String nombre, String contraseña);

    public boolean actualizarPuntaje(String nombre, int puntos);

    public void mostrarDatos();
}