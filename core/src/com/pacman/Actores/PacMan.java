package com.pacman.Actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PacMan extends Actor {

    private TextureRegion imagen;

    public PacMan(TextureRegion img){
        imagen=img;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(imagen,getX(),getY(),imagen.getRegionWidth()+100,imagen.getRegionHeight()+100);
    }
}
