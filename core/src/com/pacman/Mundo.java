package com.pacman;

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
    private Texture sprites; //ver si corresponde aca
    private PacMan pacman;
    private ArrayList<Fantasma> listaFantasma = new ArrayList<Fantasma>();
    private List<Pildora> listaPildora = new ArrayList<Pildora>();
    private final int cantFantasmas = 4;

    public Mundo(TiledMap mapa, Stage escenario) {
        sprites = new Texture("personajes/actors.png");
        this.mapa = mapa;
        MapLayer capaPacman = mapa.getLayers().get("Player");
        Rectangle rectanguloPacMan = ((RectangleMapObject) capaPacman.getObjects().get(0)).getRectangle();
        pacman = new PacMan(sprites, rectanguloPacMan, this);
        escenario.addActor(pacman);

        MapLayer capaFantasma = mapa.getLayers().get("Ghost");
        Rectangle rectanguloFantasma = ((RectangleMapObject) capaFantasma.getObjects().get(0)).getRectangle();
        for (int i = 0; i < cantFantasmas; i++) {
            this.listaFantasma.add(new Fantasma(sprites, rectanguloFantasma, i, this));
            escenario.addActor(this.listaFantasma.get(i));
        }
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
        //puerta
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
        //System.out.println("Empece a analizar colisiones de pildora");
        for (Pildora pildoraAux : this.listaPildora) {
            Rectangle limites = pildoraAux.getLimites();
            if (Intersector.overlaps(pacman.getLimites(), limites)) {
                // ocurrio una colision con una pildora
                // obtengo la pildora
                pildora = obtenerPildora(limites);
                if (pildora.esGrande()) {
                    this.pacman.setEstado("evolucionado");
                    for (Fantasma f : this.listaFantasma) {
                        f.setEstado("debilitado");
                    }
                }
                pildora.esComida();
                //System.out.println("Colision Pildora" + "||" + pacman.getLimites().x + "//" + pacman.getLimites().y + "||" + limites.getX() + "//" + (limites.getY()));
            }
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
}
