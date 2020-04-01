package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 *
 */
public class Monster extends AbstractEntity implements Attackable{
    
    /**
     * Dungeon coordinates
     */
    int xCoord = 1;
    int yCoord = 1;
    
    /**
     * Animation timers
     */
    final float ANIMATE_STEP = 0.5f;
    float animationTimer = 0;
    boolean isDying = false;
    
    /**
     * Stats
     */
    int health = 3;
    int actionPoints = 4;
    int defense = 5;
    int armor = 0;
    
    /**
     * Create a new blank monster
     */
    public Monster()
    {
        super();
    }
    
    /**
     * Attack this monster
     */
    public void Attack(int AS, Weapon w)
    {
        if ( (MathUtils.random(1,6) + MathUtils.random(1,6) + AS) > defense )
        {
            System.out.println("HIT");
            health -= w.damage;
            if (health < 1)
            {
                isDying = true;
                System.out.println("DEAD!!!");
            }
        }
        else
        {
            System.out.println("MISS");
        }
    }
    
    /**
     * Create a new monster
     */
    public Monster(Texture t, int x, int y, int w, int h)
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
        if (isDying)
        {
            animationTimer += delta;
            if (animationTimer > ANIMATE_STEP && alpha > 0)
            {
                alpha -= 0.03;
                if (alpha < 0.0)
                    alpha = 0.0f;
            }
        }
        else if (sprites.size > 1)
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
        xPosition = (Game.TILE_SIZE/2) + xCoord * Game.TILE_SIZE;
        yPosition = (Game.TILE_SIZE/2) + yCoord * Game.TILE_SIZE;
        
        sprites.get(activeSprite).setSize(Game.TILE_SIZE, Game.TILE_SIZE);
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
    
    /**
     * Is this monster dead?
     */
    public boolean isDead()
    {
        if (isDying && alpha == 0)
        {
            return true;
        }
        
        return false;
    }
    
}
