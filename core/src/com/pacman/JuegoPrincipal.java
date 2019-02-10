package com.pacman;

import com.badlogic.gdx.Game;

public class JuegoPrincipal extends Game {

    private final BasedeDatos baseDeDatos;

    public JuegoPrincipal(BasedeDatos baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }

    @Override
    public void create() {
        setScreen(new PantallaJuegoPrincipal(this));
    }
}
