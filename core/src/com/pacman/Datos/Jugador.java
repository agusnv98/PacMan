package com.pacman.Datos;

public class Jugador {

    private String usuario;
    private String contraseña;
    private int maxPuntaje;

    public Jugador(String nombre, String contraseña) {
        this.usuario = nombre;
        this.contraseña = contraseña;
        this.maxPuntaje = 0;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public String getContraseña() {
        return this.contraseña;
    }

    public int getMaxPuntaje() {
        return this.maxPuntaje;
    }
}
