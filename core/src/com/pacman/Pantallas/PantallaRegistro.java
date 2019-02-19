package com.pacman.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.I18NBundle;
import com.pacman.BaseDeDatos;
import com.pacman.BotonRegistroListener;
import com.pacman.JuegoPrincipal;

public class PantallaRegistro extends PantallaBase {

    private BaseDeDatos bd;

    private TextField usuarioTextField;
    private TextField contrasenaTextField;
    private TextButton boton;
    private Table campos;
    private BitmapFont fuente;
    private I18NBundle traductor;

    public PantallaRegistro(JuegoPrincipal juego, BaseDeDatos bd) {
        super(juego);
        this.bd = bd;
    }

    @Override
    public void show() {
        //carga de archivo de traduccion
        traductor = I18NBundle.createBundle(Gdx.files.internal("idiomas/idioma"));
        establecerCamara();
        this.fuente = new BitmapFont();
        this.fuente.setColor(0, 204, 204, 1);
        this.skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));
        this.usuarioTextField = new TextField(traductor.get("pantallaIngreso/Registro.usuario"), this.skin);
        this.contrasenaTextField = new TextField(traductor.get("pantallaIngreso/Registro.contrasena"), this.skin);
        this.contrasenaTextField.setPasswordMode(true);
        this.contrasenaTextField.setPasswordCharacter('•');
        this.boton = new TextButton(traductor.get("pantallaIngreso/Registro.botonIngreso"), this.skin);
        this.boton.addListener(new BotonRegistroListener(this.escenario, this.skin, this.bd, this.usuarioTextField, this.contrasenaTextField, this.juego));
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
        this.batch.begin();
        this.fuente.draw(this.batch, traductor.get("pantallaRegistro.titulo"), (this.anchoEnPx / 2) - (this.fuente.getRegion().getRegionWidth() / 3.3f), this.altoEnPx - 30);
        this.batch.end();
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
        skin.dispose();
    }
}
