package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Represents tiles
 */
public class Tile extends AbstractEntity{
    
    public Tile()
    {
        super();
        xPosition = 32;
        yPosition = 32;
    }
    
    public Tile (Texture t, int x, int y, int w, int h)
    {
        // Call super constructor
        super(t, x, y, w, h);
    }
    
    /**
     * Render this entity to the screen
     */
    public void Render(SpriteBatch batch, int x, int y)
    {
        xPosition = 32 + x * 64;
        yPosition = 32 + y * 64;
        
        sprites.get(activeSprite).setSize(64, 64);
        sprites.get(activeSprite).setColor(red, green, blue, alpha);
        sprites.get(activeSprite).setCenter(xPosition, yPosition);
        sprites.get(activeSprite).draw(batch);
        
    }  // end Render
}
