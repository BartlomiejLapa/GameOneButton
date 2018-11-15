
package com.gob.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion {
    
    public static final float FRAME_LENGTH = 0.2f;
    public static final int OFFSET = 8;
    public static final int SIZE = 35;
    
    private static Animation animation = null;
    float x,y;
    float statetime;
    
    public boolean remove = false;
    
    public Explosion (float x, float y){
        this.x = x - OFFSET;
        this.y = y - OFFSET;
        statetime = 0;
        
        if (animation == null) 
            animation = new Animation(FRAME_LENGTH, TextureRegion.split(new Texture(GamePlane.pathExplosion), SIZE, SIZE)[0]);     
    }
    
    public void update (float deltatime){
        statetime += deltatime;
        if(animation.isAnimationFinished(statetime)) 
            remove = true;
    }
    
    public void render (SpriteBatch batch){
       batch.draw((TextureRegion) animation.getKeyFrame(statetime), x, y, SIZE, SIZE);
    }
}
