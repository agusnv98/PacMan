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
import com.badlogic.gdx.physics.box2d.joints.PulleyJoint;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pacman.Actores.*;

import java.util.ArrayList;
import java.util.List;

public class Mundo {

    private TiledMap mapa;
    private Texture sprites;
    private PacMan pacman;
    private ArrayList<Fantasma> listaFantasma = new ArrayList<Fantasma>();
    private List<Pildora> listaPildora = new ArrayList<Pildora>();
    private final int cantFantasmas = 4;
    private Sound sonidoPildora, sonidoPildoraGrande;
    private AssetManager manager;

    public Mundo(TiledMap mapa, Stage escenario) {
        sprites = new Texture("personajes/actors.png");
        this.mapa = mapa;
        //PacMan
        MapLayer capaPacman = mapa.getLayers().get("Player");
        Rectangle rectanguloPacMan = ((RectangleMapObject) capaPacman.getObjects().get(0)).getRectangle();
        pacman = new PacMan(sprites, rectanguloPacMan, this);
        escenario.addActor(pacman);
        //Fantasmas
        MapLayer capaFantasma = mapa.getLayers().get("Ghost");
        Rectangle rectanguloFantasma = ((RectangleMapObject) capaFantasma.getObjects().get(0)).getRectangle();
        for (int i = 0; i < 1; i++) {
            this.listaFantasma.add(new Fantasma(sprites, rectanguloFantasma, i, this));
            escenario.addActor(this.listaFantasma.get(i));
        }
        //Pildoras
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
        establecerSonido();
    }

    private void establecerSonido() {
        manager = new AssetManager();
        manager.load("sounds/big_pill.ogg", Sound.class);
        manager.load("sounds/clear.ogg", Sound.class);
        manager.load("sounds/ghost_die.ogg", Sound.class);
        manager.load("sounds/pacman_die.ogg", Sound.class);
        manager.load("sounds/pill.ogg", Sound.class);
        manager.load("sounds/pac-mans-park-block-plaza-super-smash-bros-3ds.ogg", Music.class);
        manager.load("sounds/u-got-that-full-version-mmv.ogg", Music.class);
        manager.finishLoading();
    }

    public AssetManager getManager() {
        return this.manager;
    }

    public PacMan getPacman() {
        return pacman;
    }

    public Rectangle verificarColisionPared(Personaje personaje) {
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
        return pared;
    }

    public void verificarColisionPildora() {
        Pildora pildora;
        boolean seguir = true;
        int i = 0, length = this.listaPildora.size();
        //System.out.println("Empece a analizar colisiones de pildora");
        Pildora pildoraAux;
        while (seguir && i < length) {
            pildoraAux = this.listaPildora.get(i);
            Rectangle limites = pildoraAux.getLimites();
            if (Intersector.overlaps(pacman.getLimites(), limites)) {
                // ocurrio una colision con una pildora
                // obtengo la pildora
                pildora = obtenerPildora(limites);
                if (pildora.esValida()) {
                    if (pildora.esGrande()) {
                        this.pacman.setEstado("evolucionado");
                        for (Fantasma f : this.listaFantasma) {
                            f.setEstado("debilitado");
                        }
                        sonidoPildoraGrande = manager.get("sounds/big_pill.ogg");
                        sonidoPildoraGrande.play();
                    } else {
                        sonidoPildora = manager.get("sounds/pill.ogg");
                        sonidoPildora.play();
                    }
                }
                pildora.esComida();
                seguir = false;
                //System.out.println("Colision Pildora" + "||" + pacman.getLimites().x + "//" + pacman.getLimites().y + "||" + limites.getX() + "//" + (limites.getY()));
            }
            i++;
        }
    }

