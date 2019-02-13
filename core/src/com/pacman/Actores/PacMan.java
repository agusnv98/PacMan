package com.pacman.Actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.pacman.Mundo;

public class PacMan extends Personaje {
    private Animation animMuerto;
    private boolean muerto = false;

    public PacMan(Texture texturas, Rectangle respawn, Mundo mundo) {
        super(respawn, mundo);
        this.estados.add("muerto");
        this.estados.add("quieto");
        this.estados.add("evolucionado");
        this.estados.add("diagonalArribaIzq");
        this.estados.add("diagonalArribaDer");
        this.estados.add("diagonalAbajoIzq");
        this.estados.add("diagonalAbajoDer");
        this.estadoActual = 6; //estado quieto
        this.setPosition(this.limites.getX(), this.limites.getY()); //establezco la posicion del actor donde corresponde
        this.establecerAnimaciones(texturas);
        this.animActual = this.animDer;

        System.out.println("PosPacMan" + getX() + "///" + getY());
        System.out.println("DimRec" + this.limites.width + "//" + this.limites.height);
        System.out.println("PosRect" + this.limites.x + "//" + this.limites.y);
    }

    @Override
    public void act(float delta) {
        this.tiempoFrame += delta;
        if (this.estadoActual != 4) {                  //se verifica si pacman esta muerto (estado 5)
            this.frameActual = (TextureRegion) this.animActual.getKeyFrame(this.tiempoFrame);
        } else {
            if (!muerto) {
                this.tiempoFrame = 0;
                this.muerto = true;
            }
            this.frameActual = (TextureRegion) this.animActual.getKeyFrame(this.tiempoFrame);
            if (this.animActual.isAnimationFinished(tiempoFrame)) {
                revivir();
            }
        }
        mover(delta);
        if (getX() > Gdx.graphics.getWidth() + this.frameActual.getRegionWidth()) {
            setPosition(0 - this.frameActual.getRegionWidth(), getY());
        } else if (getX() + this.frameActual.getRegionWidth() < 0) {
            setPosition(Gdx.graphics.getWidth(), getY());
        }
    }

