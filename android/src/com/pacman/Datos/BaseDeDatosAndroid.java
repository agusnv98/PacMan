package com.pacman.Datos;

import android.content.Context;
import android.database.Cursor;

import com.pacman.BaseDeDatos;

public class BaseDeDatosAndroid implements BaseDeDatos {

    private final ServicioBD basedeDatos;

    public BaseDeDatosAndroid(Context contexto) {
        this.basedeDatos = new ServicioBD(contexto);
    }

    @Override
    public void crearJugador(String nombre, String contraseña) {
        if (!this.basedeDatos.verificarJugador(this.basedeDatos.getJugadorByUsuario(nombre))) {
            if (this.basedeDatos.insertarJugador(new Jugador(nombre, contraseña, 0)) != (-1)) {
                System.out.println("---------------------------------------Jugador " + nombre + " creado exitosamente--------------------------------");
            } else {
                System.out.println("--------------------------------------Ocurrio un error al crear al jugador " + nombre + " -----------------------------");
            }
        } else {
            System.out.println("----------------------------El jugador ya se encuentra en la base de datos-----------------------------------------");
        }
    }

    @Override
    public void logIn(String nombre, String contraseña) {
        Cursor cursorJugador = this.basedeDatos.getJugadorByUsuario(nombre);
        if (this.basedeDatos.verificarJugador(cursorJugador)) {
            if (this.basedeDatos.verificarContraseña(cursorJugador, contraseña)) {
                System.out.println("-------------------------------------Bienvenido " + nombre + "------------------------------------------");
            } else {
                System.out.println("----------------------------------------Contraseña incorrecta-----------------------------------------");
            }
        } else {
            System.out.println("----------------------------------------El jugador " + nombre + " no existe-----------------------------------");
        }
    }

    @Override
    public void actualizarPuntaje(String nombre, int puntos) {
        if (this.basedeDatos.actualizarPuntaje(nombre, puntos) == 1) {
            System.out.println("-----------------------------Puntaje del jugador " + nombre + " actualizado a " + puntos + " puntos----------------------------------");
        } else {
            System.out.println("------------------------------------Error al actualizar el puntaje de " + nombre + "----------------------------------------");
        }
    }

    public void mostrarDatos() {
        this.basedeDatos.mostrarBaseDatos();
    }
}

