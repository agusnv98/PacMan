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

import java.util.ArrayList;

public class Fantasma extends Personaje {

    private int fantasmaId;
    private static final float duracionFrame = 0.15f;

    private Animation animVolviendo, animDebilitado;

    public Fantasma(Texture animaciones, Rectangle respawn, int id) {
        super(respawn);
        //ver si implementar el estado quieto
        this.estados.add("debilitado");
        this.estados.add("volviendo");
        int ejeY = 20, aumento = 16; //bases para obtener las animaciones
        //por default el id es 0
        switch (id) {
            case 1:
                this.fantasmaId = 1;//la posicion del fantasma con id 1 no se modifica
                break;
            case 2:
                this.fantasmaId = 2;
                this.limites.setPosition(this.limites.getX()+16,this.limites.getY());
                break;
            case 3:
                this.fantasmaId = 3;
                this.limites.setPosition(this.limites.getX()-16,this.limites.getY());
                break;
            default:                //por defecto se crea con id 0;
                this.fantasmaId = 0;
                this.limites.setPosition(this.limites.getX()-16,this.limites.getY());
                break;
        }
        this.setPosition(this.limites.getX(),this.limites.getY()); //establezco la posicion del actor donde corresponde
        establecerAnimaciones(animaciones, ejeY + (aumento * this.fantasmaId));
        this.animActual= this.animDer;

        direccion = new Vector2(0, 0); //hay que hacer que siempre choque contra las paredes arriba,abajo
        this.animActual = animArriba;
        this.estadoActual = 7; //estado arriba
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
                default:
                    direccion = new Vector2(0,0);
                    break;
            }
        } else {
            exito = false;
        }
        return exito;
    }

    //ver como moverlo a personaje/////////////////////////////////////////////
    @Override
    public void act(float delta) {
        tiempoFrame += delta;
        frameActual = (TextureRegion) animActual.getKeyFrame(tiempoFrame, true);
        mover(this.estadoActual, delta);
        if (getX() > Gdx.graphics.getWidth() + frameActual.getRegionWidth()) {
            setPosition(0 - frameActual.getRegionWidth(), getY());
        }
    }

    private void mover(int estado, float delta) {
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
                //verificar el tema de la direccion
                this.direccion = new Vector2(delta, 0);
                //System.out.println("Estado Debilitado");
                break;
            /*case 5:
                this.direccion = new Vector2(0, 0);
                System.out.println("Estado muerto");
                break;
            */
        }
        this.direccion.scl(VELOCIDAD);
        setPosition(getX() + this.direccion.x, getY() + this.direccion.y);
    }

    public int getId() {
        return this.fantasmaId;
    }
}
