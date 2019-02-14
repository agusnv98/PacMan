package com.pacman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class JuegoPrincipal extends Game {
    SpriteBatch batch;
    //el spirte se va a encargar de mostrar las cosas por pantalla
    Texture pacMan, mapa;
    //TextureRegion mapita;
    private int largoPacMan, altoPacMan, largoPantalla, altoPantalla;
    public final BaseDeDatos baseDeDatos;

    public JuegoPrincipal(BaseDeDatos baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }

    @Override
    public void create() {
        //batch = new SpriteBatch();
        //mapa =new Texture("mapa.jpg");
        //Procesador p = new Procesador();
        //Gdx.input.setInputProcessor(p);
        //pacMan = new Texture("pacMan.png");
        //largoPacMan = pacMan.getWidth();
        //altoPacMan = pacMan.getHeight();
        //largoPantalla = Gdx.graphics.getWidth();
        //altoPantalla = Gdx.graphics.getHeight();
        //System.out.println(largoPantalla);
        //System.out.println(altoPantalla);
        setScreen(new PantallaJuegoPrincipal(this));
    }


    @Override
    public void render() {
        //se va a encargar de renderizar las imagenes
        Gdx.gl.glClearColor(1f, 4, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //batch.begin();
        //batch.draw(pacMan, 0, 0, 60, 60);
        //batch.draw(pacMan, 580, 420, 60, 60);
        //batch.end();
    }

    @Override
    public void dispose() {
        //se encarga de limpiar lo cargado en la memoria
        batch.dispose();
        pacMan.dispose();
        mapa.dispose();
    }
}