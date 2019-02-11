package com.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pacman.Actores.Fantasma;
import com.pacman.Actores.PacMan;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PantallaJuegoPrincipal extends PantallaBase {
    //Dimensiones mapa
    private final float ANCHOENTILES = 19.0f;
    private final float ALTOENTILES = 21.0f;//setear para entrada de joystick y vidas y score
    private final float ANCHOENPX = 304.0f;
    private final float ALTOENPX = 336;

    //Aspectos de visualizacion
    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch batch;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private FitViewport stageViewport;

    //Mapa y Escenario
    private TiledMap mapa;
    private Stage escenario;

    private Mundo mundo;

    //Elementos Visuales //Irian en el manager del juego
    private Texture sprites;
    private PacMan pacman;
    private Fantasma fantasma1, fantasma2, fantasma3, fantasma4;

    public PantallaJuegoPrincipal(JuegoPrincipal juego) {
        super(juego);
    }

    /*MapProperties prop = tiledMap.getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        int widthEnPx = mapWidth * tilePixelWidth;
        int heightEnPx = mapHeight * tilePixelHeight;
        System.out.println(widthEnPx + "//" + heightEnPx);*/

    @Override
    public void show() {
        establecerCamara();

        MapProperties prop = mapa.getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);
        int widthEnPx = mapWidth * tilePixelWidth;
        int heightEnPx = mapHeight * tilePixelHeight;
        System.out.println("Mapa" + widthEnPx + "//" + heightEnPx);

        Mundo mundo = new Mundo(mapa, escenario);
        this.pacman = mundo.getPacman();
    }

    private void establecerCamara() {
        Gdx.graphics.setWindowedMode(304, 336);
        batch = new SpriteBatch();
        System.out.println("Pantalla" + Gdx.graphics.getWidth() + "//" + Gdx.graphics.getHeight());
        //Camera – eye in the scene, determines what the player can see, used by LibGDX to render the scene.
        //Viewport – controls how the render results from the camera are displayed to the user, be it with black bars, stretched or doing nothing at all.
        camera = new OrthographicCamera();
        viewport = new FitViewport(ANCHOENTILES, ALTOENTILES, camera);
        camera.translate(ALTOENTILES / 2, ANCHOENTILES / 2);
        camera.update();
        //Cargador y renderizador del mapa
        mapa = new TmxMapLoader().load("map/map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(mapa, 1 / 16f, batch);

        //modificar aca tambien para cuando entre el joysitic e info del juego
        stageViewport = new FitViewport(ANCHOENPX, ALTOENPX);
        escenario = new Stage(stageViewport, batch);

    }

    @Override
    public void hide() {
        escenario.dispose();
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
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        handleInput(); //usar input adapter, o la de procesador
        escenario.act();
        escenario.draw();
    }

    @Override
    public void dispose() {
        mapa.dispose();
        tiledMapRenderer.dispose();
        escenario.dispose();
        sprites.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    /*private void correctRectangle(Rectangle rectangle) {
        //rectangle.x = rectangle.x / PPM;
        rectangle.y = rectangle.y * 2;
        //rectangle.width = rectangle.width / PPM;
        //rectangle.height = rectangle.height / PPM;
    }

    private void corregir() {
        MapLayer collisionObjectLayer = mapa.getLayers().get("Wall");
        MapObjects objects = collisionObjectLayer.getObjects();
        // there are several other types, Rectangle is probably the most common one
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            correctRectangle(rectangle);
        }
        collisionObjectLayer = mapa.getLayers().get("Player");
        objects = collisionObjectLayer.getObjects();
        // there are several other types, Rectangle is probably the most common one
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            correctRectangle(rectangle);
        }
    }*/
}
