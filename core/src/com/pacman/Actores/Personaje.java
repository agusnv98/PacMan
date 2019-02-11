package com.pacman.Actores;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pacman.Mundo;

import java.util.ArrayList;

public abstract class Personaje extends Actor {

    protected static final float VELOCIDAD = 35f;
    protected static final float duracionFrame = 0.1f;
    protected float tiempoFrame;

    protected int estadoActual;
    protected Rectangle respawn;
    protected Mundo mundo;
    protected Rectangle limites;
    protected Vector2 direccion;
    protected TextureRegion frameActual;

    protected Animation animIzq, animDer, animArriba,
            animAbajo, animMuerto, animActual;

    protected static final ArrayList<String> estados = new ArrayList<String>() {{
        add("izquierda");
        add("derecha");
        add("arriba");
        add("abajo");
    }};

    public Personaje(Rectangle respawn, Mundo mundo) {
        //ver el tema de las animaciones, aunque no se podria implementar aca
        this.mundo=mundo;
        this.respawn = respawn;
        this.direccion = new Vector2(0, 0); //se crea quieto
        this.estadoActual = 1; //se crea por defecto con estado izquierda
        this.limites = new Rectangle(this.respawn.getX(), this.respawn.getY(), this.respawn.getWidth() - 2, this.respawn.getHeight() - 2);
    }

    public abstract boolean setEstado(String estado);

    public String getEstado() {
        return this.estados.get(this.estadoActual);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(frameActual, getX(), getY());
    }
}
