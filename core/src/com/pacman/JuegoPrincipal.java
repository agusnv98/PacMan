package com.pacman;

import com.badlogic.gdx.Game;

public class JuegoPrincipal extends Game {
    //clase principal sobre la que se establece el juegp
    //en esta se crean las diferentes pantallas y recibe la base de datos con la que se operara en el juego
    public final BaseDeDatos baseDeDatos;

    private PantallaJuegoPrincipal pantallaJuegoPrincipal;
    private PantallaFinDelJuego pantallaFinDelJuego;

    public JuegoPrincipal(BaseDeDatos baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }

    @Override
    public void create() {
        //Metodo que se llama cuando la aplicación es creada (antes de iniciar el game loop)
        //en este se inicializan las pantallas y se establece la pantalla principal
        this.pantallaJuegoPrincipal = new PantallaJuegoPrincipal(this);
        this.pantallaFinDelJuego = new PantallaFinDelJuego(this);
        setScreen(this.pantallaJuegoPrincipal);
    }

    public PantallaFinDelJuego getPantallaFinDelJuego() {
        return this.pantallaFinDelJuego;
    }

    public PantallaJuegoPrincipal getPantallaJuegoPrincipal() {
        return this.pantallaJuegoPrincipal;
    }

}

