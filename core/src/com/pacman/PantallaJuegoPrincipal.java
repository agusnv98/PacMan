package com.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pacman.Actores.PacMan;

public class PantallaJuegoPrincipal extends PantallaBase {

    public PantallaJuegoPrincipal(JuegoPrincipal juego) {
        super(juego);
    }

    private Stage escenario;
    private PacMan pacman;

    @Override
    public void show() {
        escenario = new Stage();
        pacman = new PacMan();
        escenario.addActor(pacman);
        pacman.setPosition(100, 20);
    }

    @Override
    public void hide() {
        escenario.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        escenario.act();
        escenario.draw();
    }
}