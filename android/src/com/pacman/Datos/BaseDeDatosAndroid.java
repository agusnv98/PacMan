package com.pacman.Datos;

import android.content.Context;
import android.database.Cursor;

import com.badlogic.gdx.utils.XmlReader;
import com.pacman.BaseDeDatos;

public class BaseDeDatosAndroid implements BaseDeDatos {

    private final ServicioBD basedeDatos;

    public BaseDeDatosAndroid(Context contexto) {
        this.basedeDatos = new ServicioBD(contexto);
    }

    @Override
    public boolean crearJugador(String nombre, String contraseña) {
        boolean resultado = false;
        if (!this.basedeDatos.verificarJugador(this.basedeDatos.getJugadorByUsuario(nombre))) {
            if (this.basedeDatos.insertarJugador(new Jugador(nombre, contraseña, 0)) != (-1)) {
                System.out.println("---------------------------------------Jugador " + nombre + " creado exitosamente--------------------------------");
                resultado = true;
            } else {
                System.out.println("--------------------------------------Ocurrio un error al crear al jugador " + nombre + " -----------------------------");
            }
        } else {
            System.out.println("----------------------------El jugador ya se encuentra en la base de datos-----------------------------------------");
        }
        return resultado;
    }

    @Override
    public boolean logIn(String nombre, String contraseña) {
        boolean resultado = false;
        Cursor cursorJugador = this.basedeDatos.getJugadorByUsuario(nombre);
        if (this.basedeDatos.verificarJugador(cursorJugador)) {
            if (this.basedeDatos.verificarContraseña(cursorJugador, contraseña)) {
                System.out.println("-------------------------------------Bienvenido " + nombre + "------------------------------------------");
                resultado = true;
            } else {
                System.out.println("----------------------------------------Contraseña incorrecta-----------------------------------------");
            }
        } else {
            System.out.println("----------------------------------------El jugador " + nombre + " no existe-----------------------------------");
        }
        return resultado;
    }

    @Override
    public boolean actualizarPuntaje(String nombre, int puntos) {
        boolean resultado=false;
        if (this.basedeDatos.actualizarPuntaje(nombre, puntos)) {
            resultado=true;
            System.out.println("-----------------------------Puntaje del jugador " + nombre + " actualizado a " + puntos + " puntos----------------------------------");
        } else {
            System.out.println("------------------------------------Error al actualizar el puntaje de " + nombre + "----------------------------------------");
        }
        return resultado;
    }

    public void mostrarDatos() {
        this.basedeDatos.mostrarBaseDatos();
    }
}

