package com.pacman;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.pacman.Actores.PacMan;

public class MiTouch extends Touchpad {
    private PacMan pac;

    public MiTouch(float deadzoneRadius, Skin skin, PacMan pac) {
        super(deadzoneRadius, skin);
        this.pac = pac;
    }

    @Override
    public void act(float delta) {
        //Este metodo se llama en toda la ejecución de la pantalla del juego
        super.act(delta);
        System.out.println("X"+this.getKnobX()+"---j---"+this.getKnobY()+"Y");
        //System.out.println(delta + "delta de touch");
        if (isTouched()) {
            //se verifica en caso de tocarse el touchpad, si fue un lugar de direccion habil
            if (this.getKnobX() >= 58 && this.getKnobX() <= 86 && this.getKnobY() >= 102 && this.getKnobY() <= 106) {
                pac.setEstado("arriba");
                System.out.println("gg");
                System.out.println(pac.getEstado() + "y estoy en" + pac.getDireccion());
            } else {
                if (this.getKnobX() >= 59 && this.getKnobX() <= 86 && this.getKnobY() >= 29 && this.getKnobY() <= 31) {
                    pac.setEstado("abajo");
                    System.out.println("gg");
                    System.out.println(pac.getEstado() + "y estoy en" + pac.getDireccion());

                } else {
                    if (this.getKnobX() >= 30 && this.getKnobX() <= 34 && this.getKnobY() >= 56 && this.getKnobY() <= 82) {
                        pac.setEstado("izquierda");
                        System.out.println("gg");
                        System.out.println(pac.getEstado() + "y estoy en" + pac.getDireccion());

                    } else {
                        if (this.getKnobX() >= 106 && this.getKnobX() <= 109 && this.getKnobY() >= 51 && this.getKnobY() <= 82) {
                            pac.setEstado("derecha");
                            System.out.println("gg");
                            System.out.println(pac.getEstado() + "y estoy en" + pac.getDireccion());
                        }
                    }
                }
            }
        }else{
            pac.setEstado("quieto");
        }
    }
}