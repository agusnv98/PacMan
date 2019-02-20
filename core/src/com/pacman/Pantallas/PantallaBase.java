package com.pacman.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pacman.JuegoPrincipal;

public abstract class PantallaBase implements Screen {
    //clase abstracta que establece la estructura de las pantallas que se van a utilizar en el juego

    protected JuegoPrincipal juego;

    //Propiedades del mapa
    protected float anchoEnTiles;
    protected float altoEnTiles;
    protected float anchoEnPx;
    protected float altoEnPx;

    //Aspectos de visualizacion
    protected OrthographicCamera camera;
    protected FitViewport viewport;
    protected SpriteBatch batch;
    protected OrthogonalTiledMapRenderer tiledMapRenderer;
    protected FitViewport stageViewport;
    protected Skin skin;

    //Mapa y Escenario
    protected TiledMap mapa;
    protected Stage escenario;

    //Traductor
    protected I18NBundle traductor;

    public PantallaBase(JuegoPrincipal juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        //metodo que se ejecuta cuando se muestra por primera vez la aplicacion

        //carga de archivo de traduccion
        traductor = I18NBundle.createBundle(Gdx.files.internal("idiomas/idioma"));
    }

    @Override
    public void render(float delta) {
        //metodo que se ejecuta en cada frame del juego
    }

    @Override
    public void resize(int width, int height) {
        //metodo que se llama cuando las dimensiones de la pantalla cambian
        this.viewport.update(width, height);
        this.camera.position.set(this.camera.viewportWidth / 2, this.camera.viewportHeight / 2, 0);
    }

    @Override
    public void pause() {
        //metodo que se llama antes de que la aplicación pierda salga de la pantalla, antes de que se ejecute el metodo dispose()
    }

    @Override
    public void resume() {
        //metodo que es llamado cuando se devuelve el control a la aplicación despues de pause()
    }

    @Override
    public void hide() {
        //metodo que se ejecuta cuando se minimiza la aplicacion
    }

    @Override
    public void dispose() {
        //metodo que se ejecuta cuando se cierra la aplicacion y elimina los recursos innecesarios
    }

    protected void establecerCamara() {
        //metodo que se encarga de obtener las dimensiones del mapa y luego establecer la camara y los puntos de vista que muestran los elementos en pantalla

        //Cargador del mapa
        this.mapa = new TmxMapLoader().load("map/map.tmx");
        MapProperties prop = mapa.getProperties();
        this.anchoEnTiles = prop.get("width", Integer.class);
        this.altoEnTiles = prop.get("height", Integer.class) + 2;
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);
        this.anchoEnPx = this.anchoEnTiles * tilePixelWidth;
        this.altoEnPx = this.altoEnTiles * tilePixelHeight;
        //System.out.println("Pantalla px ancho "+anchoEnPx+" Alto "+altoEnPx);
        //System.out.println("Pantalla tiles ancho "+anchoEnTiles+" Alto "+altoEnTiles);

        //se establece el tamaño de la pantalla
        Gdx.graphics.setWindowedMode((int) this.anchoEnPx, (int) this.altoEnPx);

        //camara – se determina lo que se puede ver, y se usa para renderizar las imagenes
        //viewport – controla como se muestra lo renderizaro por la camara
        this.camera = new OrthographicCamera();
        viewport = new FitViewport(anchoEnTiles, altoEnTiles, this.camera);
        this.camera.translate(anchoEnTiles / 2, altoEnTiles / 2);
        this.camera.update();
        this.stageViewport = new FitViewport(this.anchoEnPx, this.altoEnPx);
        this.batch = new SpriteBatch();
        this.escenario = new Stage(this.stageViewport, this.batch);
    }
}
