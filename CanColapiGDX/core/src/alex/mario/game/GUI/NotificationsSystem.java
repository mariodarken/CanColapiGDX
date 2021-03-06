package alex.mario.game.GUI;

import alex.mario.game.Interfaces.iSystem;
import alex.mario.game.LOGIC.NotificationsSystem_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

public class NotificationsSystem extends NotificationsSystem_L implements iSystem {
    private BitmapFont font;
    public NotificationsSystem(MyGdxGame game){
        super(game);
        this.font = this.game.getNotificationsFont();
        this.shapeRenderer.setAutoShapeType(true);
        font.setColor(Color.WHITE);
    }

    public void draw(){
        int i = 0;
        float lastNotificationHeight = 0;
        shapeRenderer.setProjectionMatrix(this.game.getSpriteBatch().getProjectionMatrix());
        for(Notification notification : this.notifications){
            notification.draw(
                    this.shapeRenderer,
                    this.spriteBatch,
                    this.font,
                    new Vector2(0, lastNotificationHeight),
                    new Vector2(this.game.getCameraSystem().getScreenSize().x, 0));
            lastNotificationHeight += notification.getNotificationHeight();
            i++;
        }
    }
}
