package alex.mario.game.GUI;

import alex.mario.game.LOGIC.Item_L;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public class Item extends Item_L {
    private GlyphLayout notificationLayout;

    public Item(){
        this.notificationLayout = new GlyphLayout();
    }
    public Item(String name){
        this.notificationLayout = new GlyphLayout();
        this.name = name;
    }
    public void draw(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch, BitmapFont font, Vector2 pos, Vector2 size)
    {
        shapeRenderer.begin();
        //Calculate text width-height
        this.notificationLayout.setText(font, this.name, Color.WHITE, size.x - 10, Align.left, true);

        //Bakcground
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(
                pos.x, pos.y,
                size.x, size.y
        );
        shapeRenderer.end();

        spriteBatch.begin();
        //Message
        font.draw(spriteBatch, this.name,
                pos.x + 5, pos.y + notificationLayout.height + 5,
                size.x - 5, Align.center, true);

        spriteBatch.end();
    }
}
