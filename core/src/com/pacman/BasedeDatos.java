package com.pacman;

public interface BaseDeDatos {
    //interfaz de la base de datos, utilizada para hacer el vinculo con la base de datos creada para el dispositivo android
    //esta interfaz establece los metodos que se van a poder utilizar sobre la base de datos

    public void crearJugador(String nombre, String contraseña);

    public void logIn(String nombre, String contraseña);

    public void actualizarPuntaje(String nombre, int puntos);

    public void mostrarDatos();
}