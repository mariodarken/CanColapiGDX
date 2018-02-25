package alex.mario.game;

import alex.mario.game.GUI.*;
import alex.mario.game.GUI.Character;
import alex.mario.game.characters.Dog;
import alex.mario.game.characters.Player;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {

	private MapSystem mapSystem;
	private Player player;
	private CameraSystem cameraSystem;
	private TriggersSystem triggersSystem;
	private NotificationsSystem notificationsSystem;

	private BitmapFont notificationsFont;
	private BitmapFont inventoryFont;
	private SpriteBatch spriteBatch;
	private ShapeRenderer shapeRenderer;

	private ArrayList<Character> characters;

	private Character dog;

	//Directions
	public static final Vector2 DIRECTION_UP = new Vector2(0, 1);
	public static final Vector2 DIRECTION_LEFT = new Vector2(-1, 0);
	public static final Vector2 DIRECTION_RIGHT = new Vector2(1, 0);
	public static final Vector2 DIRECTION_DOWN = new Vector2(0, -1);
	private Vector2[] directions = new Vector2[]{DIRECTION_LEFT, DIRECTION_DOWN, DIRECTION_RIGHT, DIRECTION_UP};

	@Override
	public void create () {
		this.spriteBatch = new SpriteBatch();

		this.notificationsFont = new BitmapFont();

		this.inventoryFont = new BitmapFont();
		this.inventoryFont.getData().setScale(0.9f);

		this.shapeRenderer = new ShapeRenderer();

		//Creamos toda la pesca
	    this.cameraSystem = new CameraSystem(this);
        this.mapSystem = new MapSystem(this);
		this.triggersSystem = new TriggersSystem(this);

		//Cargamos el mapa
		this.mapSystem.loadMap(formatToFilePath("mapaTest"));
		//this.loadMap("Planta1");

		player = new Player(this, this.mapSystem.getMap(formatToFilePath("mapaTest")));
		player.setPos(this.mapSystem.getEntryPos(player.getMap(), "start"));

		this.characters = new ArrayList<Character>();

		this.notificationsSystem = new NotificationsSystem(this);
		Gdx.input.setInputProcessor(this);
		System.out.println("ALL LOADED OK");
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

		for(Character character : this.characters){
			character.update();
		}
	}
	private void draw(){
		//Dibujamos el fondo del mapa
		this.player.getMap().DrawBackground(this.cameraSystem);

		//Dibujamos al jugador
		player.draw();

		for(Character character : this.characters){
			if(character.getMap() == this.player.getMap()){
				character.draw();
			}
		}

		//Dibujamos la parte "superior"
		this.player.getMap().DrawForeground();

		//Sistema de notificaciones
		this.notificationsSystem.draw();

		this.player.getInventorySystem().draw();

		//Hacemos que la cámara se actualice
		cameraSystem.draw();
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
		else if(keycode == Input.Keys.I)
			this.player.getInventorySystem().isVisible = !this.player.getInventorySystem().getIsVisible();
        this.player.setDir(newDirection);

		if(this.dog == null){return false;}
		newDirection = this.dog.getDir();
		if(keycode == Input.Keys.A)
			newDirection.add(DIRECTION_LEFT);
		else if(keycode == Input.Keys.D)
			newDirection.add(DIRECTION_RIGHT);
		else if(keycode == Input.Keys.W)
			newDirection.add(DIRECTION_UP);
		else if(keycode == Input.Keys.S)
			newDirection.add(DIRECTION_DOWN);
		this.dog.setDir(newDirection);


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

		if(this.dog == null){return false;}
		newDirection = this.dog.getDir();
		if(keycode == Input.Keys.A)
			newDirection.sub(DIRECTION_LEFT);
		else if(keycode == Input.Keys.D)
			newDirection.sub(DIRECTION_RIGHT);
		else if(keycode == Input.Keys.W)
			newDirection.sub(DIRECTION_UP);
		else if(keycode == Input.Keys.S)
			newDirection.sub(DIRECTION_DOWN);

		this.dog.setDir(newDirection);
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
		this.notificationsSystem.addNotification("xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd xd ");
		this.dog = new Dog(this, this.player.getMap());
		this.characters.add(this.dog);
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
	public NotificationsSystem getNotificationsSystem() {
		return notificationsSystem;
	}

	public Vector2 getRandomDirection(){
		return this.directions[new Random().nextInt(this.directions.length)];
	}

	public BitmapFont getInventoryFont() {
		return inventoryFont;
	}

	public ArrayList<Character> getCharacters() {
		return this.characters;
	}

	public static String formatToFilePath(String mapName){
		return "maps" + File.separator + mapName + ".tmx";
	}

	public BitmapFont getNotificationsFont() {
		return notificationsFont;
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public ShapeRenderer getShapeRenderer() {
		return shapeRenderer;
	}
}