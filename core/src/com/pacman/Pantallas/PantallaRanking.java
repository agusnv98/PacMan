package com.pacman.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.pacman.BaseDeDatos;
import com.pacman.JuegoPrincipal;

import java.util.ArrayList;

public class PantallaRanking extends PantallaBase {

    private BaseDeDatos bd;

    private Skin skin;

    private Table campos;
    private BitmapFont fuente;

    public PantallaRanking(JuegoPrincipal juego, BaseDeDatos bd) {
        super(juego);
        this.bd = bd;
    }

    @Override
    public void show() {
        establecerCamara();
        this.fuente = new BitmapFont();
        this.fuente.setColor(0, 204, 204, 1);
        this.skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
        this.campos = new Table(this.skin);
        this.campos.setFillParent(true);
        ArrayList listaJugadores = this.bd.obtenerDatos();
        for (int i = 0; i < listaJugadores.size(); i++) {
            if (i % 2 == 0) {
                this.campos.add(listaJugadores.get(i).toString()).height(50).width(100);
            } else {
                this.campos.add(listaJugadores.get(i).toString()).height(50).width(50);
                this.campos.row();
            }
        }
        this.escenario.addActor(this.campos);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.batch.begin();
        this.fuente.draw(this.batch, "RANKING", (304/2)-(this.fuente.getRegion().getRegionWidth()/6), 336);
        this.batch.end();
        this.escenario.act();
        this.escenario.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void hide() {
        this.escenario.dispose();
    }

    @Override
    public void dispose() {
        this.escenario.dispose();
    }
}
