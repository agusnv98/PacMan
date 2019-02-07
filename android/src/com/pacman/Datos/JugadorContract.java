package com.pacman.Datos;

import android.provider.BaseColumns;

public class JugadorContract {

    //Clase que guarda las caracteristicas de un jugador en la base de datos

    public static abstract class JugadorEntry implements BaseColumns {

        public static final String NOMBRE_TABLA = "jugador";
        public static final String USUARIO = "usuario";
        public static final String CONTRASEÑA = "contraseña";
        public static final String PUNTAJE = "puntaje maximo";
    }
}
