package com.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.pacman.Actores.PacMan;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class PantallaJuegoPrincipal extends PantallaBase {
    //pantalla que se encarga de crear la partida que se va a jugar,
    // estableciendo la camara, los sonidos, el gamepad y el elemento que muestra el puntaje por pantalla

    private Mundo mundo;
    private PacMan pacman;

    //Elementos Visuales
    private TextArea puntajePantalla;
    private Texture sprites;

    private Skin skin;
    private Gamepad touch;
    private Sound sonidoJuego;
    private AssetManager manager;

    public PantallaJuegoPrincipal(JuegoPrincipal juego) {
        super(juego);
    }

    @Override
    public void show() {
        //metodo que se ejecuta cuando se muestra por primera vez la aplicacion

        establecerCamara();             //se establece la camara
        establecerSonido();             //se establecen los sonidos del juego
        //System.out.println("Mapa" + widthEnPx + "//" + heightEnPx);

        this.mundo = new Mundo(this.mapa, this.escenario, this.manager);
        this.pacman = this.mundo.getPacman();

        //se establece el gamePad
        Gdx.input.setInputProcessor(escenario);
        this.skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));//skin para los botones
        this.touch = new Gamepad(15, this.skin, this.pacman);
        this.touch.setBounds(76, 0, 140, 140);
        this.escenario.addActor(this.touch);

        //se inicia la reproduccion del sonido del juego
        this.sonidoJuego = this.manager.get("sounds/pac-mans-park-block-plaza-super-smash-bros-3ds.ogg");
        this.sonidoJuego.setLooping(0,true);
        this.sonidoJuego.play();

        //se establece el elemento que muestra el puntaje por pantalla
        this.puntajePantalla = new TextArea("Score: ", skin);
        this.puntajePantalla.setPosition(200, 350);
        this.escenario.addActor(puntajePantalla);
    }

    public void actualizarScore() {
        this.puntajePantalla.setText("Puntaje:" + this.mundo.getPuntaje());
        System.out.println(this.puntajePantalla.getText());
    }

    @Override
    protected void establecerCamara() {
        //metodo que utiliza al meotodo de la clase padre y agrega al renderizador del mapa, para que se muestre por pantalla
        super.establecerCamara();
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(mapa, 1 / 16f, this.batch);
    }

    private void establecerSonido() {
        //metodo que carga los sonidos que se van a usar en el juego
        this.manager = new AssetManager();
        this.manager.load("sounds/big_pill.ogg", Sound.class);
        this.manager.load("sounds/clear.ogg", Sound.class);
        this.manager.load("sounds/ghost_die.ogg", Sound.class);
        this.manager.load("sounds/pacman_die.ogg", Sound.class);
        this.manager.load("sounds/pill.ogg", Sound.class);
        this.manager.load("sounds/pac-mans-park-block-plaza-super-smash-bros-3ds.ogg", Sound.class);
        this.manager.finishLoading();
    }

    @Override
    public void hide() {
        //metodo que se ejecuta cuando se minimiza la aplicacion
        Gdx.input.setInputProcessor(null);
    }

    private boolean handleInput() {
        boolean presiono = false;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            presiono = true;
            pacman.setEstado("izquierda");
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            presiono = true;
            pacman.setEstado("derecha");
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            presiono = true;
            pacman.setEstado("arriba");
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            presiono = true;
            pacman.setEstado("abajo");
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            presiono = true;
            pacman.setEstado("muerto");
        } else {
            //System.out.println("Ninguna");
            pacman.setEstado("quieto");
        }
        return presiono;
    }

    @Override
    public void render(float delta) {
        //metodo que se ejecuta en cada frame del juego
        //es el encargado de verificar si el juego termino o no, ademas
        //de hacer que los elementos en el escenario actuen y se dibujen por pantalla
        this.camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.tiledMapRenderer.setView(this.camera);
        this.tiledMapRenderer.render();
        //handleInput();asdadasdadadsdddddddddddddddddddddddddddddddddddddd
        //si el juego finalizo, se establece la transicion a la pantalla de fin del juego
        //caso contrario continua con la ejecucuion del juego
        if (this.mundo.getFinDelJuego()) {
            escenario.addAction(Actions.sequence(
                    Actions.delay(0.30f),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            juego.setScreen(juego.getPantallaFinDelJuego());
                        }
                    })
            ));
        }
        actualizarScore();
        this.escenario.act();
        this.escenario.draw();
    }

    @Override
    public void dispose() {
        //metodo que se ejecuta cuando se cierra la aplicacion y elimina los recursos innecesarios
        this.skin.dispose();
        this.mapa.dispose();
        this.tiledMapRenderer.dispose();
        this.escenario.dispose();
        this.sprites.dispose();
    }

    @Override
    public void resize(int width, int height) {
        //metodo que se llama cuando las dimensiones de la pantalla cambian
        this.viewport.update(width, height);
        this.camera.position.set(this.camera.viewportWidth / 2, this.camera.viewportHeight / 2, 0);
    }
}
