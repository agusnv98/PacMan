package com.pacman.Botones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class MiTouch extends Touchpad {


    public MiTouch(float deadzoneRadius, Skin skin) {
        super(deadzoneRadius, skin);
    }



    @Override
    public void act(float delta) {
        super.act(delta);
        if (isTouched()) {
            System.out.println("X" + this.getKnobX() + "----------+" + this.getKnobY() + "Y");
        }
    }
}
