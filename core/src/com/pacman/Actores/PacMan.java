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

public class PacMan extends Actor {
    public static final float VEL = 20f;
    private static final float duracionFrame = 0.1f;
    float tiempoFrame;

    private ArrayList<String> estados = new ArrayList<String>() {{
        add("quieto");
        add("izquierda");
        add("derecha");
        add("arriba");
        add("abajo");
        add("evolucionado");
        add("muerto");
    }};
    private int estadoActual;

    private Animation animDer, animMuerto, animEvolucionado,
            animIzq, animArriba, animAbajo, animActual;
    private TextureRegion frameActual;

    private Vector2 direccion;

    public PacMan(Texture animaciones) {
        establecerAnimaciones(animaciones);
        direccion = new Vector2(0, 0);
        this.animActual = this.animDer;
        this.estadoActual = 0;
    }

    private void establecerAnimaciones(Texture animaciones) {
        //metodo que incializa las animaciones del PacMan
        animIzq = new Animation(duracionFrame,
                new Sprite(new TextureRegion(animaciones, 194, 58, 14, 14)),
                new Sprite(new TextureRegion(animaciones, 179, 58, 14, 14)),
                new Sprite(new TextureRegion(animaciones, 132, 58, 14, 14)),
                new Sprite(new TextureRegion(animaciones, 179, 58, 14, 14)));
        animDer = new Animation(duracionFrame,
                new Sprite(new TextureRegion(animaciones, 166, 58, 14, 14)),
                new Sprite(new TextureRegion(animaciones, 149, 58, 12, 14)),
                new Sprite(new TextureRegion(animaciones, 132, 58, 14, 14)),
                new Sprite(new TextureRegion(animaciones, 149, 58, 12, 14)));
        animArriba = new Animation(duracionFrame,
                new Sprite(new TextureRegion(animaciones, 228, 61, 14, 9)),
                new Sprite(new TextureRegion(animaciones, 212, 59, 14, 12)),
                new Sprite(new TextureRegion(animaciones, 132, 58, 14, 14)),
                new Sprite(new TextureRegion(animaciones, 212, 59, 14, 12)));
        animAbajo = new Animation(duracionFrame,
                new Sprite(new TextureRegion(animaciones, 260, 60, 14, 14)),
                new Sprite(new TextureRegion(animaciones, 244, 59, 14, 14)),
                new Sprite(new TextureRegion(animaciones, 132, 58, 14, 14)),
                new Sprite(new TextureRegion(animaciones, 244, 59, 14, 14)));
        animMuerto = new Animation(duracionFrame,
                new Sprite(new TextureRegion(animaciones, 131, 78, 16, 8)),
                new Sprite(new TextureRegion(animaciones, 147, 80, 16, 6)),
                new Sprite(new TextureRegion(animaciones, 163, 81, 16, 5)),
                new Sprite(new TextureRegion(animaciones, 179, 81, 16, 5)),
                new Sprite(new TextureRegion(animaciones, 195, 80, 16, 6)),
                new Sprite(new TextureRegion(animaciones, 211, 79, 16, 7)),
                new Sprite(new TextureRegion(animaciones, 227, 79, 16, 7)),
                new Sprite(new TextureRegion(animaciones, 243, 80, 16, 6)),
                new Sprite(new TextureRegion(animaciones, 259, 80, 16, 6)),
                new Sprite(new TextureRegion(animaciones, 274, 76, 16, 10)));
    }

    public boolean setEstado(String estado) {
        //metodo que cambia al pacman de estado y establece la animacion correspondiente al estado
        boolean exito = true;
        int pos = estados.indexOf(estado);
        System.out.println(pos);
        if (pos != -1 && this.estadoActual != pos) {
            this.estadoActual = pos;
            switch (this.estadoActual) {
                //para el estado quieto (index 0, no se cambia la animacion)
                case 1:
                    this.animActual = this.animIzq;
                    break;
                case 2:
                    this.animActual = this.animDer;
                    break;
                case 3:
                    this.animActual = this.animArriba;
                    break;
                case 4:
                    this.animActual = this.animAbajo;
                    break;
                case 5:
                    this.animActual = this.animEvolucionado;
                    break;
                case 6:
                    this.animActual = this.animMuerto;
                    break;
            }
        } else {
            exito = false;
        }
        return exito;
    }

    public String getEstado() {
        return this.estados.get(this.estadoActual);
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
                //ver para cuando muera el tema de la animacion en setEstado y aca
                this.direccion = new Vector2(0, 0);
                System.out.println("Estado quieto");
                break;
            case 1:
                this.direccion = new Vector2(-delta, 0);
                System.out.println("Estado izquierda");
                break;
            case 2:
                this.direccion = new Vector2(delta, 0);
                System.out.println("Estado derecha");
                break;
            case 3:
                this.direccion = new Vector2(0, delta);
                System.out.println("Estado arriba");
                break;
            case 4:
                this.direccion = new Vector2(0, -delta);
                System.out.println("Estado abajo");
                break;
            case 5:
                //mantiene la direccion con la que venia
                //this.direccion = new Vector2(0, 0);--------------------------------
                System.out.println("Estado Evolucionado");
                break;
            case 6:
                this.direccion = new Vector2(0, 0);
                System.out.println("Estado muerto");
                break;
        }
        this.direccion.scl(VEL);
        setPosition(getX() + this.direccion.x, getY() + this.direccion.y);
    }
}
