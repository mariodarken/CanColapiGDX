package alex.mario.game.GUI;

import alex.mario.game.LOGIC.Item_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public class Item extends Item_L {
    protected GlyphLayout itemNameLayout;
    protected Texture texture;
    protected boolean isVisible;

    public Item(MyGdxGame game, Map map, RectangleMapObject rectangleMapObject){
        super(game, map, rectangleMapObject);
        this.isVisible = rectangleMapObject.getProperties().get("isVisible", true, Boolean.class);

        this.itemNameLayout = new GlyphLayout();
    }
    public void draw(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch, BitmapFont font, Vector2 pos, Vector2 size)
    {
        shapeRenderer.begin();
        //Calculate text width-height
        this.itemNameLayout.setText(font, this.name, Color.WHITE, size.x - 10, Align.left, true);

        //Bakcground
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(
                pos.x, pos.y,
                size.x, size.y
        );

        shapeRenderer.end();

        spriteBatch.begin();
        spriteBatch.draw(texture, pos.x + size.x / 2 - this.texture.getWidth() / 2, pos.y + size.y / 2 - this.texture.getHeight() / 2);
        //Message
        font.draw(spriteBatch, this.name,
                pos.x + 5, pos.y + this.itemNameLayout.height + 5,
                size.x - 5, Align.center, true);

        spriteBatch.end();
    }
    public void drawGround(SpriteBatch spriteBatch, Camera camera)
    {
        //Requires .begin()!!!
        if(!this.isVisible){
            return;
        }
        spriteBatch.draw(texture, this.pos.x, this.pos.y);
    }

    @Override
    public void useGround(Character character){
        Map map = character.getMap();
        map.removeItem(this);
        character.addItem(this);
        return;
    }

    public String getName() {
        return this.name;
    }

}
