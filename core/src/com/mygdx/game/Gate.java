package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A gate
 */
public class Gate extends AbstractEntity{
    
    /**
     * Animation timers
     */
    final float ANIMATE_STEP = 0.75f;
    float animationTimer = 0;
    
    boolean isOpen = false;
    boolean isOpening = false;
    
    /**
     * Create a new gate
     */
    public Gate()
    {
        super();
    }
    
    /**
     * Update this entity
     */
    @Override
    public void Update(float delta)
    {
        if (isOpening)
        {
            animationTimer += delta;
            if (animationTimer > ANIMATE_STEP)
            {
                activeSprite++;
                animationTimer = 0;
                if (activeSprite == (sprites.size -1))
                {
                    isOpening = false;
                    isOpen = true;
                }
            }
        }
       
    }  // end Update
    
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
