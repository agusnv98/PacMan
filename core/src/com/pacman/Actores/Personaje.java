package com.pacman.Actores;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pacman.Mundo;

import java.util.ArrayList;

public abstract class Personaje extends Actor {

    protected static final float VELOCIDAD = 35f;
    protected static final float duracionFrame = 0.1f;
    protected float tiempoFrame;

    protected int estadoActual;
    protected Rectangle respawn;
    protected Mundo mundo;
    protected Rectangle limites;
    protected Vector2 direccion;
    protected TextureRegion frameActual;

    protected Animation animIzq, animDer, animArriba, animAbajo, animActual;

    protected ArrayList<String> estados = new ArrayList<String>() {{
        add("izquierda");
        add("derecha");
        add("arriba");
        add("abajo");
    }};

    public Personaje(Rectangle respawn, Mundo mundo) {
        //ver el tema de las animaciones, aunque no se podria implementar aca
        this.mundo = mundo;
        this.respawn = respawn;
        this.direccion = new Vector2(0, 0); //se crea quieto
        this.estadoActual = 2; //se crea por defecto con estado izquierda
        //this.limites = new Rectangle(this.respawn.getX()+1.5f, this.respawn.getY()+1.5f, this.respawn.getWidth()-3, this.respawn.getHeight()-3);
        this.limites = new Rectangle(this.respawn.getX(), this.respawn.getY(), this.respawn.getWidth() - 2, this.respawn.getHeight() - 2);
    }

    public abstract boolean setEstado(String estado);

    public String getEstado() {
        return this.estados.get(this.estadoActual);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(frameActual, getX(), getY());
    }

    public void setXY(float pX, float pY) {
        setPosition(pX, pY);
        this.limites.setX(pX);
        this.limites.setY(pY);
    }

