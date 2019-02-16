package com.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class PantallaRegistro extends PantallaBase {

    private BaseDeDatos bd;
    private Stage escenario;
    private Skin skin;

    private TextField usuarioTextField;
    private TextField contrasenaTextField;
    private TextButton boton;
    private Table campos;

    public PantallaRegistro(JuegoPrincipal juego, BaseDeDatos bd) {
        super(juego);
        this.bd = bd;
    }

    @Override
    public void show() {
        this.escenario = new Stage();
        this.skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
        this.usuarioTextField = new TextField("Usuario", this.skin);
        this.contrasenaTextField = new TextField("Contrasena", this.skin);
        this.contrasenaTextField.setPasswordMode(true);
        this.contrasenaTextField.setPasswordCharacter('•');
        this.boton = new TextButton("Ingresar datos", this.skin);
        this.boton.addListener(new BotonRegistroListener(this.bd, this.usuarioTextField, this.contrasenaTextField));
        this.campos = new Table();
        this.campos.setFillParent(true);
        this.campos.add(this.usuarioTextField).height(50).width(200).padBottom(30);
        this.campos.row();
        this.campos.add(this.contrasenaTextField).height(50).width(200).padBottom(30);
        this.campos.row();
        this.campos.add(this.boton).height(50).width(200).padBottom(30);
        Gdx.input.setInputProcessor(this.escenario);
        this.escenario.addActor(campos);
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
        this.escenario.dispose();
    }

    @Override
    public void dispose() {
        this.escenario.dispose();
    }
}
