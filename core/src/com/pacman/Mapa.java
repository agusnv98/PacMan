package com.pacman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Mapa extends ApplicationAdapter {

    private OrthographicCamera camera;
    private final float WIDTH = 19.0f;
    private final float HEIGHT = 23.0f;
    private FitViewport viewport;
    private SpriteBatch batch;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    ////
    private FitViewport stageViewport;
    private Stage stage;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private boolean showBox2DDebuggerRenderer;
    private ImageButton flecha;

    @Override
    public void create() {

        //Creando flechas

        // Gdx.graphics.setWindowedMode(428,518);
        batch = new SpriteBatch();

        System.out.println(Gdx.graphics.getHeight() + "soy la altura" + Gdx.graphics.getWidth());
        //camara
        //Camera – eye in the scene, determines what the player can see, used by LibGDX to render the scene.
        //Viewport – controls how the render results from the camera are displayed to the user, be it with black bars, stretched or doing nothing at all.
        camera = new OrthographicCamera();
        viewport = new FitViewport(WIDTH, HEIGHT, camera);
        camera.translate(WIDTH / 2, HEIGHT / 2);
        camera.update();
        tiledMap = new TmxMapLoader().load("map/map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / 16f, batch);
        ///////
        stageViewport = new FitViewport(WIDTH * 20, HEIGHT * 20);
        stage = new Stage(stageViewport, batch);
        //////////////////////

    }

    @Override
    public void render() {
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        stage.draw();
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        tiledMapRenderer.dispose();
        stage.dispose();

    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }
}