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
        xPosition = (Game.TILE_SIZE/2) + x * Game.TILE_SIZE;
        yPosition = (Game.TILE_SIZE/2) + y * Game.TILE_SIZE;
        
        sprites.get(activeSprite).setSize(Game.TILE_SIZE, Game.TILE_SIZE);
        sprites.get(activeSprite).setColor(red, green, blue, alpha);
        sprites.get(activeSprite).setCenter(xPosition, yPosition);
        sprites.get(activeSprite).draw(batch);
        
    }  // end Render
}
