package com.pacman;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pacman.Actores.*;

import java.util.ArrayList;
import java.util.List;

public class Mundo {
    //Clase que representa al mundo donde se desarrolla el juego
    //En esta se crean los personajes y las pildoras, ademas es la encargada
    // de indicar con que objeto se colisiono en el mapa

    private TiledMap mapa;
    private float anchoMapa;
    private Texture sprites;
    private PacMan pacman;
    private ArrayList<Fantasma> listaFantasma = new ArrayList<Fantasma>();
    private List<Pildora> listaPildora = new ArrayList<Pildora>();
    private MapLayer capaCambiosDir;
    private final int cantFantasmas = 4;
    //variable utilizada para indicar que el juego finalizo (-1 el juego no finalizo, 0 el juegador perdio, 1 el juegador gano)
    private int finDelJuego = -1;
    private int puntaje;
    private Sound sonidoPildora, sonidoPildoraGrande;
    private AssetManager manager;

    public Mundo(TiledMap mapa, Stage escenario, AssetManager manager) {
        this.manager = manager;
        this.sonidoPildoraGrande = this.manager.get("sounds/big_pill.ogg");
        this.sonidoPildora = this.manager.get("sounds/pill.ogg");

        this.sprites = new Texture("personajes/actors.png");
        this.anchoMapa = escenario.getCamera().viewportWidth;
        this.mapa = mapa;

        // se crea el PacMan
        MapLayer capaPacman = mapa.getLayers().get("Player");
        Rectangle rectanguloPacMan = ((RectangleMapObject) capaPacman.getObjects().get(0)).getRectangle();
        this.pacman = new PacMan(this.sprites, rectanguloPacMan, this);
        escenario.addActor(this.pacman);

        //se crean los Fantasmas
        MapLayer capaFantasma = mapa.getLayers().get("Ghost");
        Rectangle rectanguloFantasma = ((RectangleMapObject) capaFantasma.getObjects().get(0)).getRectangle();
        for (int i = 0; i < this.cantFantasmas; i++) {
            this.listaFantasma.add(new Fantasma(this.sprites, rectanguloFantasma, i, this));
            escenario.addActor(this.listaFantasma.get(i));
        }

        //se crean las Pildoras
        MapLayer capaPildoras = mapa.getLayers().get("Pill");
        for (MapObject mapObject : capaPildoras.getObjects()) {
            Rectangle rectangulo = ((RectangleMapObject) mapObject).getRectangle();
            Pildora pildoraAux;
            if (mapObject.getProperties().containsKey("big")) {
                pildoraAux = new Pildora(sprites, rectangulo, true);
                this.listaPildora.add(pildoraAux);
            } else {
                pildoraAux = new Pildora(sprites, rectangulo, false);
                this.listaPildora.add(pildoraAux);
            }
            escenario.addActor(pildoraAux);
        }

        //se obtiene la capa de cambio de direccion
        this.capaCambiosDir = mapa.getLayers().get("CambioDireccion");
    }

    public PacMan getPacman() {
        return pacman;
    }

    public float getAncho() {
        //metodo que retorna el ancho del mapa
        return this.anchoMapa;
    }

    public Rectangle verificarColisionPared(Personaje personaje) {
        //metodo que verifica si un personaje colisiono con una pared
        //retorna los limites de la pared si colisiono el personaje, null en caso contrario
        MapLayer capaPared = mapa.getLayers().get("Wall");
        MapObjects objetos = capaPared.getObjects();
        Rectangle pared = null;
        //System.out.println("Empece a analizar colisiones con paredes");
        for (RectangleMapObject rectangleObject : objetos.getByType(RectangleMapObject.class)) {
            Rectangle rectangulo = rectangleObject.getRectangle();
            if (Intersector.overlaps(personaje.getLimites(), rectangulo)) {
                // ocurrio una colision con una pared
                pared = rectangulo;
            }
        }
        //System.out.println("Termine de analizar colisiones con paredes");
        return pared;
    }

