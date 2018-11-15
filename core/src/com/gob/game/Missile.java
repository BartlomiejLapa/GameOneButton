package com.gob.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Missile extends Rectangle {
    
    public float randomLvl;
    private Texture texture;
        
    public Missile(Texture texture){
        this.texture = texture;
        this.height = texture.getHeight();
        this.width = texture.getWidth();
        
    } 
       
    public void draw(SpriteBatch batch){
        batch.draw(texture, x , y);
    }
    
    public float getX(){
        return x;
    }
    
    public float getY(){
        return y;
    }
}
