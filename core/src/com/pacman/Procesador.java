package com.pacman;

import com.badlogic.gdx.InputAdapter;

public class Procesador extends InputAdapter {

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("Posicion X = " + screenX + " Posicion Y = " + screenX);
        System.out.println("Dedo = " + pointer + " boton = " + button);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }

}
