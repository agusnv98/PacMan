package com.pacman.Actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PacMan extends Actor {
    public static final float VEL = 10f;
    private TextureRegion imagen;

    Animation animacion;
    TextureRegion frameActual;
    float tiempoEstado;

    public PacMan(TextureRegion img) {
        imagen = img;
    }

    public PacMan(Texture animaciones) {
        animacion = new Animation(0.25f,
                new Sprite(new TextureRegion(animaciones, 4, 1, 9, 13)),
                new Sprite(new TextureRegion(animaciones, 20, 1, 12, 13)),
                new Sprite(new TextureRegion(animaciones, 36, 1, 13, 13)),
                new Sprite(new TextureRegion(animaciones, 20, 1, 12, 13)));
        frameActual= new Sprite(new TextureRegion(animaciones, 4, 1, 9, 13));
    }

    @Override
    public void act(float delta){update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //, imagen.getRegionWidth() + 100, imagen.getRegionHeight() + 100
        batch.draw(frameActual, getX(), getY());
    }

    public void update(float dt) {
        tiempoEstado += dt;
        frameActual = (TextureRegion) animacion.getKeyFrame(tiempoEstado, true);
        move(new Vector2(dt, 0));

        if (getX() > Gdx.graphics.getWidth() + frameActual.getRegionWidth()) {
            setPosition(0 - frameActual.getRegionWidth(), getY());
        }
    }

    public void move(Vector2 direccion) {
        direccion.scl(VEL);
        setPosition(getX() + direccion.x, getY() + direccion.y);
    }
}
