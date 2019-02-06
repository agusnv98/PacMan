package com.pacman.Datos;

import android.provider.*;

public final class JugadoresContract {

    private JugadoresContract() {
    }

    public static class JugadoresEntry implements BaseColumns {

        public static final String nombreTabla = "jugador";
        public static final String usuario = "usuario";
        public static final String contraseña = "contraseña";
        public static final String maxPuntaje = "maximoPuntaje";
    }
}
