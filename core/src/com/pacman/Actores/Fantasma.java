package com.pacman.Actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pacman.Mundo;

public class Fantasma extends Personaje {

    private int fantasmaId;
    private static final float duracionFrame = 0.15f;

    private Animation animVolviendo, animDebilitado;

    public Fantasma(Texture texturas, Rectangle respawn, int id, Mundo mundo) {
        super(respawn, mundo);
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
                this.limites.setPosition(this.limites.getX() + 16, this.limites.getY());
                break;
            case 3:
                this.fantasmaId = 3;
                this.limites.setPosition(this.limites.getX() - 16, this.limites.getY());
                break;
            default:                //por defecto se crea con id 0;
                this.fantasmaId = 0;
                this.limites.setPosition(this.limites.getX() - 16, this.limites.getY());
                break;
        }
        this.setPosition(this.limites.getX(), this.limites.getY()); //establezco la posicion del actor donde corresponde
        establecerAnimaciones(texturas, ejeY + (aumento * this.fantasmaId));
        this.animActual = animArriba;
        this.estadoActual = 2; //estado arriba
    }

    private void establecerAnimaciones(Texture texturas, int ejeY) {
        animIzq = new Animation<Sprite>(duracionFrame,
                new Sprite(new TextureRegion(texturas, 34, ejeY, 14, 13)),
                new Sprite(new TextureRegion(texturas, 50, ejeY, 14, 13)));
        animDer = new Animation<Sprite>(duracionFrame,
                new Sprite(new TextureRegion(texturas, 2, ejeY, 14, 13)),
                new Sprite(new TextureRegion(texturas, 18, ejeY, 14, 13)));
        animArriba = new Animation<Sprite>(duracionFrame,
                new Sprite(new TextureRegion(texturas, 66, ejeY, 14, 13)),
                new Sprite(new TextureRegion(texturas, 82, ejeY, 14, 13)));
        animAbajo = new Animation<Sprite>(duracionFrame,
                new Sprite(new TextureRegion(texturas, 98, ejeY, 14, 13)),
                new Sprite(new TextureRegion(texturas, 114, ejeY, 14, 13)));
        animDebilitado = new Animation<Sprite>(duracionFrame,
                new Sprite(new TextureRegion(texturas, 2, 84, 14, 13)),
                new Sprite(new TextureRegion(texturas, 18, 84, 14, 13)),
                new Sprite(new TextureRegion(texturas, 34, 84, 14, 13)),
                new Sprite(new TextureRegion(texturas, 50, 84, 14, 13)));
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
                    direccion = new Vector2(0, 0);
                    break;
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
        mover(delta);
        if (getX() > Gdx.graphics.getWidth() + frameActual.getRegionWidth()) {
            setPosition(0 - frameActual.getRegionWidth(), getY());
        }
    }

    private void mover(float delta) {
        Rectangle pared;
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
        setXY(getX() + this.direccion.x,getY() + this.direccion.y);
        pared = this.mundo.verificarColisionPared(this);
        if (pared != null) {
            reacomodar(pared);
        }
    }

    public int getId() {
        return this.fantasmaId;
    }
}
