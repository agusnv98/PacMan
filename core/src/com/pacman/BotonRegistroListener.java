package com.pacman;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class BotonRegistroListener extends InputListener {

    //Clase que extiende InputListener para manejar el comportamiento del boton de registro de usuario

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
        //Metodo que establece el comportamiento del boton cuando este es presionado
        //Realiza una consulta a la base de datos para agregar un nuevo jugador a la misma
        //Muestra un cartel por pantalla para indicar si la operacion tuvo exito o no
        Dialog ventana = new Dialog("", this.skin);
        TextButton boton = new TextButton("Ok", this.skin);
        ventana.button(boton);
        if (this.bd.crearJugador(this.usuarioTextField.getText(), this.contrasenaTextField.getText(), 0)) {
            ventana.text("Jugador creado");
            juego.setNombreJugador(this.usuarioTextField.getText());
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
