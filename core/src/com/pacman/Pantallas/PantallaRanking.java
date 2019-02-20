package com.pacman.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.pacman.BaseDeDatos;
import com.pacman.JuegoPrincipal;

import java.util.ArrayList;

public class PantallaRanking extends PantallaBase {

    private BaseDeDatos bd;
    private Skin skin;
    private Table tabla;
    private Label cabecera;
    private ScrollPane panel;
    private TextButton retroceso;

    public PantallaRanking(JuegoPrincipal juego, BaseDeDatos bd) {
        super(juego);
        this.bd = bd;
    }

    @Override
    public void show() {
        super.show();
        establecerCamara();
        this.skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
        this.tabla = new Table(this.skin);
        this.tabla.align(Align.center);
        this.tabla.setFillParent(true);
        this.cabecera = new Label(traductor.get("pantallaRankings.titulo"), skin);
        this.tabla.add(cabecera);
        this.tabla.row();
        this.panel = new ScrollPane(tabla, skin);
        ArrayList listaJugadores = this.bd.obtenerDatos();
        for (int i = 0; i < listaJugadores.size(); i++) {
            if (i % 2 == 0) {
                this.tabla.add(listaJugadores.get(i).toString()).height(50).width(100);
            } else {
                this.tabla.add(listaJugadores.get(i).toString()).height(50).width(50);
                this.tabla.row();
            }
        }
        this.retroceso = new TextButton("<", this.skin);
        //Funcionalidad del Boton retroceso
        this.retroceso.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(juego.getPantallaMenu());
            }
        });

        Table container = new Table(this.skin);
        container.setFillParent(true);
        container.add(this.panel).expand().fill();
        container.row();
        this.escenario.addActor(container);
        this.retroceso.setPosition(10, altoEnPx - 10 - retroceso.getHeight());
        this.escenario.addActor(retroceso);
        Gdx.input.setInputProcessor(this.escenario);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.escenario.act();
        this.escenario.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        this.escenario.dispose();
    }
}
