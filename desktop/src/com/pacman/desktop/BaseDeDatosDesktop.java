package com.pacman.desktop;

import com.badlogic.gdx.Gdx;
import com.pacman.BaseDeDatos;

public class BaseDeDatosDesktop implements BaseDeDatos {

    public void leerJugador(String nombre) {
        Gdx.app.log("BaseDeDatosDesktop", "Se intento acceder a la base de datos de escritorio");
    }
}
