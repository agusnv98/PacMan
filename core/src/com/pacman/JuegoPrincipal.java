package com.pacman;

import com.badlogic.gdx.Game;

public class JuegoPrincipal extends Game {

    private final BaseDeDatos baseDeDatos;

    public JuegoPrincipal(BaseDeDatos baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }

    @Override
    public void create() {
        setScreen(new PantallaRegistro(this, this.baseDeDatos));
    }
}