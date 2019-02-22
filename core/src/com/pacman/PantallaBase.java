package com.pacman;

import com.badlogic.gdx.Screen;

public abstract class PantallaBase implements Screen {
    protected JuegoPrincipal juegoPrincipal;

    public PantallaBase(JuegoPrincipal principal) {
        juegoPrincipal= principal;

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