package alex.mario.game.LOGIC;

import alex.mario.game.Interfaces.iSystem_L;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraSystem_L implements iSystem_L {
    protected MyGdxGame game;
    protected OrthographicCamera camera;
    protected int zoom = 0;
    protected float w = Gdx.graphics.getWidth();
    protected float h = Gdx.graphics.getHeight();
    private static final float SCALE_RATE = 100;

    public CameraSystem_L(MyGdxGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
    }

    void setPosition(Vector2 pos) {
        //Cambio la posición de la cámara al valor introducido más el zoom (Vector3)
        camera.position.set(new Vector3(pos.x, pos.y, this.zoom));
    }

    public void update() {
        //Actualizo la posición de la cámara respecto a la posición del jugador
        this.setPosition(this.game.getPlayer().getPos());
    }

    public void resetPosition() {
        camera.setToOrtho(false, w, h);
    }

    public Vector2 getScreenSize() {
        return new Vector2(this.w, this.h);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void proportionalZoom(float delta) {
        float aspectRatio = w / h;
        camera.viewportWidth += SCALE_RATE * delta;
        camera.viewportHeight += SCALE_RATE / aspectRatio * delta;
    }
}
