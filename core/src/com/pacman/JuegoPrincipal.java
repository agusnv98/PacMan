package com.pacman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class JuegoPrincipal extends Game {
    /*
    private TiledMap map;
    private AssetManager manager;

    Map properties
    private int tileWidth, tileHeight,
            mapWidthInTiles, mapHeightInTiles,
            mapWidthInPixels, mapHeightInPixels;

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;*/

    @Override
    public void create() {
        /*manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load("map/map.tmx", TiledMap.class);
        manager.finishLoading();

        map = manager.get("map/map.tmx", TiledMap.class);

        // Read properties
        MapProperties properties = map.getProperties();
        tileWidth         = properties.get("tilewidth", Integer.class);
        tileHeight        = properties.get("tileheight", Integer.class);
        System.out.println("Tile height px "+tileHeight+" Tile width px"+tileHeight);
        mapWidthInTiles   = properties.get("width", Integer.class);
        mapHeightInTiles  = properties.get("height", Integer.class);
        System.out.println("Map height in Tile "+mapHeightInTiles+" Map width in tiles "+mapWidthInTiles);
        mapWidthInPixels  = mapWidthInTiles  * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;

        // Set up the camera
        camera = new OrthographicCamera(mapWidthInPixels, mapHeightInPixels);
        //camera = new OrthographicCamera(mapHeightInPixels, mapWidthInPixels);
        //        camera.rotate(90);
        camera.position.x = mapWidthInPixels*0.5f;
        camera.position.y = mapHeightInPixels*0.5f;
        System.out.println("mapaAnchopx = " + mapWidthInPixels + " mapaAltopx = " + mapHeightInPixels);
        System.out.println("Pos Camara" + camera.position.x + "||" + camera.position.y);

        renderer = new OrthogonalTiledMapRenderer(map);
        */
        setScreen(new PantallaJuegoPrincipal(this));
    }

    /*@Override
    public void render() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //camera.update();
        //renderer.setView(camera);
        //renderer.render();
    }*/

    /*@Override
    public void dispose() {
        //manager.dispose();
    }
    */
}
