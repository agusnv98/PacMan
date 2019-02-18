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
    private boolean evolucionado = false;
    private int cantVidas = 3;

    public PacMan(Texture texturas, Rectangle respawn, Mundo mundo) {
        super(respawn, mundo);
        //se agregan los estados propios del PacMan
        this.estados.add("muerto");
        this.estados.add("quieto");
        this.estados.add("evolucionado");
        this.estadoActual = 5;                             //se establece el estado quieto
        this.setPosition(this.limites.getX(), this.limites.getY()); //establezco la posicion del actor a donde corresponde
        this.establecerAnimaciones(texturas);
        this.animActual = this.animDer;                    //se establece la animacion derecha como inicial
        /*System.out.println("PosPacMan" + getX() + "///" + getY());
        System.out.println("DimRec" + this.limites.width + "//" + this.limites.height);
        System.out.println("PosRect" + this.limites.x + "//" + this.limites.y);*/
    }

    @Override
    public void act(float delta) {
        if (this.cantVidas > 0) {
            //si el PacMan un tiene vidas, sigue el juego
            super.act(delta);
        } else {
            //si no tiene vidas, se notifica al mundo que termina el juego
            this.mundo.setFinDelJuego();
            System.out.println("Fin del Juego");
        }
    }

    public boolean setEstado(String estado) {
        //metodo que cambia al pacman de estado y establece la animacion correspondiente al estado recibido por parametro
        //el metodo retorna true si se pudo cambiar el estado, false en caso contrario
        boolean exito = true;
        int estadoAux = this.estados.indexOf(estado);
        //System.out.println("Estado Actual"+pos);
        if (estadoAux != -1 && this.estadoActual != estadoAux && !this.inicioAnimMuerto) {
            //si el pacman recibio el estado muerto, entonces no debe cambiar mas el estado hasta que revive
            if (!getEstado().equals("muerto")) {
                this.estadoActual = estadoAux;
                //System.out.println(getEstado());
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
                    case 6:
                        //se establece la condicion evolucionado
                        this.evolucionado = true;
                        break;
                }
            }
        } else {
            exito = false;
        }
        return exito;
    }

    protected void revivir() {
        //metodo que se ejecuta cuando termina la animacion de muerte del PacMan
        super.revivir();
        this.evolucionado = false;
        this.cantVidas--;
        this.direccion = new Vector2(0, 0);   //revive quieto
        this.estadoActual = 2;                      //revive con estado el estado derecha
        this.animActual = this.animDer;             //revive con la animacion derecha
    }

    protected void mover(float delta) {
        //metodo que determina la direccion en la que debe moverse el PacMan segun el estado
        //luego traslada al PacMan y verifica si ocurrieron colisiones con paredes, pildoras y/o fantasmas
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

        }
        if (this.estadoActual != 6) {
            // el estado evolucionado mantiene la direccion,
            // por lo tanto si el estado actual es evolucionado, no se debe volver a escalar la direccion
            // caso contrario si lo debe hacer
            this.direccion.scl(this.VELOCIDAD);
        }
        setXY(getX() + this.direccion.x, getY() + this.direccion.y);
        pared = this.mundo.verificarColisionPared(this);
        if (pared != null) {
            reacomodar(pared);
        }
        this.mundo.verificarColisionPildora();
        if (this.mundo.verificarColsionFantasma()) {
            //si el PacMan chooca con un fantasma, este muere
            //System.out.println("Colision con fantasma");
            this.setEstado("muerto");
        } else {
            //System.out.println("No murio por chocar a un fantasma, o no se encontro con uno");
        }
    }

    public boolean estaEvolucionado() {
        return this.evolucionado;
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
        //metodo que devuelve un arreglo con los frames de la animacion de movimiento izquierda
        Array<Sprite> frames = new Array<Sprite>();
        frames.add(new Sprite(new TextureRegion(texturas, 194, 58, 14, 14)));
        frames.add(new Sprite(new TextureRegion(texturas, 179, 58, 14, 14)));
        frames.add(new Sprite(new TextureRegion(texturas, 132, 58, 14, 14)));
        frames.add(new Sprite(new TextureRegion(texturas, 179, 58, 14, 14)));
        return frames;
    }

    private Array<Sprite> getAnimDer(Texture texturas) {
        //metodo que devuelve un arreglo con los frames de la animacion de movimiento derecha
        Array<Sprite> frames = new Array<Sprite>();
        frames.add(new Sprite(new TextureRegion(texturas, 166, 58, 14, 14)));
        frames.add(new Sprite(new TextureRegion(texturas, 149, 58, 12, 14)));
        frames.add(new Sprite(new TextureRegion(texturas, 132, 58, 14, 14)));
        frames.add(new Sprite(new TextureRegion(texturas, 149, 58, 12, 14)));
        return frames;
    }

    private Array<Sprite> getAnimArriba(Texture texturas) {
        //metodo que devuelve un arreglo con los frames de la animacion de movimiento arriba
        Array<Sprite> frames = new Array<Sprite>();
        frames.add(new Sprite(new TextureRegion(texturas, 228, 61, 14, 9)));
        frames.add(new Sprite(new TextureRegion(texturas, 212, 59, 14, 12)));
        frames.add(new Sprite(new TextureRegion(texturas, 132, 58, 14, 14)));
        frames.add(new Sprite(new TextureRegion(texturas, 212, 59, 14, 12)));
        return frames;
    }

    private Array<Sprite> getAnimAbajo(Texture texturas) {
        //metodo que devuelve un arreglo con los frames de la animacion de movimiento abajo
        Array<Sprite> frames = new Array<Sprite>();
        frames.add(new Sprite(new TextureRegion(texturas, 260, 60, 14, 14)));
        frames.add(new Sprite(new TextureRegion(texturas, 244, 59, 14, 14)));
        frames.add(new Sprite(new TextureRegion(texturas, 132, 58, 14, 14)));
        frames.add(new Sprite(new TextureRegion(texturas, 244, 59, 14, 14)));
        return frames;
    }

    private Array<Sprite> getAnimMuerto(Texture texturas) {
        //metodo que devuelve un arreglo con los frames de la animacion de muerte
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
