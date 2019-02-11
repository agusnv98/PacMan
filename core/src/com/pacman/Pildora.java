package com.pacman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Pildora extends Actor {

    private TextureRegion imagen;
    private int idPildora = 0;
    private Rectangle limites;
    private boolean esGrande;
    private boolean valida;

    public Pildora(Texture imagen, Rectangle limites, boolean esGrande) {
        this.valida = true;
        if (esGrande) {
            this.imagen = new TextureRegion(imagen, 147, 39, 16, 16);
        } else {
            this.imagen = new TextureRegion(imagen, 131, 39, 16, 16);
        }
        this.idPildora++;
        this.esGrande = esGrande;
        this.limites = limites;
        this.setPosition(this.limites.getX(), this.limites.getY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(imagen, getX(), getY());
    }

    public void esComida() {
        if (this.valida) {
            this.valida = false;
            System.out.println("La pildora fue comida");
            this.remove();
        }
    }

    public int getId() {
        return this.idPildora;
    }

    public Rectangle getLimites() {
        return this.limites;
    }
}
