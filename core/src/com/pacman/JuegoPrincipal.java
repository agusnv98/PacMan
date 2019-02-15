package com.pacman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pacman.Botones.MiTouch;

import java.awt.Color;

import javax.swing.plaf.basic.BasicArrowButton;


public class JuegoPrincipal extends ApplicationAdapter {

    public PantallaJuegoPrincipal jugando;
    private Stage menu;
    private Skin skin;
    private TextButton jugar, opciones, salir;
    private Texture titulo;
    private SpriteBatch batch;
    private Table buttons;
    private Sprite sprite;
    private Label cabecera;



    @Override
    public void create() {//se ejecuta cuando se llama al juego

        Gdx.graphics.setWindowedMode(428, 518);
        batch = new SpriteBatch();
        //titulo= new Texture("title.png");
        menu = new Stage(new FitViewport(428, 518));
        skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));//skin para los botones

        //Creo la cabecera
        //Label.LabelStyle labelStyle=new Label.LabelStyle(,Color.ORANGE);
        cabecera = new Label("PAC MAN", skin);
        cabecera.setSize(100, 100);
        cabecera.setPosition(340, 300);
        cabecera.setAlignment(Align.top);
        cabecera.setFontScale(2, 2);


        //Creamos los botones
        jugar = new TextButton(" Jugar ", skin);
        opciones = new TextButton(" Opciones ", skin);
        salir = new TextButton(" Salir ", skin);
        buttons = new Table();
        buttons.setFillParent(true);//redimensiona el tamaño de la tabla al del stage
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
                jugar.setText("gg");
            }
        });
        Gdx.input.setInputProcessor(menu);
        //menu.addActor(derecha);
        //menu.addActor(cabecera);
        menu.addActor(buttons);
        //TouchPad
        MiTouch touch =new MiTouch(60,skin);
        touch.setBounds(100,0,140,134);
        //
        //BasicArrowButton a=new BasicArrowButton();

        menu.addActor(touch);


    }


    public void show() {
        Gdx.input.setInputProcessor(menu);
    }




    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        menu.act();
        menu.draw();
    }

    @Override
    public void dispose() {
        menu.dispose();
        skin.dispose();


    }

    @Override
    public void resize(int width, int height) {


    }
}