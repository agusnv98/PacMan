package com.pacman.Actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import org.w3c.dom.css.Rect;

import java.util.ArrayList;

public class PacMan extends Personaje {
    private Animation animEvolucionado;

    public PacMan(Texture animaciones, Rectangle respawn) {
        super(respawn);
        this.estados.add("evolucionado");
        this.estados.add("muerto");
        this.estados.add("quieto");
        this.estadoActual = 6; //estado quieto
        this.setPosition(this.limites.getX(), this.limites.getY()); //establezco la posicion del actor donde corresponde
        this.establecerAnimaciones(animaciones);
        this.animActual = this.animDer;

        System.out.println("PosPacMan" + getX() + "///" + getY());
        System.out.println("DimRec" + limites.width + "//" + limites.height);
        System.out.println("PosRect" + limites.x + "//" + limites.y);
    }

    public Rectangle getLimites() {
        return limites;
    }

    //ver si public o private
    public void setXY(float pX, float pY) {
        setPosition(pX, pY);
        limites.setX(pX);
        limites.setY(pY);
    }

    public Vector2 getDireccion() {
        return this.direccion;
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
        System.out.println(pos);/////////////////////////////////////////////////
        if (pos != -1 && this.estadoActual != pos) {
            this.estadoActual = pos;
            switch (this.estadoActual) {
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
                    //faltan las 4 direcciones de evolucionado
                    this.animActual = this.animEvolucionado;
                    break;
                case 5:
                    //para reiniciar la animacion hay que volvera crearla aca-------------------
                    this.animActual = this.animMuerto;
                    break;
                //para el estado quieto (index 6, no se cambia la animacion)
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
        moverse(this.estadoActual, delta);
        if (getX() > Gdx.graphics.getWidth() + frameActual.getRegionWidth()) {
            setPosition(0 - frameActual.getRegionWidth(), getY());
        }else if (getX() +frameActual.getRegionWidth() < 0){
            setPosition(Gdx.graphics.getWidth(), getY());
        }
    }

    private void moverse(int estado, float delta) {
        switch (this.estadoActual) {
            case 0:
                this.direccion = new Vector2(-delta, 0);
                //System.out.println("Estado izquierda");
                break;
            case 1:
                this.direccion = new Vector2(delta, 0);
                //System.out.println("Estado derecha");
                break;
            case 2:
                this.direccion = new Vector2(0, delta);
                //System.out.println("Estado arriba");
                break;
            case 3:
                this.direccion = new Vector2(0, -delta);
                //System.out.println("Estado abajo");
                break;
            case 4:
                //hay que poner una animacion evolucionado por cada direccion
                //this.direccion = new Vector2(0, 0);--------------------------------
                //System.out.println("Estado Evolucionado");
                break;
            default:
                this.direccion = new Vector2(0, 0);
                //System.out.println("Estado muerto/quieto");
                break;
        }
        this.direccion.scl(VELOCIDAD);
        setXY(getX() + this.direccion.x, getY() + this.direccion.y);
    }
}
