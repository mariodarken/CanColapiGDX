package alex.mario.game;

import alex.mario.game.GUI.*;
import alex.mario.game.LOGIC.TriggersSystem_L;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {

	private MapSystem mapSystem;
	private Player player;
	private CameraSystem cameraSystem;
	private TriggersSystem triggersSystem;
	private NotificationsSystem notificationsSystem;

	private BitmapFont mainFont;

	//Directions
	public static final Vector2 DIRECTION_UP = new Vector2(0, 1);
	public static final Vector2 DIRECTION_LEFT = new Vector2(-1, 0);
	public static final Vector2 DIRECTION_RIGHT = new Vector2(1, 0);
	public static final Vector2 DIRECTION_DOWN = new Vector2(0, -1);

	@Override
	public void create () {
		//Creamos toda la pesca
	    cameraSystem = new CameraSystem(this);
        mapSystem = new MapSystem(this);

		//Cargamos el mapa
		this.loadMap("MapaTest");

		player = new Player(this);

		this.mainFont = new BitmapFont();
		this.notificationsSystem = new NotificationsSystem(this, this.mainFont);

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		//Bucle principal

		//Update todas las variables, movimiento, etc
		this.update();

		//Dibujamos
		this.draw();
	}
	private void update(){
		//Actualizamos al jugador
		player.update();

		this.notificationsSystem.update();

		//Actualizamos la cámara
		this.cameraSystem.update();
	}
	private void draw(){
		//Dibujamos el fondo del mapa
		mapSystem.DrawBackground();

		//Dibujamos al jugador
		player.draw();

		//Dibujamos la parte "superior"
		mapSystem.DrawForeground();

		//Sistema de notificaciones
		this.notificationsSystem.draw();

		//Hacemos que la cámara se actualice
		cameraSystem.draw();
	}
	public void loadMap(String name){
        mapSystem.loadMap(name);
		triggersSystem = new TriggersSystem(this);
    }

	@Override
	public boolean keyDown(int keycode) {
		Vector2 newDirection = player.getDir();

		if(keycode == Input.Keys.LEFT)
        	newDirection.add(DIRECTION_LEFT);
        else if(keycode == Input.Keys.RIGHT)
        	newDirection.add(DIRECTION_RIGHT);
        else if(keycode == Input.Keys.UP)
        	newDirection.add(DIRECTION_UP);
        else if(keycode == Input.Keys.DOWN)
        	newDirection.add(DIRECTION_DOWN);
        this.player.setDir(newDirection);

		// Zoom
        if(keycode == Input.Keys.Z) cameraSystem.proportionalZoom(-0.5f);
        if(keycode == Input.Keys.X) cameraSystem.proportionalZoom(0.5f);

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
        //player.setDir(new Vector2(0,0));
		Vector2 newDirection = player.getDir();

		if(keycode == Input.Keys.LEFT)
			newDirection.sub(DIRECTION_LEFT);
		else if(keycode == Input.Keys.RIGHT)
			newDirection.sub(DIRECTION_RIGHT);
		else if(keycode == Input.Keys.UP)
			newDirection.sub(DIRECTION_UP);
		else if(keycode == Input.Keys.DOWN)
			newDirection.sub(DIRECTION_DOWN);

		this.player.setDir(newDirection);
		/*if(keycode == Input.Keys.NUM_1)
			tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
		if(keycode == Input.Keys.NUM_2)
			tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());*/
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		this.notificationsSystem.addNotification("Hola que ase tu");
        return false;
    }

    @Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	//GETTERS
	public Player getPlayer(){ return player; }
	public MapSystem getMapSystem() {return mapSystem;}
	public CameraSystem getCameraSystem() {return cameraSystem;}
	public TriggersSystem getTriggersSystem() {return triggersSystem;}
}