package com.pacman.desktop;

import com.badlogic.gdx.Gdx;
import com.pacman.BaseDeDatos;

public class BaseDeDatosDesktop implements BaseDeDatos {

    @Override
    public void crearJugador(String nombre, String contraseña) {
        Gdx.app.log("BaseDeDatosDesktop", "Se intento acceder a la base de datos de escritorio");
    }

    @Override
    public void logIn(String nombre, String contraseña) {
        Gdx.app.log("BaseDeDatosDesktop", "Se intento acceder a la base de datos de escritorio");
    }

    @Override
    public void actualizarPuntaje(String nombre, int puntos) {
        Gdx.app.log("BaseDeDatosDesktop", "Se intento acceder a la base de datos de escritorio");
    }

    @Override
    public void mostrarDatos() {
        Gdx.app.log("BaseDeDatosDesktop", "Se intento acceder a la base de datos de escritorio");
    }
}
