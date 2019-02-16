package com.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class BotonRegistroListener extends InputListener {

    private BaseDeDatos bd;
    private TextField usuarioTextField;
    private TextField contrasenaTextField;

    public BotonRegistroListener(BaseDeDatos bd, TextField campoUsuario, TextField campoContra) {
        this.bd = bd;
        this.usuarioTextField = campoUsuario;
        this.contrasenaTextField = campoContra;
    }

    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        this.bd.crearJugador(this.usuarioTextField.getText(), this.contrasenaTextField.getText());
        return true;
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        Gdx.app.log("my app", "Released");
    }
}
