package com.pacman.Actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

public class Fantasma extends Actor {

    private int fantasmaId;

    public static final float VEL = 30f;
    private static final float duracionFrame = 0.15f;
    float tiempoFrame;

    private ArrayList<String> estados = new ArrayList<String>() {{
        add("izquierda");
        add("derecha");
        add("arriba");
        add("abajo");
        add("debilitado");
        add("volviendo");
    }};
    private int estadoActual;

    private Animation animDer, animDebilitado,
            animIzq, animArriba, animAbajo, animActual;
    private TextureRegion frameActual;

    private Vector2 direccion;

    public Fantasma(Texture animaciones, int id) {
        int ejeY = 20, aumento = 16;
        //por default el id es 0
        if (id < 0 || id >= 4) {
            id = 0;
        }
        establecerAnimaciones(animaciones, ejeY + (aumento * id));
        direccion = new Vector2(0, 0);
        this.animActual = animArriba;
        this.estadoActual = 2;
        this.fantasmaId = id;
    }

    private void establecerAnimaciones(Texture animaciones, int ejeY) {
        animIzq = new Animation(duracionFrame,
                new Sprite(new TextureRegion(animaciones, 34, ejeY, 14, 13)),
                new Sprite(new TextureRegion(animaciones, 50, ejeY, 14, 13)));
        animDer = new Animation(duracionFrame,
                new Sprite(new TextureRegion(animaciones, 2, ejeY, 14, 13)),
                new Sprite(new TextureRegion(animaciones, 18, ejeY, 14, 13)));
        animArriba = new Animation(duracionFrame,
                new Sprite(new TextureRegion(animaciones, 66, ejeY, 14, 13)),
                new Sprite(new TextureRegion(animaciones, 82, ejeY, 14, 13)));
        animAbajo = new Animation(duracionFrame,
                new Sprite(new TextureRegion(animaciones, 98, ejeY, 14, 13)),
                new Sprite(new TextureRegion(animaciones, 114, ejeY, 14, 13)));
        animDebilitado = new Animation(duracionFrame,
                new Sprite(new TextureRegion(animaciones, 2, 84, 14, 13)),
                new Sprite(new TextureRegion(animaciones, 18, 84, 14, 13)),
                new Sprite(new TextureRegion(animaciones, 34, 84, 14, 13)),
                new Sprite(new TextureRegion(animaciones, 50, 84, 14, 13)));
        //animVolviendo;
    }

    public boolean setEstado(String estado) {
        //metodo que cambia al fantasma de estado y establece la animacion correspondiente al estado
        boolean exito = true;
        int pos = estados.indexOf(estado);
        System.out.println(pos);
        if (pos != -1 && this.estadoActual != pos) {
            this.estadoActual = pos;
            switch (this.estadoActual) {
                //para el estado quieto (index 0, no se cambia la animacion)
                case 0:
                    this.animActual = this.animIzq;
                    break;
                case 1:
                    this.animActual = this.animDer;
                    break;
                case 2:
                    this.animActual = this.animArriba;
                    break;
                case 3:
                    this.animActual = this.animAbajo;
                    break;
                case 4:
                    this.animActual = this.animDebilitado;
                    break;
                /*case 5:
                    //establecer animacion volviendo;
                    break;
                */
            }
        } else {
            exito = false;
        }
        return exito;
    }

    @Override
    public void act(float delta) {
        tiempoFrame += delta;
        frameActual = (TextureRegion) animActual.getKeyFrame(tiempoFrame, true);
        mover(this.estadoActual, delta);
        if (getX() > Gdx.graphics.getWidth() + frameActual.getRegionWidth()) {
            setPosition(0 - frameActual.getRegionWidth(), getY());
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(frameActual, getX(), getY());
    }

    private void mover(int estado, float delta) {
        switch (this.estadoActual) {
            case 0:
                this.direccion = new Vector2(-delta, 0);
                System.out.println("Estado izquierda");
                break;
            case 1:
                this.direccion = new Vector2(delta, 0);
                System.out.println("Estado derecha");
                break;
            case 2:
                this.direccion = new Vector2(0, delta);
                System.out.println("Estado arriba");
                break;
            case 3:
                this.direccion = new Vector2(0, -delta);
                System.out.println("Estado abajo");
                break;
            case 4:
                //verificar el tema de la direccion
                this.direccion = new Vector2(delta, 0);
                System.out.println("Estado Debilitado");
                break;
            /*case 5:
                this.direccion = new Vector2(0, 0);
                System.out.println("Estado muerto");
                break;
            */
        }
        this.direccion.scl(VEL);
        setPosition(getX() + this.direccion.x, getY() + this.direccion.y);
    }

    public String getEstado() {
        return this.estados.get(this.estadoActual);
    }

    public int getId() {
        return this.fantasmaId;
    }
}
