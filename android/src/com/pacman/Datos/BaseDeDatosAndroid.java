package com.pacman.Datos;

import android.content.Context;

import com.pacman.BaseDeDatos;

public class BaseDeDatosAndroid implements BaseDeDatos {

    private final ServicioBD basedeDatos;

    public BaseDeDatosAndroid(Context contexto) {
        this.basedeDatos = new ServicioBD(contexto);
    }

    public void leerJugador(String nombre) {
        this.basedeDatos.getJugadorByUsuario(nombre);
    }
}

