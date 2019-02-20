package com.pacman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.pacman.Pantallas.PantallaFinDelJuego;
import com.pacman.Pantallas.PantallaIngreso;
import com.pacman.Pantallas.PantallaJuegoPrincipal;
import com.pacman.Pantallas.PantallaMenu;
import com.pacman.Pantallas.PantallaRanking;
import com.pacman.Pantallas.PantallaRegistro;
import com.sun.accessibility.internal.resources.accessibility;

public class JuegoPrincipal extends Game {
    //clase principal sobre la que se establece el juegp
    //en esta se crean las diferentes pantallas y recibe la base de datos con la que se operara en el juego
    public final BaseDeDatos baseDeDatos;
    private PantallaJuegoPrincipal pantallaJuegoPrincipal;
    private PantallaFinDelJuego pantallaFinDelJuego;
    private PantallaIngreso pantallaIngreso;
    private PantallaRegistro pantallaRegistro;
    private PantallaMenu pantallaMenu;
    private PantallaRanking pantallaRanking;
    private String nombreJugador="Vacio";
    private int puntaje=0;

    public JuegoPrincipal(BaseDeDatos baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }

    @Override
    public void create() {
        Gdx.input.setCatchBackKey(true);  //se bloquea el boton back, para que no se cierre la aplicacion
        //Metodo que se llama cuando la aplicación es creada (antes de iniciar el game loop)
        //en este se inicializan las pantallas y se establece la pantalla principal
        this.baseDeDatos.inicializar();
        this.pantallaJuegoPrincipal = new PantallaJuegoPrincipal(this);
        this.pantallaFinDelJuego = new PantallaFinDelJuego(this, baseDeDatos);
        this.pantallaIngreso = new PantallaIngreso(this, baseDeDatos);
        this.pantallaRegistro = new PantallaRegistro(this, baseDeDatos);
        this.pantallaMenu = new PantallaMenu(this);
        this.pantallaRanking = new PantallaRanking(this, baseDeDatos);
        setScreen(this.pantallaMenu);
    }

    public PantallaRanking getPantallaRanking() {
        return pantallaRanking;
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

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public void setPuntajeActual(int puntajeActual) {
        this.puntaje = puntajeActual;
    }

    public String[] getDatosPartida() {
        String[] dato = {this.nombreJugador, Integer.toString(this.puntaje)};
        return dato;
    }
}