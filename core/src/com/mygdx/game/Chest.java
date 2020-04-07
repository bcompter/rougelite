package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A treasure chest
 */
public class Chest extends AbstractEntity {
    
    /**
     * Dungeon coordinates
     */
    int xCoord = 1;
    int yCoord = 1;
    
    /**
     * Make a new chest
     */
    public Chest()
    {
        super();
    }
    
    /**
     * Open this chest and leave a treasure behind
     */
    public void Open()
    {
        // Open chest
        SetActiveSprite(1);
        System.out.println("Chest Opened!");
        
        // Spawn treasure, but what kind?
        
    }
    
    /**
     * Render this entity to the screen
     */
    @Override
    public void Render(SpriteBatch batch)
    {
        xPosition = (Game.TILE_SIZE/2) + xCoord * Game.TILE_SIZE;
        yPosition = (Game.TILE_SIZE/2) + yCoord * Game.TILE_SIZE;
        
        sprites.get(activeSprite).setSize(Game.TILE_SIZE, Game.TILE_SIZE);
        sprites.get(activeSprite).setColor(red, green, blue, alpha);
        sprites.get(activeSprite).setCenter(xPosition, yPosition);
        sprites.get(activeSprite).draw(batch);
        
    }  // end Render
    
}  // end class
