package alex.mario.game.LOGIC;

import alex.mario.game.GUI.Item;
import alex.mario.game.MyGdxGame;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

public class TriggersSystem_L {

    protected MyGdxGame game;
    public TriggersSystem_L(MyGdxGame game) {
        this.game = game;

    }
    public void checkTriggers(Character_L character, Rectangle playerRectangle){
        /// BUGG WITH this.tiledMap not updated ///
        // MapLayer TriggerObjectLayer = this.tiledMap.getLayers().get("Triggers");
        //////
        MapLayer TriggerObjectLayer = character.getMap().getTiledMap().getLayers().get("Triggers");
        MapObjects triggers = TriggerObjectLayer.getObjects();

        for (RectangleMapObject rectangleObject : triggers.getByType(RectangleMapObject.class)) {

            Rectangle rectangle = rectangleObject.getRectangle();
            MapProperties mapProperties = rectangleObject.getProperties();
            Iterator<String> keys = mapProperties.getKeys();

            //Se llama a la función trigger para cada llave, incluimos todas las propiedades por si hace falta información
            //extra
            while(keys.hasNext()){
                String key = keys.next();
                this.triggers(
                        character,
                        key,
                        mapProperties.get(key).toString(),
                        Intersector.overlaps(rectangle, playerRectangle),
                        mapProperties//Todas las propiedades del objeto
                );
            }

        }
    }
    public void triggers(Character_L character, String triggerName, String triggerValue, boolean triggered, MapProperties mapProperties){
        switch(triggerName){
            case "sendMessage":
                //Si no es el jugador
                if(character != this.game.getPlayer()){return;}

                if(triggered){
                    if(character.trigger(mapProperties)){
                        this.game.getNotificationsSystem().addNotification(triggerValue);
                        //character.addItem(new Item("Llave magica"));
                    }
                }else{
                    if(character.untrigger(mapProperties)){
                        this.game.getNotificationsSystem().addNotification(triggerValue);
                        //character.addItem(new Item("Llave magnética roja"));
                    }
                }
                break;
            case "playSound":
                if(character != this.game.getPlayer()){return;} //Si no es el jugador

                if(triggered){
                    if(character.trigger(mapProperties)){
                        Boolean isLooped = mapProperties.get("isLooped", false, Boolean.class);

                        if(isLooped) this.game.getSoundSystem().playLoop(triggerValue);
                        else this.game.getSoundSystem().play(triggerValue);
                    }
                }else{
                    if(character.untrigger(mapProperties)){

                    }
                }
                break;
            case "stopAllSounds":
                if(character != this.game.getPlayer()){return;} //Si no es el jugador

                if(triggered){
                    if(character.trigger(mapProperties)){
                        this.game.getSoundSystem().stopAll();
                    }
                }else{
                    if(character.untrigger(mapProperties)){

                    }
                }
                break;
            case "changeMap":

                if(triggered){
                    this.game.getMapSystem().loadMap(MyGdxGame.formatToFilePath(triggerValue));
                    character.setMap(this.game.getMapSystem().getMap(MyGdxGame.formatToFilePath(triggerValue)));
                    //character.resetPos();

                    //Get value of key "linkTo":
                    String linkTo = mapProperties.get("linkTo").toString();
                    character.setPos(this.game.getMapSystem().getEntryPos(character.getMap(), linkTo));
                }
                break;
            case "cameraZoom":
                //Si no es el jugador
                if(character != this.game.getPlayer()){return;}

                if(triggered){ //Zoom
                    if(character.trigger(mapProperties)) {
                        this.game.getCameraSystem().proportionalZoom(Float.parseFloat(triggerValue));
                    }
                }else { //DesZoom
                    if (character.untrigger(mapProperties)) {
                        this.game.getCameraSystem().proportionalZoom(-Float.parseFloat(triggerValue));
                    }
                }
                break;
        }
    }
}
