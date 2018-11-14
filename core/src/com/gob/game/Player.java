
package com.gob.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import static com.gob.game.GamePlane.SCREEN_HEIGHT;

public class Player extends Rectangle {
    
    private Sound soundFly;
    private Texture texture;
    public float fly;
    public boolean canFly = true;
    public Player(Texture texture){
        this.texture = texture;
        this.height = texture.getHeight();
        this.width = texture.getWidth();
//        soundFly = Gdx.audio.newSound(Gdx.files.internal("soundPlayer.ogg"));
    } 
    

    public void draw(SpriteBatch batch){
        batch.draw(texture, x , y);
        
    }
    
    public void fly(){
        if(canFly ){
            fly += 100;
//          canFly = false;
//          soundFly.play();
         }
    }
        
}

