package com.pacman;

import com.badlogic.gdx.InputAdapter;

public class Procesador extends InputAdapter {

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("scrreX" + screenX + "screenY" + screenY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}
