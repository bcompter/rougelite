package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Representation of the player in the game
 */
public class Player extends AbstractEntity{
    
    /**
     * Dungeon coordinates
     */
    int xCoord = 1;
    int yCoord = 1;
    
    final float ANIMATE_STEP = 0.5f;
    float animationTimer = 0;
    
    /**
     * Create a new player
     */
    public Player(Texture t, int x, int y, int w, int h)
    {
        // Call super constructor
        super(t, x, y, w, h);
    }
    
    /**
     * Update this entity
     */
    @Override
    public void Update(float delta)
    {
        if (sprites.size > 1)
        {
            animationTimer += delta;
            if (animationTimer > ANIMATE_STEP)
            {
                animationTimer = 0;
                activeSprite++;
                if (activeSprite >= sprites.size)
                    activeSprite = 0;
            }
        }
    }  // end Update
    
    /**
     * Render this entity to the screen
     */
    @Override
    public void Render(SpriteBatch batch)
    {
        xPosition = 32 + xCoord * 64;
        yPosition = 32 + yCoord * 64;
        
        sprites.get(activeSprite).setSize(64, 64);
        sprites.get(activeSprite).setColor(red, green, blue, alpha);
        sprites.get(activeSprite).setCenter(xPosition, yPosition);
        sprites.get(activeSprite).draw(batch);
        
    }  // end Render
    
    /**
     * Flip this players sprites
     */
    public void FlipSprites()
    {
        for (Sprite s : sprites)
        {
            s.flip(true, false);
        }
    }
    
}
