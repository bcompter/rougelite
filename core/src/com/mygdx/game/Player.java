package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 * Representation of the player in the game
 */
public class Player extends AbstractEntity implements Attackable{
    
    /**
     * Dungeon coordinates
     */
    int xCoord = 1;
    int yCoord = 1;
    
    /**
     * Animation timers
     */
    float ANIMATE_STEP = 0.5f;
    float animationTimer = 0;
    float actTimer = 0;
    
    /**
     * Stats
     */
    int actionPoints = 5;
    int maxActionPoints = 5;
    int health = 10;
    int AS = 2;
    int defense = 7;
    int armor = 0;
            
    /**
     * Equipment
     */
    Weapon weaponSlot = new Weapon();
    
    /**
     * Create a new player
     */
    public Player(Texture t, int x, int y, int w, int h)
    {
        // Call super constructor
        super(t, x, y, w, h);
    }
    
    /**
     * Attack this player
     */
    public void Attack(int AS, Weapon w)
    {
        if ( (MathUtils.random(1,6) + MathUtils.random(1,6) + AS) > defense )
        {
            System.out.println("HIT");
            health -= w.damage;
            if (health < 1)
            {
                /* Player death handled in main game loop */
                System.out.println("Game over man, Game Over!");
            }
        }
        else
        {
            System.out.println("MISS");
        }
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
            if (actTimer > 0)
            {
                actTimer -= delta;
                if (actTimer < 0)
                {
                    actTimer = 0;   
                    ANIMATE_STEP = 0.5f;
                }
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
        System.out.println("acttimer = " + actTimer);
    }
    
    /**
     * Temporarily increase animation speed
     */
    public void Act()
    {
        ANIMATE_STEP = 0.15f;
        actTimer = 1.0f;
    }
    
    /**
     * 
     */
    public void RefillActionPoints()
    {
        actionPoints = maxActionPoints;
    }
    
}