    public void verificarColisionPildora() {
        //metodo que verifica si el pacman colisiono con una pildora del mundo
        //si hubo colision determina si es grande o no, y la remueve del mundo; si no hubo colision solo analiza a las pildoras existentes
        //si la pildora colisionada es grande se evoluciona al pacman y debilitan los fantasmas
        Pildora pildora;
        boolean seguir = true;
        int i = 0, length = this.listaPildora.size();
        //System.out.println("Empece a analizar colisiones de pildora");
        Pildora pildoraAux;
        while (seguir && i < length) {
            pildoraAux = this.listaPildora.get(i);
            Rectangle limites = pildoraAux.getLimites();
            if (Intersector.overlaps(this.pacman.getLimites(), limites)) {
                // ocurrio una colision con una pildora
                // obtengo la pildora
                pildora = obtenerPildora(limites);
                if (pildora.esValida()) {
                    if (pildora.esGrande()) {
                        //se evoluciona al PacMan y debilitan los fantasmas
                        this.pacman.setEstado("evolucionado");
                        for (Fantasma f : this.listaFantasma) {
                            f.setEstado("debilitado");
                        }
                        this.sonidoPildoraGrande.play();
                        modificarPuntaje(500);
                    } else {
                        this.sonidoPildora.play();
                        modificarPuntaje(100);
                    }
                }
                pildora.esComida();
                seguir = false;
                this.listaPildora.remove(pildora);
                //System.out.println("Colision Pildora" + "||" + pacman.getLimites().x + "//" + pacman.getLimites().y + "||" + limites.getX() + "//" + (limites.getY()));
            }
            i++;
        }
        if (length <= 0) { //si no hay pildoras para analizar, el jugador gano
            System.out.println("Gano el juego");
            this.setFinDelJuego(1);
        }

    }

    private Pildora obtenerPildora(Rectangle rectangulo) {
        // metodo que se llama cuando hubo una colision con una pildora
        // obtiene el objeto pildora a partir de los limites con los que se colisiono en el mundo y luego lo retorna
        Pildora pildora = null;
        for (Pildora pildoraAux : this.listaPildora) {
            if (pildoraAux.getLimites().equals(rectangulo)) {
                pildora = pildoraAux;
            }
        }
        return pildora;
    }

