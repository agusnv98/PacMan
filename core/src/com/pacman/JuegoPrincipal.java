package com.pacman;

import com.badlogic.gdx.Game;
import com.pacman.Pantallas.PantallaFinDelJuego;
import com.pacman.Pantallas.PantallaIngreso;
import com.pacman.Pantallas.PantallaJuegoPrincipal;
import com.pacman.Pantallas.PantallaMenu;
import com.pacman.Pantallas.PantallaRegistro;

public class JuegoPrincipal extends Game {
    //clase principal sobre la que se establece el juegp
    //en esta se crean las diferentes pantallas y recibe la base de datos con la que se operara en el juego
    public final BaseDeDatos baseDeDatos;

    private PantallaJuegoPrincipal pantallaJuegoPrincipal;
    private PantallaFinDelJuego pantallaFinDelJuego;
    private PantallaIngreso pantallaIngreso;
    private PantallaRegistro pantallaRegistro;
    private PantallaMenu pantallaMenu;

    public JuegoPrincipal(BaseDeDatos baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }

    @Override
    public void create() {
        //Metodo que se llama cuando la aplicación es creada (antes de iniciar el game loop)
        //en este se inicializan las pantallas y se establece la pantalla principal
        this.pantallaJuegoPrincipal = new PantallaJuegoPrincipal(this);
        this.pantallaFinDelJuego = new PantallaFinDelJuego(this);
        this.pantallaIngreso = new PantallaIngreso(this, baseDeDatos);
        this.pantallaRegistro = new PantallaRegistro(this, baseDeDatos);
        this.pantallaMenu = new PantallaMenu(this);
        setScreen(this.pantallaMenu);
    }

    public PantallaMenu getPantallaMenu() {
        return pantallaMenu;
    }

    public PantallaIngreso getPantallaIngreso() {
        return this.pantallaIngreso;
    }

    public PantallaRegistro getPantallaRegistro() {
        return this.pantallaRegistro;
    }

    public PantallaFinDelJuego getPantallaFinDelJuego() {
        return this.pantallaFinDelJuego;
    }

    public PantallaJuegoPrincipal getPantallaJuegoPrincipal() {
        return this.pantallaJuegoPrincipal;
    }

}

