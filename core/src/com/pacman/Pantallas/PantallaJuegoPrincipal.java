package com.pacman.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.I18NBundle;
import com.pacman.Actores.PacMan;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.pacman.Gamepad;
import com.pacman.JuegoPrincipal;
import com.pacman.Mundo;

public class PantallaJuegoPrincipal extends PantallaBase {
    //pantalla que se encarga de crear la partida que se va a jugar,
    // estableciendo la camara, los sonidos, el gamepad y el elemento que muestra el puntaje por pantalla

    private Mundo mundo;
    private PacMan pacman;

    //Elementos Visuales
    private TextArea puntajePantalla;
    private Texture sprites;

    private Gamepad touch;
    private Sound sonidoJuego;
    private AssetManager manager;

    public PantallaJuegoPrincipal(JuegoPrincipal juego) {
        super(juego);
    }

    @Override
    public void show() {
        //metodo que se ejecuta cuando se muestra por primera vez la aplicacion
        super.show();
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
        this.sonidoJuego.setLooping(0, true);
        this.sonidoJuego.play();

        //se establece el elemento que muestra el puntaje por pantalla
        this.puntajePantalla = new TextArea("Score: ", skin);
        this.puntajePantalla.setPosition(200, 336);
        this.escenario.addActor(puntajePantalla);

        //se obtienen los sprites para mostrar las vidas
        this.sprites = new Texture("personajes/actors.png");
    }

    public void actualizarScore() {
        this.puntajePantalla.setText(traductor.get("pantallaJuegoPrincipal.puntaje") + this.mundo.getPuntaje());
        //System.out.println(this.puntajePantalla.getText());
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
        int cantVidas = this.pacman.getCantVidas();
        Batch batch = this.escenario.getBatch();
        batch.begin();
        for (int i = 0; i < cantVidas; i++) {
            batch.draw(new TextureRegion(sprites, 179, 58, 14, 14), 1 + i, 21, 1, 1);
        }
        batch.end();
        //si el juego finalizo (estado 0 o 1), se establece la transicion a la pantalla de fin del juego
        //caso contrario continua con la ejecucuion del juego
        if (this.mundo.getEstadoJuego() != -1) {
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
}
