package alex.mario.game.LOGIC;

import alex.mario.game.GUI.*;
import alex.mario.game.MyGdxGame;
import alex.mario.game.objects.Key;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Character_L {
    public static final int DEFAULT_VEL = 4;
    protected MapSystem mapSystem;
    protected TriggersSystem triggersSystem;
    protected Map map;
    protected Vector2 position, direction, size;
    protected MyGdxGame game;
    protected int vel = 4;
    protected ArrayList<String> triggeredBy;

    protected int lastDir = 0;
    protected long millis = System.currentTimeMillis();
    protected int step;

    protected InventorySystem inventorySystem;

    protected boolean isPassable = false;
    public boolean isRunning = false;
    public Character_L(MyGdxGame game, Map map){
        this.game = game;
        this.map = map;
        this.mapSystem = this.game.getMapSystem();
        this.triggersSystem = this.game.getTriggersSystem();
        this.inventorySystem = new InventorySystem(this.game);

        this.triggeredBy = new ArrayList<String>();

        this.position = new Vector2(0,0);
        this.direction = new Vector2(0,0);
        this.size = new Vector2(40,32);
    }
    public void update(){

        //Movimiento
        Vector2 newPosition = position.cpy().add(direction.cpy().scl(vel,vel));
        Rectangle playerRectNewPosition = this.getRect(newPosition);

        if(!mapSystem.isCharacterCollidingWithCollisionsLayer(this, playerRectNewPosition)
           && !mapSystem.isCharacterCollidingWithItem(this, playerRectNewPosition)
                ) {
            if(!mapSystem.isCharacterCollidingWithAnyCharacter(this, playerRectNewPosition)){
            if(this.isRunning){this.vel = DEFAULT_VEL * 2;}else{this.vel = DEFAULT_VEL;}
                position.add(direction.cpy().scl(vel, vel)); //Movimiento básico
            }else{
                position.add(direction.cpy().scl(0.5f, 0.5f)); //Movimiento reducido atravesando jugadores
            }
        }

        //Compruebo Triggers
        this.triggersSystem.checkTriggers(this, playerRectNewPosition);

    }
    public boolean trigger(MapProperties mapProperties){
        if(this.triggeredBy.contains(mapProperties.get("id").toString())){
            return false;
        }else{
            this.triggeredBy.add(mapProperties.get("id").toString());
            return true;
        }
    }
    public boolean untrigger(MapProperties mapProperties){
        if(mapProperties.get("justOnce", false, Boolean.class)){return false;}
        if(!this.triggeredBy.contains(mapProperties.get("id").toString())){
            return false;
        }else{
            this.triggeredBy.remove(mapProperties.get("id").toString());
            return true;
        }
    }
    public Rectangle getRect(Vector2 playerPos){
        return new Rectangle(playerPos.x, playerPos.y, this.size.x, this.size.y);
    }
    public Rectangle getRect(){
        return this.getRect(this.position);
    }
    public void setDir(Vector2 newDir){
        this.direction = newDir;
    }
    public void resetPos(){
        position = new Vector2(Gdx.graphics.getWidth()/2 ,Gdx.graphics.getHeight()/2);}
    public void setPos(Vector2 newPos){
        this.position = newPos;
    }
    public Vector2 getDir(){
        return this.direction;
    }
    public Vector2 getPosition(){
        return this.position;
    }

    protected int getDirection(){ //TODO hacer cambios temporales
        if(direction.x < 0){
            lastDir = 1;
            return 1;
        }
        if(direction.x > 0){
            lastDir = 2;
            return 2;
        }
        if(direction.y < 0){
            lastDir = 0;
            return 0;
        }
        if(direction.y > 0){
            lastDir = 3;
            return 3;
        }

        step = 0;
        return lastDir;
    }

    protected int getStep(){
        if(System.currentTimeMillis() > millis+200 && !direction.epsilonEquals(0,0)){
            millis = System.currentTimeMillis();
            if(step == 0) step++;
            step += 2;
            if(step > 3) step = 1;
        }

        return step;
    }
    public Map getMap(){
        return this.map;
    }
    public void setMap(Map map){
        this.map = map;
    }

    public void addItem(Item item){
        this.game.getNotificationsSystem().addNotification("Se ha añadido '" + item.getName() + "' al inventario");
        this.inventorySystem.add(item);
    }
    public ArrayList<Item> getItems(){
        return this.inventorySystem.getItems();
    }
    public InventorySystem getInventorySystem() {
        return inventorySystem;
    }

    public boolean isPassable() {
        return isPassable;
    }

    public Item hasItemType(Class type) {
        for(Item item : this.inventorySystem.items){
            if(item.getClass() == type){
                return item;
            }
        }
        return null;
    }
}
