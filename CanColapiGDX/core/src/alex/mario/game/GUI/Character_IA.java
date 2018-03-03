package alex.mario.game.GUI;

import alex.mario.game.Interfaces.iCharacter_IA_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

public class Character_IA extends Character implements iCharacter_IA_L {
    public Character_IA(MyGdxGame game, Map map) {
        super(game, map);
    }

    @Override
    public void think() {

        MapLayer PathObjectLayer = this.map.getTiledMap().getLayers().get("Path");
        MapObjects paths = PathObjectLayer.getObjects();

        for (RectangleMapObject rectangleObject : paths.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            MapProperties mapProperties = rectangleObject.getProperties();
            if(Intersector.overlaps(rectangle, this.getRect())){
                this.direction = MyGdxGame.getDirectionFromString(mapProperties.get("direction", "undefined", String.class));
            }
        }
    }
}
