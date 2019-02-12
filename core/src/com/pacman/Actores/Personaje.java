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

    protected Animation animIzq, animDer, animArriba, animAbajo, animActual;

    protected ArrayList<String> estados = new ArrayList<String>() {{
        add("izquierda");
        add("derecha");
        add("arriba");
        add("abajo");
    }};

    public Personaje(Rectangle respawn, Mundo mundo) {
        //ver el tema de las animaciones, aunque no se podria implementar aca
        this.mundo = mundo;
        this.respawn = respawn;
        this.direccion = new Vector2(0, 0); //se crea quieto
        this.estadoActual = 2; //se crea por defecto con estado izquierda
        this.limites = new Rectangle(this.respawn.getX(), this.respawn.getY(), this.respawn.getWidth(), this.respawn.getHeight());
    }

    public abstract boolean setEstado(String estado);

    public String getEstado() {
        return this.estados.get(this.estadoActual);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(frameActual, getX(), getY());
    }

    public void setXY(float pX, float pY) {
        setPosition(pX, pY);
        this.limites.setX(pX);
        this.limites.setY(pY);
    }

    public void reacomodar(Rectangle pared) {
        //System.out.println("Colision  Personaje X=" + this.limites.getX() + "// Y= " + this.limites.getY() + "|| Pared X = " + pared.getX() + "//" + (pared.getY()));
        //System.out.println("Ancho pared" + pared.getWidth() + "Alto" + pared.getHeight());
        //System.out.println("Direccion" + this.direccion.x + "//" + this.direccion.y);
        Rectangle limitesPacMan = this.limites;
        if (this.direccion.x > 0) {//va hacia la derecha
            float diferencia = (limitesPacMan.getX() + limitesPacMan.getWidth()) - pared.getX();
            this.setXY(limitesPacMan.getX() - diferencia, limitesPacMan.getY());
        } else if (this.direccion.x < 0) {//va hacia la izquierda
            float diferencia = (pared.getX() + pared.getWidth()) - limitesPacMan.getX();
            this.setXY(limitesPacMan.getX() + diferencia, limitesPacMan.getY());
        } else if (this.direccion.y > 0) {//va hacia arriba
            float diferencia = (limitesPacMan.getY() + limitesPacMan.getHeight()) - pared.getY();
            this.setXY(limitesPacMan.getX(), limitesPacMan.getY() - diferencia);
        } else if (this.direccion.y < 0) {//va hacia abajo
            float diferencia = (pared.getY() + pared.getHeight()) - limitesPacMan.getY();
            this.setXY(limitesPacMan.getX(), limitesPacMan.getY() + diferencia);
        }
    }

    public Rectangle getLimites() {
        return limites;
    }
}