    public void reacomodar(Rectangle pared) {
        //System.out.println("Colision  Personaje X=" + this.limites.getX() + "// Y= " + this.limites.getY() + "|| Pared X = " + pared.getX() + "//" + (pared.getY()));
        //System.out.println("Ancho pared" + pared.getWidth() + "Alto" + pared.getHeight());
        //System.out.println("Direccion" + this.direccion.x + "//" + this.direccion.y);
        float diferenciaHorizontal = 0; //obtiene cuantas unidadades en el ejeX se superpuso el personaje sobre la pared
        float diferenciaVertical = 0; //obtiene cuantas unidades en el ejeY se superpuso el personaje sobre la pared
        Rectangle limitesPersonaje = this.limites;
        if (this.direccion.x > 0) {//va hacia la derecha
            diferenciaHorizontal = (limitesPersonaje.getX() + limitesPersonaje.getWidth()) - pared.getX();
            //determino si el personaje choco por la esquina superior derecha
            //se define como colision al tercio superior del lado derecho del personaje
            float diferenciaEsquinaDerSup = (limitesPersonaje.getY() + (limitesPersonaje.getHeight() / 3) * 2) - pared.getY();
            //determino si el personaje choco por la esquina inferior derecha
            //se define como colision al tercio inferior del lado derecho del personaje
            float diferenciaEsquinaDerInf = (limitesPersonaje.getY() + (limitesPersonaje.getHeight() / 3)) - (pared.getY() + pared.getHeight());
            if (diferenciaEsquinaDerSup <= 0) {
                System.out.println("Choco en la esquina inferior derecha, corrigiendo");
                diferenciaVertical = diferenciaEsquinaDerSup / 2;
                System.out.println(diferenciaVertical);
            } else if (diferenciaEsquinaDerInf >= 0) {
                System.out.println("Choco en la esquina inferior derecha, corrigiendo");
                diferenciaVertical = diferenciaEsquinaDerInf / 2;
                System.out.println(diferenciaVertical);
            } else {
                System.out.println("Choco en el tercio central derecho, no corrijo");
            }
            this.setXY(limitesPersonaje.getX() - diferenciaHorizontal, limitesPersonaje.getY() + diferenciaVertical);
        } else if (this.direccion.x < 0) {//va hacia la izquierda
            diferenciaHorizontal = (pared.getX() + pared.getWidth()) - limitesPersonaje.getX();
            //determino si el personaje choco por la esquina superior izquierda
            //se define como colision al tercio superior del lado izquierdo del personaje
            float diferenciaEsquinaIzqSup = (limitesPersonaje.getY() + (limitesPersonaje.getHeight() / 3) * 2) - pared.getY();
            //determino si el personaje choco por la esquina inferior izquierda
            //se define como colision al tercio inferior del lado izquierdo del personaje
            float diferenciaEsquinaIzqInf = (limitesPersonaje.getY() + (limitesPersonaje.getHeight() / 3)) - (pared.getY() + pared.getHeight());
            if (diferenciaEsquinaIzqSup <= 0) {
                System.out.println("Choco en la esquina inferior izquierda, corrigiendo");
                diferenciaVertical = diferenciaEsquinaIzqSup / 2;
                System.out.println(diferenciaVertical);
            } else if (diferenciaEsquinaIzqInf >= 0) {
                System.out.println("Choco en la esquina inferior izquierda, corrigiendo");
                diferenciaVertical = diferenciaEsquinaIzqInf / 2;
                System.out.println(diferenciaVertical);
            } else {
                System.out.println("Choco en el tercio central izquierdo, no corrijo");
            }
            this.setXY(limitesPersonaje.getX() + diferenciaHorizontal, limitesPersonaje.getY() + diferenciaVertical);
        } else if (this.direccion.y > 0) {//va hacia arriba
            diferenciaVertical = (limitesPersonaje.getY() + limitesPersonaje.getHeight()) - pared.getY();
            //determino si el personaje choco por la esquina superior izquierda
            //se define como colision al tercio izquierdo del lado superior del personaje
            float diferenciaEsquinaSupIzq = (limitesPersonaje.getX() + (limitesPersonaje.getWidth() / 3)) - (pared.getX() + pared.getWidth());
            //determino si el personaje choco por la esquina superior derecha
            //se define como colision al tercio derecho del lado superior del personaje
            float diferenciaEsquinaSupDer = (limitesPersonaje.getX() + (limitesPersonaje.getHeight() / 3) * 2) - pared.getX();
            if (diferenciaEsquinaSupIzq >= 0) {
                System.out.println("Choco en la esquina superior izquierda, corrigiendo");
                diferenciaHorizontal = diferenciaEsquinaSupIzq / 2;
                System.out.println(diferenciaHorizontal);
            } else if (diferenciaEsquinaSupDer <= 0) {
                System.out.println("Choco en la esquina inferior derecha, corrigiendo");
                diferenciaHorizontal = diferenciaEsquinaSupDer / 2;
                System.out.println(diferenciaHorizontal);
            } else {
                System.out.println("Choco en el tercio central superior, no corrijo");
            }
            this.setXY(limitesPersonaje.getX() + diferenciaHorizontal, limitesPersonaje.getY() - diferenciaVertical);
        } else if (this.direccion.y < 0) {//va hacia abajo
            diferenciaVertical = (pared.getY() + pared.getHeight()) - limitesPersonaje.getY();
            //determino si el personaje choco por la esquina inferior izquierda
            //se define como colision al tercio izquierdo del lado inferior del personaje
            float diferenciaEsquinaInfIzq = (limitesPersonaje.getX() + (limitesPersonaje.getWidth() / 3)) - (pared.getX() + pared.getWidth());
            //determino si el personaje choco por la esquina inferior derecha
            //se define como colision al tercio derecho del lado inferior del personaje
            float diferenciaEsquinaSupDer = (limitesPersonaje.getX() + (limitesPersonaje.getHeight() / 3) * 2) - pared.getX();
            if (diferenciaEsquinaInfIzq >= 0) {
                System.out.println("Choco en la esquina superior izquierda, corrigiendo");
                diferenciaHorizontal = diferenciaEsquinaInfIzq / 2;
                System.out.println(diferenciaHorizontal);
            } else if (diferenciaEsquinaSupDer <= 0) {
                System.out.println("Choco en la esquina inferior derecha, corrigiendo");
                diferenciaHorizontal = diferenciaEsquinaSupDer / 2;
                System.out.println(diferenciaHorizontal);
            } else {
                System.out.println("Choco en el tercio central inferior, no corrijo");
            }
            this.setXY(limitesPersonaje.getX()+diferenciaHorizontal, limitesPersonaje.getY() + diferenciaVertical);
        }
    }

    public Rectangle getLimites() {
        return limites;
    }
}
