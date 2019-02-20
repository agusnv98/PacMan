package com.pacman.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.pacman.BaseDeDatos;
import com.pacman.BotonIngresoListener;
import com.pacman.JuegoPrincipal;

public class PantallaIngreso extends PantallaBase {

    private BaseDeDatos bd;

    private TextField usuarioTextField, contrasenaTextField;
    private TextButton ingreso, registro, retroceso;
    private Label titulo;
    private Table tabla;

    public PantallaIngreso(JuegoPrincipal juego, BaseDeDatos bd) {
        super(juego);
        this.bd = bd;
    }

    @Override
    public void show() {
        super.show();
        establecerCamara();
        this.skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
        this.titulo = new Label(traductor.get("pantallaIngreso.titulo"), skin);
        this.titulo.setFontScale(1f);
        this.usuarioTextField = new TextField(traductor.get("pantallaIngreso/Registro.usuario"), this.skin);
        this.contrasenaTextField = new TextField(traductor.get("pantallaIngreso/Registro.contrasena"), this.skin);
        this.contrasenaTextField.setPasswordMode(true);
        this.contrasenaTextField.setPasswordCharacter('•');
        this.ingreso = new TextButton(traductor.get("pantallaIngreso/Registro.botonIngreso"), this.skin);
        this.ingreso.addListener(new BotonIngresoListener(this.escenario, this.skin, this.bd, this.usuarioTextField, this.contrasenaTextField, this.juego));
        this.registro = new TextButton(traductor.get("pantallaIngreso.botonRegistro"), this.skin);

        //Funcionalidad del Boton registro
        this.registro.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(juego.getPantallaRegistro());
            }
        });
        this.retroceso = new TextButton("<", this.skin);

        //Funcionalidad del Boton retroceso
        this.retroceso.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(juego.getPantallaMenu());
            }
        });

        this.tabla = new Table();
        this.tabla.setFillParent(true);
        this.tabla.add(this.titulo).padBottom(30);
        this.tabla.row();
        this.tabla.add(this.usuarioTextField).height(50).width(200).padBottom(30);
        this.tabla.row();
        this.tabla.add(this.contrasenaTextField).height(50).width(200).padBottom(30);
        this.tabla.row();
        this.tabla.add(this.ingreso).height(50).width(200);
        this.tabla.row();
        this.tabla.add(this.registro).align(Align.right);
        Gdx.input.setInputProcessor(this.escenario);
        this.escenario.addActor(tabla);
        this.retroceso.setPosition(10, altoEnPx - 10 - retroceso.getHeight());
        this.escenario.addActor(retroceso);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.escenario.act();
        this.escenario.draw();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        this.escenario.dispose();
        skin.dispose();
    }
}
