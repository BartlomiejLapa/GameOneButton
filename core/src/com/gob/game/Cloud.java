package com.gob.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Cloud extends Rectangle {
    
    private Texture texture;
        
    public Cloud(Texture texture){
        this.texture = texture;
        this.height = texture.getHeight();
        this.width = texture.getWidth();
        
    } 
    
    public void draw(SpriteBatch batch){
        batch.draw(texture, x , y);
    }

}
    
