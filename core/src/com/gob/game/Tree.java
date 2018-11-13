/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gob.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Tree extends Rectangle {
    
    private Texture texture;
        
    public Tree(Texture texture){
        this.texture = texture;
        this.height = texture.getHeight();
        this.width = texture.getWidth();
        
    } 
    
    public void draw(SpriteBatch batch){
        batch.draw(texture, x , y);
    }
    

}
