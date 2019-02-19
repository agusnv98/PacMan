package com.pacman.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.pacman.JuegoPrincipal;

public class PantallaFinDelJuego extends PantallaBase {
    //pantalla que indica que finalizo el juego y da la posibilidad de reiniciar la partida

    private BitmapFont fuente;
    private TextButton reiniciar;
    private I18NBundle traductor;

    public PantallaFinDelJuego(final JuegoPrincipal juego) {
        super(juego);
    }

    @Override
    public void show() {
        //metodo que se ejecuta cuando se muestra por primera vez la aplicacion
        //modificar lo del viweport hacer que el texto cuadre----------------------------------------
        establecerCamara();
        this.skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
        this.fuente = new BitmapFont();
        this.fuente.setColor(Color.CYAN);

        //carga de archivo de traduccion
        traductor = I18NBundle.createBundle(Gdx.files.internal("idiomas/idioma"));

        this.reiniciar = new TextButton(traductor.get("pantallaFinDelJuego.reiniciar"), skin);
        this.reiniciar.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.setScreen(juego.getPantallaJuegoPrincipal());
            }
        });
        this.reiniciar.setSize(152, 50);
        this.reiniciar.setPosition((anchoEnPx / 2) - (this.reiniciar.getWidth() / 2), (altoEnPx / 2) - (this.reiniciar.getWidth() / 2));

        this.escenario.addActor(this.reiniciar);
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
        this.batch.begin();
        this.fuente.draw(this.batch, traductor.get("pantallaFinDelJuego.fin"), (this.anchoEnPx / 2) - 45, (this.altoEnPx / 2) + 30);
        this.fuente.draw(this.batch, traductor.get("pantallaFinDelJuego.puntaje"), (this.anchoEnPx / 2) - 60, (this.altoEnPx / 2) + 10);
        this.fuente.draw(this.batch, "000000", (this.anchoEnPx / 2) + 5, (this.altoEnPx / 2) + 10);
        this.batch.end();
        this.escenario.act();
        this.escenario.draw();
    }
}
