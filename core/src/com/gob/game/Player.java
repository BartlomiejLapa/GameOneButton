
package com.gob.game;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Rectangle {
    

    private Texture texture, healthTex;
    public float fly;
    public boolean canFly = true;
    public Player(Texture texture, Texture healtTex){
        this.texture = texture;
        this.height = texture.getHeight();
        this.width = texture.getWidth();
        this.healthTex = healtTex;

    } 
    

    public void draw(SpriteBatch batch){
        batch.draw(texture, x , y);
        batch.draw(healthTex, x, y + height+5, width * GamePlane.HEALTH/1000, 2);
        
    }
    
    public void fly(){
        if(canFly ){
            fly += 100;
//          canFly = false;
//          soundFly.play();
         }
    }
    
        public float getX(){
        return x;
    }
    
     public float getY(){
        return y;
    }

        
}