    public MapObject verificarCambioDireccion(Fantasma fantasma) {
        //metodo que verifica si un fantasma se encuentra en posicion para cambiar de direccion
        //las posiciones son determinadas en el mapa en la capa ColisionFantasma
        MapObject posicionCambio = null;
        boolean seguir = true;
        Vector2 direccionFantasma = fantasma.getDireccion();
        Rectangle limitesFantasma = fantasma.getLimites();
        int i = 0, limite = this.capaCambiosDir.getObjects().getCount();
        while (posicionCambio == null && i < limite && seguir) {
            MapObject mapObject = this.capaCambiosDir.getObjects().get(i);
            Rectangle rectangulo = ((RectangleMapObject) mapObject).getRectangle();
            //se analiza si se debe hacer el cambio de direccion, solo si hubo colision
            if (Intersector.overlaps(limitesFantasma, rectangulo)) {
                //se establecen como limites las octavas partes de la poscion, segun el lado a analizar
                //si el fantasma posee mas 7/8 partes del mismo sobre la posicion, se analiza el cambio de direccion

                //para que se valide el cambio de direccion, el fantasma debe estar en el limite de la posicion y
                // debe seguir la direccion correspondiente al lado a analizar
                if (direccionFantasma.x > 0) {
                    float limiteDerecho = rectangulo.getX() + ((rectangulo.getWidth() / 8) * 7);
                    boolean fantasmaEnLimiteDer = (limitesFantasma.getX() + limitesFantasma.getWidth()) >= limiteDerecho &&
                            (limitesFantasma.getX() + limitesFantasma.getWidth()) <= (rectangulo.getX() + rectangulo.getWidth());
                    if (fantasmaEnLimiteDer) {
                        posicionCambio = mapObject;
                    }
                } else if (direccionFantasma.x < 0) {
                    float limiteIzquierdo = rectangulo.getX() + (rectangulo.getWidth() / 8);
                    boolean fantasmaEnLimiteIzq = limitesFantasma.getX() <= limiteIzquierdo &&
                            limitesFantasma.getX() >= rectangulo.getX();
                    if (fantasmaEnLimiteIzq) {
                        posicionCambio = mapObject;
                    }
                } else if (direccionFantasma.y > 0) {
                    float limiteSuperior = rectangulo.getY() + ((rectangulo.getHeight() / 8) * 7);
                    boolean fantasmaEnLimiteSup = (limitesFantasma.getY() + limitesFantasma.getHeight()) <= (rectangulo.getY() + rectangulo.getHeight()) &&
                            (limitesFantasma.getY() + limitesFantasma.getHeight()) >= limiteSuperior;
                    if (fantasmaEnLimiteSup) {
                        posicionCambio = mapObject;
                    }
                } else if (direccionFantasma.y < 0) {
                    float limiteInferior = rectangulo.getY() + (rectangulo.getHeight() / 8);
                    boolean fantasmaEnLimiteInf = limitesFantasma.getY() <= limiteInferior &&
                            limitesFantasma.getY() >= rectangulo.getY();
                    if (fantasmaEnLimiteInf) {
                        posicionCambio = mapObject;
                    }
                }
                seguir = false;
                /*System.out.println("limiteSup " + limiteSuperior);
                System.out.println(mapObject.getProperties().get("id", Integer.class));
                System.out.println("Final sup Collider" + (rectangulo.getY() + rectangulo.getHeight()));
                System.out.println("borde sup Fantasma" + (limitesFantasma.getY() + limitesFantasma.getHeight()));
                System.out.println("BordeIzqFantasma" + limitesFantasma.getX());
                System.out.println("BordeIzqRect" + rectangulo.getX() + "BordeDer" + (rectangulo.getX() + rectangulo.getWidth()));
                System.out.println(fantasmaEnLimiteSup + "////////////////////////////////////////////////////////////");*/
            }
            i++;
        }
        return posicionCambio;
    }

    public boolean verificarColsionFantasma() {
        //metodo que verifica que si el PacMan colisiono con un fantasma
        //retrona true si ocurrio la colision, false en caso contrario
        int cantFantasmas = this.listaFantasma.size(), i = 0;
        boolean exito = false;
        Fantasma fantasma;
        while (!exito && i < cantFantasmas) {
            fantasma = this.listaFantasma.get(i);
            if (Intersector.overlaps(this.pacman.getLimites(), fantasma.getLimites())) {
                //ocurrio la colision con el fantasma

                //se verifica si el fantasma esta muerto, porque el PacMan puede colisionar con el fantasma
                // cuando este esta reproduciendo su animacion de muerte
                if (!fantasma.estaMuerto()) {
                    if (this.pacman.estaEvolucionado() && fantasma.estaDebilitado()) {
                        fantasma.setEstado("muerto");
                        modificarPuntaje(1000);
                    } else {
                        exito = true;
                    }
                }
            }
            i++;
        }
        return exito;
    }

    public synchronized void setFinDelJuego(int condicion) {
        //metodo utilizado para indicarle al mundo que el juego finalizo
        //si condicion = 0, el jugador perdio
        //si condicion = 1, el jugador gano
        //solo se va a actualizar la condicion del juego si es la primera vez que se establece
        if (this.finDelJuego == -1 && condicion >= 0 && condicion <= 1) {
            this.finDelJuego = condicion;
        }
    }

    public int getEstadoJuego() {
        //metodo utilizado para saber si el juego finalizo
        return this.finDelJuego;
    }

    public int getPuntaje() {
        return this.puntaje;
    }

    private void modificarPuntaje(int puntos) {
        this.puntaje = this.getPuntaje() + puntos;
    }

    public void terminoEvolucion() {
        for (Fantasma fantasma : this.listaFantasma) {
            fantasma.setEstado("finDebilitado");
        }
    }
}
