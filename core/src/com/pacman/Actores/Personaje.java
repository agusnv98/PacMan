package com.pacman.Actores;

import com.badlogic.gdx.Gdx;
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

    protected boolean muerto = false;

    public Personaje(Rectangle respawn, Mundo mundo) {
        //ver el tema de las animaciones, aunque no se podria implementar aca
        this.mundo = mundo;
        this.respawn = respawn; //se guarda la posicion a donde debe revivir el personaje
        this.direccion = new Vector2(0, 0); //se crea quieto
        this.estadoActual = 2; //se crea por defecto con estado izquierda
        this.limites = new Rectangle(this.respawn.getX(), this.respawn.getY(), this.respawn.getWidth() - 2, this.respawn.getHeight() - 2);
    }

    @Override
    public void act(float delta) {
        this.tiempoFrame += delta;
        if (!this.getEstado().equals("muerto")) {    //si el fantasma no esta muerto se carga la animacion que este
            this.frameActual = (TextureRegion) this.animActual.getKeyFrame(this.tiempoFrame);
        } else {
            //si el fantasma esta muerto se analiza si hay que reiniciar la animacion de muerte o continuar con su reproduccion
            if (!this.muerto) {
                this.tiempoFrame = 0;
                this.muerto = true;
            }
            this.frameActual = (TextureRegion) this.animActual.getKeyFrame(this.tiempoFrame);
            if (this.animActual.isAnimationFinished(tiempoFrame)) {
                //una vez que la animacion de muerte del fantasma termina, revive
                revivir();
            }
        }
        mover(delta);
        if (getX() > Gdx.graphics.getWidth() + this.frameActual.getRegionWidth()) {
            setPosition(0 - this.frameActual.getRegionWidth(), getY());
        } else if (getX() + this.frameActual.getRegionWidth() < 0) {
            setPosition(Gdx.graphics.getWidth(), getY());
        }
    }

    protected abstract void mover(float delta);

    protected void revivir() {
        this.muerto = false;
        this.limites = new Rectangle(this.respawn.getX(), this.respawn.getY(), this.respawn.getWidth() - 2, this.respawn.getHeight() - 2);
        this.setXY(this.limites.getX(), this.limites.getY());
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
            diferenciaVertical = choqueLateral(limitesPersonaje, pared);
            this.setXY(limitesPersonaje.getX() - diferenciaHorizontal, limitesPersonaje.getY() + diferenciaVertical);
            //System.out.println("DifH"+diferenciaHorizontal+"/"+"DifV"+diferenciaVertical);
        } else if (this.direccion.x < 0) {//va hacia la izquierda
            diferenciaHorizontal = (pared.getX() + pared.getWidth()) - limitesPersonaje.getX();
            diferenciaVertical = choqueLateral(limitesPersonaje, pared);
            this.setXY(limitesPersonaje.getX() + diferenciaHorizontal, limitesPersonaje.getY() + diferenciaVertical);
        } else if (this.direccion.y > 0) {//va hacia arriba
            diferenciaVertical = (limitesPersonaje.getY() + limitesPersonaje.getHeight()) - pared.getY();
            diferenciaHorizontal = choqueVertical(limitesPersonaje, pared);
            this.setXY(limitesPersonaje.getX() + diferenciaHorizontal, limitesPersonaje.getY() - diferenciaVertical);
        } else if (this.direccion.y < 0) {//va hacia abajo
            diferenciaVertical = (pared.getY() + pared.getHeight()) - limitesPersonaje.getY();
            diferenciaHorizontal = choqueVertical(limitesPersonaje, pared);
            this.setXY(limitesPersonaje.getX() + diferenciaHorizontal, limitesPersonaje.getY() + diferenciaVertical);
        }
    }

    private float choqueLateral(Rectangle limitesPersonaje, Rectangle pared) {
        float diferenciaVertical = 0;

        //determino si el personaje choco en la esquina superior por el lado derecho/izquierdo
        //se define como colision al tercio superior del personaje
        float tercioSup = (limitesPersonaje.getY() + (limitesPersonaje.getHeight() / 3) * 2);
        //determino si el personaje choco en la esquina inferior por lado derecho/izquierdo
        //se define como colision al tercio inferior del personaje
        float tercioInf = limitesPersonaje.getY() + (limitesPersonaje.getHeight() / 3);

        if (pared.getY() <= (limitesPersonaje.getY() + limitesPersonaje.getHeight()) && pared.getY() >= tercioSup) {
            //System.out.println("Choco en la esquina superior del lado derecho, corrigiendo");
            diferenciaVertical = (tercioSup - pared.getY()) / 2;
            //System.out.println(diferenciaVertical);
        } else {
            float bordeSupPared = pared.getY() + pared.getHeight();
            if (bordeSupPared <= tercioInf && bordeSupPared >= limitesPersonaje.getY()) {
                //System.out.println("Choco en la esquina inferior del lado derecho, corrigiendo");
                diferenciaVertical = (tercioInf - (pared.getY() + pared.getHeight())) / 2;
                //System.out.println(diferenciaVertical);
            } else {
                //System.out.println("Choco en el tercio central derecho, no corrijo");
            }
        }
        return diferenciaVertical;
    }

    private float choqueVertical(Rectangle limitesPersonaje, Rectangle pared) {
        float diferenciaHorizontal = 0;

        //determino si el personaje choco en la esquina izquierda por el lado superior/inferior
        //se define como colision al tercio izquierdo del personaje
        float tercioIzq = limitesPersonaje.getX() + (limitesPersonaje.getWidth() / 3);
        //determino si el personaje choco en la esquina derecha por el lado superior/inferior
        //se define como colision al tercio derecho del personaje
        float tercioDer = limitesPersonaje.getX() + ((limitesPersonaje.getHeight() / 3) * 2);

        float bordeDerPared = pared.getX() + pared.getWidth();
        if (bordeDerPared <= tercioIzq && bordeDerPared >= limitesPersonaje.getX()) {
            //System.out.println("Choco en la esquina superior izquierda, corrigiendo");
            diferenciaHorizontal = (tercioIzq - bordeDerPared) / 2;
            //System.out.println(diferenciaHorizontal);
        } else if (pared.getX() <= (limitesPersonaje.getX() + limitesPersonaje.getWidth()) && pared.getX() >= tercioDer) {
            // System.out.println("Choco en la esquina superior derecha, corrigiendo");
            diferenciaHorizontal = (tercioDer - pared.getX()) / 2;
            //System.out.println(diferenciaHorizontal);
        } else {
            //System.out.println("Choco en el tercio central superior, no corrijo");
        }
        return diferenciaHorizontal;
    }

    public Rectangle getLimites() {
        return this.limites;
    }

    public Vector2 getDireccion() {
        return this.direccion;
    }
}
