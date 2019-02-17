package com.pacman;

public interface BaseDeDatos {

    public void crearJugador(String nombre, String contraseña);

    public void logIn(String nombre, String contraseña);

    public void actualizarPuntaje(String nombre, int puntos);

    public void mostrarDatos();
}