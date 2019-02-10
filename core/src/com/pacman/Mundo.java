package com.pacman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pacman.Actores.*;

public class Mundo {

    private TiledMap mapa;

    private Texture sprites; //ver si corresponde aca
    private PacMan pacman;
    private Fantasma[] fantasma = new Fantasma[4];

    public Mundo(TiledMap mapa, Stage escenario) {
        sprites = new Texture("personajes/actors.png");
        this.mapa = mapa;
        MapLayer capaPacman = mapa.getLayers().get("Player");
        Rectangle rectanguloPacMan = ((RectangleMapObject) capaPacman.getObjects().get(0)).getRectangle();
        pacman = new PacMan(sprites, rectanguloPacMan);
        escenario.addActor(pacman);

        MapLayer capaFantasma = mapa.getLayers().get("Ghost");
        Rectangle rectanguloFantasma = ((RectangleMapObject) capaFantasma.getObjects().get(0)).getRectangle();
        for (int i = 0; i < 4; i++) {
            this.fantasma[i] = new Fantasma(sprites, rectanguloFantasma, i);
            escenario.addActor(fantasma[i]);
        }
    }

    public PacMan getPacman() {
        return pacman;
    }
}
