package com.pacman.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.pacman.JuegoPrincipal;

public class PantallaMenu extends PantallaBase {

    private TextButton jugar, opciones, salir;
    private Texture titulo;
    private Table buttons;
    private Sprite sprite;
    private Label cabecera;

    public PantallaMenu(JuegoPrincipal juego) {
        super(juego);
    }

    @Override
    public void show() {
        establecerCamara();
        skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));//skin para los botones
        //se crea la cabecera
        //Label.LabelStyle labelStyle=new Label.LabelStyle(,Color.ORANGE);
        cabecera = new Label("PAC MAN", skin);
        cabecera.setSize(100, 100);
        cabecera.setPosition(altoEnPx / 2, 300);
        cabecera.setAlignment(Align.top);
        cabecera.setFontScale(2, 2);

        //carga de archivo de traduccion
        I18NBundle traductor = I18NBundle.createBundle(Gdx.files.internal("idiomas/idioma"));

        //se crean los botones
        jugar = new TextButton(traductor.get("pantallaMenu.jugar"), skin);
        opciones = new TextButton(traductor.get("pantallaMenu.opciones"), skin);
        salir = new TextButton(traductor.get("pantallaMenu.salir"), skin);
        buttons = new Table();
        buttons.setFillParent(true);     //redimensiona el tamaño de la tabla al del stage
        buttons.add(cabecera).height(50).width(200).padBottom(60);
        buttons.row();
        buttons.add(jugar).height(50).width(200).padBottom(30);
        buttons.row();
        buttons.add(opciones).height(50).width(200).padBottom(30);
        buttons.row();
        buttons.add(salir).height(50).width(200).padBottom(30);

        //Funcionalidad al Boton jugar
        jugar.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(juego.getPantallaIngreso());
            }
        });

        Gdx.input.setInputProcessor(escenario);
        escenario.addActor(buttons);
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
