package com.pacman.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.pacman.JuegoPrincipal;

public class PantallaMenu extends PantallaBase {

    private TextButton jugar, rankings, salir;
    private Table buttons;
    private Label cabecera;

    public PantallaMenu(JuegoPrincipal juego) {
        super(juego);
    }

    @Override
    public void show() {
        super.show();
        establecerCamara();
        skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));//skin para los botones
        //se crea la cabecera
        //Label.LabelStyle labelStyle=new Label.LabelStyle(,Color.ORANGE);
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
        buttons = new Table();
        buttons.setFillParent(true);                       //redimensiona el tamaño de la tabla al del stage
        buttons.add(cabecera).height(50).width(200).padBottom(60);
        buttons.row();
        buttons.add(jugar).height(50).width(200).padBottom(30);
        buttons.row();
        buttons.add(rankings).height(50).width(200).padBottom(30);
        buttons.row();
        buttons.add(salir).height(50).width(200).padBottom(30);
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