package alex.mario.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class MapSystem {
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    CameraSystem cs;

    MapSystem(CameraSystem cs){
        this.cs = cs;
        tiledMap = new TmxMapLoader().load("MapaTest.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    void DrawBackground(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cs.camera.update();
        tiledMapRenderer.setView(cs.camera);
        tiledMapRenderer.render(new int[]{0,1});
    }

    void DrawForeground(){
        tiledMapRenderer.render(new int[]{2,4});
    }

    public boolean colisiono(Rectangle playerRectangle){
        MapLayer collisionObjectLayer = tiledMap.getLayers().get("Colisiones");
        MapObjects objects = collisionObjectLayer.getObjects();

        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, playerRectangle)){
                return true;
            }
        }

        return false;
    }
}
