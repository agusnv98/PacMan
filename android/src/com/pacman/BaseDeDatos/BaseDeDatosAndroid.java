package com.pacman.BaseDeDatos;

import android.content.Context;
import com.pacman.BasedeDatos;

public class BaseDeDatosAndroid implements BasedeDatos {

    private final ServicioBD basedeDatos;

    public BaseDeDatosAndroid(Context contexto) {
        this.basedeDatos = new ServicioBD(contexto);
    }
}
