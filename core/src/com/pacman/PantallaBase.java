package com.pacman;

import com.badlogic.gdx.Screen;

public abstract class PantallaBase implements Screen {

    protected JuegoPrincipal juego;

    public PantallaBase (JuegoPrincipal juego){
        this.juego = juego;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