    private Pildora obtenerPildora(Rectangle rectangulo) {
        //el metodo se llama cuando se asegura que hubo una colision
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
        MapLayer capaColisiones = mapa.getLayers().get("CambioDireccion");
        MapObject posicionCambio = null;
        Vector2 direccionFantasma = fantasma.getDireccion();
        Rectangle limitesFantasma = fantasma.getLimites();
        int i = 0, limite = capaColisiones.getObjects().getCount();
        System.out.println(fantasma.getEstado());
        while (posicionCambio == null && i < limite) {
            MapObject mapObject = capaColisiones.getObjects().get(i);
            Rectangle rectangulo = ((RectangleMapObject) mapObject).getRectangle();
            float limiteIzquierdo = rectangulo.getX() + (rectangulo.getWidth() / 8);
            float limiteDerecho = rectangulo.getX() + ((rectangulo.getWidth() / 8) * 7);
            float limiteSuperior = rectangulo.getY() + ((rectangulo.getHeight() / 8) * 7);
            float limiteInferior = rectangulo.getY() + (rectangulo.getHeight() / 8);

            boolean fantasmaEnLimiteDer = (limitesFantasma.getX() + limitesFantasma.getWidth()) >= limiteDerecho &&
                    (limitesFantasma.getX() + limitesFantasma.getWidth()) <= (rectangulo.getX() + rectangulo.getWidth());
            boolean fantasmaEnLimiteIzq = limitesFantasma.getX() <= limiteIzquierdo &&
                    limitesFantasma.getX() >= rectangulo.getX();
            boolean fantasmaEnLimiteSup = (limitesFantasma.getY() + limitesFantasma.getHeight()) <= (rectangulo.getY() + rectangulo.getHeight()) &&
                    (limitesFantasma.getY() + limitesFantasma.getHeight()) >= limiteSuperior;
            boolean fantasmaEnLimiteInf = limitesFantasma.getY() <= limiteInferior &&
                    limitesFantasma.getY() >= rectangulo.getY();
            /*System.out.println("limiteSup " + limiteSuperior);
            System.out.println(mapObject.getProperties().get("id", Integer.class));
            System.out.println("Final sup Collider" + (rectangulo.getY() + rectangulo.getHeight()));
            System.out.println("borde sup Fantasma" + (limitesFantasma.getY() + limitesFantasma.getHeight()));
            System.out.println("BordeIzqFantasma" + limitesFantasma.getX());
            System.out.println("BordeIzqRect" + rectangulo.getX() + "BordeDer" + (rectangulo.getX() + rectangulo.getWidth()));
            System.out.println(fantasmaEnLimiteSup + "////////////////////////////////////////////////////////////");*/
            if (direccionFantasma.x > 0 && fantasmaEnLimiteDer &&
                    (limitesFantasma.getY() >= rectangulo.getY() && limitesFantasma.getY() <= (rectangulo.getY() + rectangulo.getHeight()))) {
                posicionCambio = mapObject;
            } else if (direccionFantasma.x < 0 && fantasmaEnLimiteIzq &&
                    (limitesFantasma.getY() >= rectangulo.getY() && limitesFantasma.getY() <= (rectangulo.getY() + rectangulo.getHeight()))) {
                posicionCambio = mapObject;
            } else if (direccionFantasma.y > 0 && fantasmaEnLimiteSup &&
                    (limitesFantasma.getX() >= rectangulo.getX() && limitesFantasma.getX() <= (rectangulo.getX() + rectangulo.getWidth()))) {
                posicionCambio = mapObject;
            } else if (direccionFantasma.y < 0 && fantasmaEnLimiteInf &&
                    (limitesFantasma.getX() >= rectangulo.getX() && limitesFantasma.getX() <= (rectangulo.getX() + rectangulo.getWidth()))) {
                posicionCambio = mapObject;
            }
            i++;
        }
        return posicionCambio;
    }

    public boolean verificarColsionFantasma() {
        int cantFantasmas = this.listaFantasma.size(), i = 0;
        boolean exito = false;
        Fantasma fantasma;
        while (!exito && i < cantFantasmas) {
            fantasma = this.listaFantasma.get(i);
            if (Intersector.overlaps(this.pacman.getLimites(), fantasma.getLimites())) {
                if (this.pacman.estaEvolucionado()) {
                    fantasma.setEstado("muerto");
                } else {
                    exito = true;
                }
            }
            i++;
        }
        return exito;
    }
}
