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

    //Variables para la logica del personaje
    protected int estadoActual;
    protected Rectangle respawn;
    protected Mundo mundo;
    protected Rectangle limites;
    protected Vector2 direccion;

    //Variables para las animaciones
    protected static final float VELOCIDAD = 38f;
    protected static final float duracionFrame = 0.1f;
    protected boolean inicioAnimMuerto = false;
    protected float tiempoFrame;
    protected TextureRegion frameActual;
    protected Animation animIzq, animDer, animArriba, animAbajo, animActual;
    protected ArrayList<String> estados = new ArrayList<String>() {{
        add("izquierda");
        add("derecha");
        add("arriba");
        add("abajo");
    }};

    public Personaje(Rectangle respawn, Mundo mundo) {
        this.mundo = mundo;
        this.respawn = respawn; //se guarda la posicion a donde debe revivir el personaje
        this.direccion = new Vector2(0, 0); //el personaje se crea quieto por defecto
        this.estadoActual = 0; //se crea por defecto con estado izquierda
        this.limites = new Rectangle(this.respawn.getX(), this.respawn.getY(), this.respawn.getWidth() - 2, this.respawn.getHeight() - 2);
    }

    @Override
    public void act(float delta) {
        //metodo que se ejecuta en cada frame del juego y realiza las acciones propias del personaje
        this.tiempoFrame += delta;
        if (!this.getEstado().equals("muerto")) {    //si el personaje no esta muerto se carga la animacion que este asignada en animActual
            this.frameActual = (TextureRegion) this.animActual.getKeyFrame(this.tiempoFrame);
        } else {
            //si el personaje esta muerto se analiza si hay que reiniciar la animacion de muerte o continuar con su reproduccion
            if (!this.inicioAnimMuerto) {
                this.tiempoFrame = 0;
                this.inicioAnimMuerto = true;
            }
            this.frameActual = (TextureRegion) this.animActual.getKeyFrame(this.tiempoFrame);
            if (this.animActual.isAnimationFinished(tiempoFrame)) {
                //una vez que la animacion de muerte del personaje termina, este revive
                revivir();
            }
        }
        mover(delta);
        //se verifica si el personaje esta por dar la vuelta al mundo, por alguno de sus laterales (izquierdo/derecho)
        //y luego se lo posiciona al lado opuesto del mapa
        if (getX() > mundo.getAncho() + this.frameActual.getRegionWidth()) {
            setPosition(0 - this.frameActual.getRegionWidth(), getY());
        } else if (getX() + this.frameActual.getRegionWidth() < 0) {
            setPosition(mundo.getAncho(), getY());
        }
    }

    //metodo que realiza el movimiento del personaje (cada personaje implementa su propio metodo)
    protected abstract void mover(float delta);

    protected void revivir() {
        //metodo que se ejecuta cuando termina la animacion de muerte del personaje
        //restaura al personaje en su posicion inicial y reinicia la animacion de muerte
        this.inicioAnimMuerto = false;
        this.limites = new Rectangle(this.respawn.getX(), this.respawn.getY(), this.respawn.getWidth() - 2, this.respawn.getHeight() - 2);
        this.setXY(this.limites.getX(), this.limites.getY());
    }

    //metodo que permite establecerle un estado al personaje (cada personaje implementa su propio metodo)
    public abstract boolean setEstado(String estado);

    public String getEstado() {
        //metodo que retorna un String con el estado actual del personaje
        return this.estados.get(this.estadoActual);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //metodo que se ejecuta en cada frame del juego
        //se encarga de dibujar el frameActual de la animacion en la pantalla
        batch.draw(frameActual, getX(), getY());
    }

    protected void setXY(float pX, float pY) {
        //metodo auxiliar que sirve para mover tanto al personaje (Actor), como a sus limites (Rectangle)
        setPosition(pX, pY);
        this.limites.setPosition(pX, pY);
    }

    public void reacomodar(Rectangle pared) {
        //metodo que se llama cuando ocurrio una colision con una pared
        //este metodo reacomoda, tanto horizontal como verticalmente, al personaje para simular la colision con la pared

        //System.out.println("Colision Personaje X = " + this.limites.getX() + "// Y = " + this.limites.getY() + "|| Pared X = " + pared.getX() + " // Pared Y = " + (pared.getY()));
        //System.out.println("Ancho pared " + pared.getWidth() + " //Alto " + pared.getHeight());
        //System.out.println("Direccion Personaje X = " + this.direccion.x + " // Y = " + this.direccion.y);
        float diferenciaHorizontal;                        //obtiene cuantas unidadades en el ejeX se superpuso el personaje sobre la pared
        float diferenciaVertical;                          //obtiene cuantas unidades en el ejeY se superpuso el personaje sobre la pared
        if (this.direccion.x > 0) {                        //el personaje se mueve hacia la derecha
            diferenciaHorizontal = (this.limites.getX() + this.limites.getWidth()) - pared.getX();
            diferenciaVertical = colisionLateral(pared);
            this.setXY(this.limites.getX() - diferenciaHorizontal, this.limites.getY() + diferenciaVertical);
            //System.out.println("DifH"+diferenciaHorizontal+"/"+"DifV"+diferenciaVertical);
        } else if (this.direccion.x < 0) {                 //el personaje se mueve hacia la izquierda
            diferenciaHorizontal = (pared.getX() + pared.getWidth()) - this.limites.getX();
            diferenciaVertical = colisionLateral(pared);
            this.setXY(this.limites.getX() + diferenciaHorizontal, this.limites.getY() + diferenciaVertical);
            //System.out.println("DifH"+diferenciaHorizontal+"/"+"DifV"+diferenciaVertical);
        } else if (this.direccion.y > 0) {                 //el personaje se mueve hacia arriba
            diferenciaVertical = (this.limites.getY() + this.limites.getHeight()) - pared.getY();
            diferenciaHorizontal = colisionVertical(pared);
            this.setXY(this.limites.getX() + diferenciaHorizontal, this.limites.getY() - diferenciaVertical);
            //System.out.println("DifH"+diferenciaHorizontal+"/"+"DifV"+diferenciaVertical);
        } else if (this.direccion.y < 0) {                 //el personaje se mueve hacia abajo
            diferenciaVertical = (pared.getY() + pared.getHeight()) - this.limites.getY();
            diferenciaHorizontal = colisionVertical(pared);
            this.setXY(this.limites.getX() + diferenciaHorizontal, this.limites.getY() + diferenciaVertical);
            //System.out.println("DifH"+diferenciaHorizontal+"/"+"DifV"+diferenciaVertical);
        }
    }

    private float colisionLateral(Rectangle pared) {
        //metodo que detecta si el personaje colisiono por alguno de sus laterales (izquierdo/derecho)
        //luego determina si fue en su esquina superior o inferior, y determina el desplazamiento para reacomodarlo
        //retorna la cantidad que debe desplazarse en el ejeY
        float diferenciaVertical = 0;

        //determina si el personaje choco en la esquina superior por el lado derecho/izquierdo
        //se define como colision al tercio superior del personaje
        float tercioSup = (this.limites.getY() + ((this.limites.getHeight() / 3) * 2));

        //determina si el personaje choco en la esquina inferior por lado derecho/izquierdo
        //se define como colision al tercio inferior del personaje
        float tercioInf = this.limites.getY() + (this.limites.getHeight() / 3);

        if (pared.getY() <= (this.limites.getY() + this.limites.getHeight()) && pared.getY() >= tercioSup) {
            diferenciaVertical = (tercioSup - pared.getY()) / 2;                     //obtiene un valor negativo (tercioSup <= bordeSupPared)
            //System.out.println("Choco en la esquina superior del lado derecho/izquierdo, corrigiendo");
            //System.out.println(diferenciaVertical);
        } else {
            float bordeSupPared = pared.getY() + pared.getHeight();
            if (bordeSupPared <= tercioInf && bordeSupPared >= this.limites.getY()) {
                diferenciaVertical = (tercioInf - bordeSupPared) / 2;                //obtiene un valor positivo (tercioInf >= bordeSupPared)
                //System.out.println("Choco en la esquina inferior del lado derecho/izquierdo, corrigiendo");
                //System.out.println(diferenciaVertical);
            } else {
                //System.out.println("Choco en el tercio central derecho/izquierdo, no corrijo");
            }
        }
        return diferenciaVertical;
    }

    private float colisionVertical(Rectangle pared) {
        //metodo que detecta si el personaje colisiono por alguno de sus laterales (superior/inferior)
        //luego determina si fue en su esquina izquierda o derecha, y determina el desplazamiento para reacomodarlo
        //retorna la cantidad que debe desplazarse en el ejeY
        float diferenciaHorizontal = 0;

        //determino si el personaje choco en la esquina izquierda por el lado superior/inferior
        //se define como colision al tercio izquierdo del personaje
        float tercioIzq = this.limites.getX() + (this.limites.getWidth() / 3);

        //determino si el personaje choco en la esquina derecha por el lado superior/inferior
        //se define como colision al tercio derecho del personaje
        float tercioDer = this.limites.getX() + ((this.limites.getWidth() / 3) * 2);

        float bordeDerPared = pared.getX() + pared.getWidth();
        if (bordeDerPared <= tercioIzq && bordeDerPared >= this.limites.getX()) {
            diferenciaHorizontal = (tercioIzq - bordeDerPared) / 2;       //obtiene un valor positivo (tercioIzq >= bordeSupPared)
            //System.out.println("Choco en la esquina superior/inferior izquierda, corrigiendo");
            //System.out.println(diferenciaHorizontal);
        } else if (pared.getX() <= (this.limites.getX() + this.limites.getWidth()) && pared.getX() >= tercioDer) {
            diferenciaHorizontal = (tercioDer - pared.getX()) / 2;      //obtiene un valor negativo (tercioDer <= bordeSupPared)
            //System.out.println("Choco en la esquina superior/inferior derecha, corrigiendo");
            //System.out.println(diferenciaHorizontal);
        } else {
            //System.out.println("Choco en el tercio central superior/inferior, no corrijo");
        }
        return diferenciaHorizontal;
    }

    public Rectangle getLimites() {
        return this.limites;
    }

    public Vector2 getDireccion() {
        //metodo que retorna la direccion en la que se esta moviendo el personaje
        return this.direccion;
    }
}
