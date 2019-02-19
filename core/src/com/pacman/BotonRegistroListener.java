package com.pacman;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class BotonRegistroListener extends InputListener {

    private Stage escenario;
    private Skin skin;
    private BaseDeDatos bd;
    private TextField usuarioTextField;
    private TextField contrasenaTextField;
    private JuegoPrincipal juego;

    public BotonRegistroListener(Stage escenario, Skin skin, BaseDeDatos bd, TextField campoUsuario, TextField campoContra, JuegoPrincipal juego) {
        this.escenario = escenario;
        this.skin = skin;
        this.bd = bd;
        this.usuarioTextField = campoUsuario;
        this.contrasenaTextField = campoContra;
        this.juego = juego;
    }

    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        Dialog ventana = new Dialog("", this.skin);
        TextButton boton = new TextButton("Ok", this.skin);
        ventana.button(boton);
        if (this.bd.crearJugador(this.usuarioTextField.getText(), this.contrasenaTextField.getText(), 0)) {
            ventana.text("Jugador creado");
            juego.setScreen(juego.getPantallaJuegoPrincipal());
        } else {
            ventana.text("El jugador ya existe");
        }
        ventana.show(this.escenario);
        return true;
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
    }
}
