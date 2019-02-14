package com.pacman.Actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pacman.Aleatorio;
import com.pacman.Mundo;

import java.util.Iterator;
import java.util.Random;

public class Fantasma extends Personaje {

    private int fantasmaId;
    private static final float duracionFrame = 0.15f;
    private Animation animVolviendo, animDebilitado;
    private String[] salida;
    private boolean saliendo;//////////////////////ver si usar, para contar los 16px
    private int choquesIzq = 0, choquesDer = 0, choquesArr = 0, chocesIzq = 0;

    public Fantasma(Texture texturas, Rectangle respawn, int id, Mundo mundo) {
        super(respawn, mundo);
        this.estados.add("debilitado");
        this.estados.add("volviendo");
        int ejeY = 20, aumento = 16; //bases para obtener las animaciones
        //por default el id es 0
        this.salida = new String[2];
        switch (id) {
            case 1:
                this.fantasmaId = 1;//la posicion del fantasma con id 1 no se modifica
                //el fantasma con id 1, no debe cambiar el camino, porque ya esta en la entrada de la guarida
                break;
            case 2:
                this.fantasmaId = 2;
                this.limites.setPosition(this.limites.getX() + 16, this.limites.getY());
                this.salida[0] = "izquierda";
                this.salida[1] = "arriba";
                break;
            case 3:
                this.fantasmaId = 3;
                this.limites.setPosition(this.limites.getX() - 16, this.limites.getY());
                this.salida[0] = "derecha";
                this.salida[1] = "arriba";
                break;
            default:                //por defecto se crea con id 0;
                this.fantasmaId = 0;
                this.limites.setPosition(this.limites.getX() - 16, this.limites.getY());
                this.salida[0] = "derecha";
                this.salida[1] = "arriba";
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
        //System.out.println(pos);
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
        //cambiar por el tamaño del mapa
        if (getX() > Gdx.graphics.getWidth() + this.frameActual.getRegionWidth()) {
            setPosition(0 - this.frameActual.getRegionWidth(), getY());
        } else if (getX() + this.frameActual.getRegionWidth() < 0) {
            setPosition(Gdx.graphics.getWidth(), getY());
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
        setXY(getX() + this.direccion.x, getY() + this.direccion.y);
        pared = this.mundo.verificarColisionPared(this);
        if (pared != null) {
            reacomodar(pared);
        } else {
            //si no hay pared, verifica si esta en un cambio de direccion y elige una nueva
            MapObject cambioDireccion = this.mundo.verificarCambioDireccion(this);
            if (cambioDireccion != null) {
                elegirDireccion(cambioDireccion);
            }
        }
    }

    private void elegirDireccion(MapObject cambioDireccion) {
        //metodo que recibe una posicion del mapa, obtiene las direcciones posibles y elige aleatoriamente
        //la siguiente direccion, dentro de las que esten disponibles en la posicion actual
        String tipoCambio = cambioDireccion.getProperties().get("type", String.class);
        String direccionesPosibles;
        /*Iterator it =cambioDireccion.getProperties().getKeys();
        while (it.hasNext()) {

            System.out.println(it.next());

        }*/
        System.out.println(cambioDireccion.getProperties().get("id", Integer.class));
        System.out.println("Rectangulo" + cambioDireccion.getProperties().get("x", Float.class) + "//" + cambioDireccion.getProperties().get("y", Float.class));
        System.out.println("Fantasma x" + this.getLimites().getX() + "//" + this.getLimites().getY());
        if (tipoCambio.equals("dosDir")) {
            direccionesPosibles = cambioDireccion.getProperties().get("direcciones", String.class);
            String[] direccion = new String[2];
            direccion[0] = direccionesPosibles.substring(0, direccionesPosibles.indexOf("/"));
            direccion[1] = direccionesPosibles.substring(direccionesPosibles.indexOf("/") + 1);
            this.setEstado(direccion[Aleatorio.intAleatorio(0, 1)]);
        } else if (tipoCambio.equals("tresDir")) {
            direccionesPosibles = cambioDireccion.getProperties().get("direcciones", String.class);
            String[] direccion = new String[3];
            direccion[0] = direccionesPosibles.substring(0, direccionesPosibles.indexOf("/"));
            int primerIndice = direccionesPosibles.indexOf("/") + 1;
            int segundoIndice = direccionesPosibles.indexOf("/", primerIndice);
            direccion[1] = direccionesPosibles.substring(primerIndice, segundoIndice);
            direccion[2] = direccionesPosibles.substring(segundoIndice);
            this.setEstado(direccion[Aleatorio.intAleatorio(0, 2)]);
        } else if (tipoCambio.equals("cuatroDir")) {
            //no se leen las direcciones, porque las posiciones de cuatro direcciones, solo tienen una posibilidad
            String[] direccion = {"arriba", "abajo", "izquierda", "derecha"};
            this.setEstado(direccion[Aleatorio.intAleatorio(0, 3)]);
        }
    }

    public int getId() {
        return this.fantasmaId;
    }
}