    public boolean setEstado(String estado) {
        //metodo que cambia al pacman de estado y establece la animacion correspondiente al estado
        boolean exito = true;
        int pos = this.estados.indexOf(estado);
        //System.out.println("Estado Actual"+pos);
        if (pos != -1 && this.estadoActual != pos && !this.muerto) {
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
                    this.animActual = this.animMuerto;
                    break;
                //para el estado quieto y evolucionado (index 5 y 6) no se cambia la animacion
            }
        } else {
            exito = false;
        }
        return exito;
    }

    private void revivir() {
        muerto = false;
        this.direccion = new Vector2(0, 0); //se crea quieto
        this.estadoActual = 2; //se crea por defecto con estado derecha
        this.animActual = this.animDer;
        /*this.limites = new Rectangle(this.respawn.getX(), this.respawn.getY(), this.respawn.getWidth() - 3, this.respawn.getHeight() - 3);
        this.setXY(this.limites.getX()+1.5f, this.limites.getY()+1.5f);*/
        this.limites = new Rectangle(this.respawn.getX(), this.respawn.getY(), this.respawn.getWidth() - 2, this.respawn.getHeight() - 2);
        this.setXY(this.limites.getX(), this.limites.getY());
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
                this.direccion = new Vector2(0, 0);
                //System.out.println("Estado muerto");
                break;
            case 5:
                this.direccion = new Vector2(0, 0);
                //System.out.println("Estado quieto");
                break;
            case 6:
                //System.out.println("Estado evolucionado");
            case 7:
                this.direccion = new Vector2(-delta, delta);
                break;
            case 8:
                this.direccion = new Vector2(delta, delta);

                break;
            case 9:
                this.direccion = new Vector2(-delta, -delta);
                break;
            case 10:
                this.direccion = new Vector2(delta, -delta);
                break;
        }
        this.direccion.scl(this.VELOCIDAD);
        setXY(getX() + this.direccion.x, getY() + this.direccion.y);
        pared = this.mundo.verificarColisionPared(this);
        if (pared != null) {
            reacomodar(pared);
        }
        this.mundo.verificarColisionPildora();
    }

    public Vector2 getDireccion() {
        return this.direccion;
    }

    private void establecerAnimaciones(Texture texturas) {
        //metodo que incializa las animaciones del PacMan
        this.animIzq = new Animation<Sprite>(this.duracionFrame, getAnimIzq(texturas), Animation.PlayMode.LOOP);
        this.animDer = new Animation<Sprite>(this.duracionFrame, getAnimDer(texturas), Animation.PlayMode.LOOP);
        this.animArriba = new Animation<Sprite>(this.duracionFrame, getAnimArriba(texturas), Animation.PlayMode.LOOP);
        this.animAbajo = new Animation<Sprite>(this.duracionFrame, getAnimAbajo(texturas), Animation.PlayMode.LOOP);
        this.animMuerto = new Animation<Sprite>(this.duracionFrame, getAnimMuerto(texturas), Animation.PlayMode.NORMAL);
    }

    private Array<Sprite> getAnimIzq(Texture texturas) {
        Array<Sprite> frames = new Array<Sprite>();
        frames.add(new Sprite(new TextureRegion(texturas, 194, 58, 14, 14)));
        frames.add(new Sprite(new TextureRegion(texturas, 179, 58, 14, 14)));
        frames.add(new Sprite(new TextureRegion(texturas, 132, 58, 14, 14)));
        frames.add(new Sprite(new TextureRegion(texturas, 179, 58, 14, 14)));
        return frames;
    }

    private Array<Sprite> getAnimDer(Texture texturas) {
        Array<Sprite> frames = new Array<Sprite>();
        frames.add(new Sprite(new TextureRegion(texturas, 166, 58, 14, 14)));
        frames.add(new Sprite(new TextureRegion(texturas, 149, 58, 12, 14)));
        frames.add(new Sprite(new TextureRegion(texturas, 132, 58, 14, 14)));
        frames.add(new Sprite(new TextureRegion(texturas, 149, 58, 12, 14)));
        return frames;
    }

    private Array<Sprite> getAnimArriba(Texture texturas) {
        Array<Sprite> frames = new Array<Sprite>();
        frames.add(new Sprite(new TextureRegion(texturas, 228, 61, 14, 9)));
        frames.add(new Sprite(new TextureRegion(texturas, 212, 59, 14, 12)));
        frames.add(new Sprite(new TextureRegion(texturas, 132, 58, 14, 14)));
        frames.add(new Sprite(new TextureRegion(texturas, 212, 59, 14, 12)));
        return frames;
    }

    private Array<Sprite> getAnimAbajo(Texture texturas) {
        Array<Sprite> frames = new Array<Sprite>();
        frames.add(new Sprite(new TextureRegion(texturas, 260, 60, 14, 14)));
        frames.add(new Sprite(new TextureRegion(texturas, 244, 59, 14, 14)));
        frames.add(new Sprite(new TextureRegion(texturas, 132, 58, 14, 14)));
        frames.add(new Sprite(new TextureRegion(texturas, 244, 59, 14, 14)));
        return frames;
    }

    private Array<Sprite> getAnimMuerto(Texture texturas) {
        Array<Sprite> frames = new Array<Sprite>();
        frames.add(new Sprite(new TextureRegion(texturas, 131, 78, 16, 8)));
        frames.add(new Sprite(new TextureRegion(texturas, 147, 80, 16, 6)));
        frames.add(new Sprite(new TextureRegion(texturas, 163, 81, 16, 5)));
        frames.add(new Sprite(new TextureRegion(texturas, 179, 81, 16, 5)));
        frames.add(new Sprite(new TextureRegion(texturas, 195, 80, 16, 6)));
        frames.add(new Sprite(new TextureRegion(texturas, 211, 79, 16, 7)));
        frames.add(new Sprite(new TextureRegion(texturas, 227, 79, 16, 7)));
        frames.add(new Sprite(new TextureRegion(texturas, 243, 80, 16, 6)));
        frames.add(new Sprite(new TextureRegion(texturas, 259, 80, 16, 6)));
        frames.add(new Sprite(new TextureRegion(texturas, 274, 76, 16, 10)));
        return frames;
    }


}
