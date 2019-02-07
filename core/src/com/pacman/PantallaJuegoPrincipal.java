package com.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pacman.Actores.Fantasma;
import com.pacman.Actores.PacMan;

public class PantallaJuegoPrincipal extends PantallaBase {

    private Stage escenario;
    //private PacMan pacman;
    private Fantasma fantasma1,fantasma2,fantasma3,fantasma4;
    private Texture sprites;

    public PantallaJuegoPrincipal(JuegoPrincipal juego) {
        super(juego);
        sprites = new Texture("personajes/actors.png");
    }

    @Override
    public void show() {
        escenario = new Stage();
        fantasma1=new Fantasma(sprites,0);
        escenario.addActor(fantasma1);
        fantasma1.setPosition(100,200);
        System.out.println(fantasma1.setEstado("debilitado"));
        //pacman = new PacMan(sprites);
        //escenario.addActor(pacman);
        //pacman.setPosition(100,200);
        //System.out.println(pacman.setEstado("abajo")+"------------------------------------");

    }

    @Override
    public void hide() {
        escenario.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        escenario.act();
        escenario.draw();
    }

    private void handleInput(float dt){
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){}
    }


    @Override
    public void dispose() {
        sprites.dispose();
    }
}
