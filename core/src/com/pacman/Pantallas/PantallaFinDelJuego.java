package com.pacman.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.pacman.JuegoPrincipal;

public class PantallaFinDelJuego extends PantallaBase {
    //pantalla que indica que finalizo el juego y da la posibilidad de reiniciar la partida

    private TextButton reiniciar, retroceso;
    private Label titulo, palabraPuntaje, puntaje;
    private Table tabla;

    public PantallaFinDelJuego(final JuegoPrincipal juego) {
        super(juego);
    }

    @Override
    public void show() {
        //metodo que se ejecuta cuando se muestra por primera vez la aplicacion
        //modificar lo del viweport hacer que el texto cuadre----------------------------------------
        super.show();
        establecerCamara();
        this.skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
        this.titulo = new Label(traductor.get("pantallaFinDelJuego.fin"), skin);
        this.titulo.setFontScale(1.5f);
        this.palabraPuntaje = new Label(traductor.get("pantallaFinDelJuego.puntaje"),skin);
        this.puntaje = new Label("000000",skin);   //////////obtener puntaje partida

        this.reiniciar = new TextButton(traductor.get("pantallaFinDelJuego.reiniciar"), skin);
        this.reiniciar.setSize(152, 50);
        this.reiniciar.setPosition((anchoEnPx / 2) - (this.reiniciar.getWidth() / 2), (altoEnPx / 2) - (this.reiniciar.getWidth() / 2));
        //Funcionalidad al Boton reiniciar
        this.reiniciar.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.setScreen(juego.getPantallaJuegoPrincipal());
            }
        });

        this.retroceso = new TextButton("<", this.skin);
        //Funcionalidad del Boton retroceso
        this.retroceso.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(juego.getPantallaMenu());
            }
        });

        tabla = new Table();
        tabla.align(Align.center);
        tabla.debug();
        tabla.setFillParent(true);
        tabla.add(titulo).align(Align.center).colspan(2).padBottom(30);
        tabla.row();
        tabla.add(palabraPuntaje);
        tabla.add(puntaje);
        tabla.row();
        tabla.add(reiniciar).width(200).padTop(30).align(Align.center).colspan(2);

        this.escenario.addActor(this.tabla);
        this.escenario.addActor(retroceso);
        this.retroceso.setPosition(10, altoEnPx - 10 - retroceso.getHeight());
        Gdx.input.setInputProcessor(this.escenario);
    }

    @Override
    public void hide() {
        //metodo que se ejecuta cuando se minimiza la aplicacion
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        //metodo que se ejecuta cuando se cierra la aplicacion y elimina los recursos innecesarios
        this.escenario.dispose();
        skin.dispose();
    }

    @Override
    public void render(float delta) {
        //metodo que se ejecuta en cada frame del juego y muestra el mensaje y el boton por pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        /*this.batch.begin();
        this.fuente.draw(this.batch, , (this.anchoEnPx / 2) - 45, (this.altoEnPx / 2) + 30);
        this.fuente.draw(this.batch, traductor.get("pantallaFinDelJuego.puntaje"), (this.anchoEnPx / 2) - 60, (this.altoEnPx / 2) + 10);
        this.fuente.draw(this.batch, "000000", (this.anchoEnPx / 2) + 5, (this.altoEnPx / 2) + 10);
        this.batch.end();*/
        this.escenario.act();
        this.escenario.draw();
    }
}
