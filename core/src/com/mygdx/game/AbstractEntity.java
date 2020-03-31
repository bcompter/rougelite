/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

/**
 * Basic entity
 */
public abstract class AbstractEntity implements iEntity {
    
    // Game reference
    Game game;
    
    // Sprites
    Array <Sprite> sprites = new Array<Sprite>();
    int activeSprite = 0;
    
    /**
     * Color tinting
     */
    float red, green, blue;
    float alpha;
    
    // Position
    float xPosition;
    float yPosition;
    boolean isFacingLeft = false;
    int width, height;
    
    // Health
    float health;
    float hitTimer;
    
    /**
     * Default constructor
     */
    public AbstractEntity()
    {
        red = 1;
        green = 1;
        blue = 1;
        alpha = 1;
        width = Game.TILE_SIZE;
        height = Game.TILE_SIZE;
    }
    
    /**
     * Create a new entity using the supplied texture
     */
    public AbstractEntity(Texture t, int x, int y, int w, int h)
    {
        AddSprite(t, x, y, w, h);
        
        red = 1;
        green = 1;
        blue = 1;
        alpha = 1;
        width = Game.TILE_SIZE;
        height = Game.TILE_SIZE;
    }
    
    /**
     * Render this entity to the screen
     */
    @Override
    public void Render(SpriteBatch batch)
    {
        sprites.get(activeSprite).setSize(width, height);
        sprites.get(activeSprite).setColor(red, green, blue, alpha);
        sprites.get(activeSprite).setCenter(xPosition, yPosition);
        sprites.get(activeSprite).draw(batch);
        
    }  // end Render
    
    /**
     * Update this entity
     */
    @Override
    public void Update(float delta)
    {
        
    }  // end Update
    
    /**
     * Add a sprite to this animation
     */
    public void AddSprite(Texture t, int x, int y, int w, int h)
    {
        sprites.add(new Sprite(t, x, y, w, h));
    }
    
    /**
     * Set the active sprite
     */
    public void SetActiveSprite(int i)
    {
        if (i < sprites.size)
            activeSprite = i;
        else
            activeSprite = 0;
    }
    
}  // end AbstractEntity