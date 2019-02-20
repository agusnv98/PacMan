package com.pacman.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.pacman.JuegoPrincipal;

public class PantallaMenu extends PantallaBase {

    private TextButton jugar, rankings, salir;
    private Table tabla;
    private Label cabecera;

    public PantallaMenu(JuegoPrincipal juego) {
        super(juego);
    }

    @Override
    public void show() {
        super.show();
        //se desabilita el boton de retroceso
        retroceso.remove();

        //se establece el titulo el juego
        cabecera = new Label("PAC MAN", skin);
        cabecera.setSize(100, 100);
        cabecera.setPosition(altoEnPx / 2, 300);
        cabecera.setAlignment(Align.center);
        cabecera.setFontScale(2, 2);

        //se crean los botones
        jugar = new TextButton(traductor.get("pantallaMenu.jugar"), skin);
        //Funcionalidad del Boton jugar
        jugar.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(juego.getPantallaIngreso());
            }
        });

        rankings = new TextButton(traductor.get("pantallaMenu.rankings"), skin);
        //Funcionalidad del Boton rankings
        rankings.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(juego.getPantallaRanking());
            }
        });

        salir = new TextButton(traductor.get("pantallaMenu.salir"), skin);
        salir.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        tabla = new Table();
        tabla.setFillParent(true);                       //redimensiona el tamaño de la tabla al del stage
        tabla.add(cabecera).height(50).width(200).padBottom(60);
        tabla.row();
        tabla.add(jugar).height(50).width(200).padBottom(30);
        tabla.row();
        tabla.add(rankings).height(50).width(200).padBottom(30);
        tabla.row();
        tabla.add(salir).height(50).width(200).padBottom(30);

        Gdx.input.setInputProcessor(escenario);
        escenario.addActor(tabla);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        escenario.act();
        escenario.draw();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        escenario.dispose();
        skin.dispose();
    }
}