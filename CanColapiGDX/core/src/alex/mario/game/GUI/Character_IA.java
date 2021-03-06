package alex.mario.game.GUI;

import alex.mario.game.Interfaces.iCharacter_IA_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public class Character_IA extends Character implements iCharacter_IA_L {
    protected String id;
    protected boolean chasing;
    protected Vector2 positionToChase;
    protected float distWithPlayer;

    public Character_IA(MyGdxGame game, Map map, boolean chasing) {
        super(game, map);
        this.id = UUID.randomUUID().toString();
        this.chasing = chasing;
    }

    @Override
    public void think() {
        distWithPlayer = this.getCenterPos().dst(this.game.getPlayer().getCenterPos());

        if (chasing) {        //Follow positionToChase
            if (this.map != this.game.getPlayer().getMap()) {
                return;
            }

            if (this.getCenterPos().dst(this.positionToChase) > 30) { //Si la distancia es grande, ve hacia el objetivo. Si no no.
                this.direction = this.position.cpy().sub(this.positionToChase).nor().scl(-1, -1);
            } else {
                this.direction = new Vector2(0, 0);
            }
        } else {             //Follow path
            MapLayer PathObjectLayer = this.map.getTiledMap().getLayers().get("Path");
            MapObjects paths = PathObjectLayer.getObjects();
            for (RectangleMapObject rectangleObject : paths.getByType(RectangleMapObject.class)) {
                Rectangle rectangle = rectangleObject.getRectangle();
                MapProperties mapProperties = rectangleObject.getProperties();
                if (Intersector.overlaps(rectangle, this.getRect())) {
                    this.direction = MyGdxGame.getDirectionFromString(mapProperties.get("direction", "undefined", String.class));
                }
            }
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void getDamage(int damageQty) {
        System.out.println("Char " + this.getClass().getName().toString() + " got " + damageQty + " DAMAGE.");
    }
}
