package com.pacman.desktop;

import com.badlogic.gdx.Gdx;
import com.pacman.BaseDeDatos;

public class BaseDeDatosDesktop implements BaseDeDatos {

    @Override

    public boolean crearJugador(String nombre, String contrasena) {
        Gdx.app.log("BaseDeDatosDesktop", "Se intento acceder a la base de datos de escritorio");
        return false;
    }

    @Override
    public boolean logIn(String nombre, String contrasena) {
        Gdx.app.log("BaseDeDatosDesktop", "Se intento acceder a la base de datos de escritorio");
        return false;
    }

    @Override
    public boolean actualizarPuntaje(String nombre, int puntos) {
        Gdx.app.log("BaseDeDatosDesktop", "Se intento acceder a la base de datos de escritorio");
        return false;
    }

    @Override
    public void mostrarDatos() {
        Gdx.app.log("BaseDeDatosDesktop", "Se intento acceder a la base de datos de escritorio");
    }
}
