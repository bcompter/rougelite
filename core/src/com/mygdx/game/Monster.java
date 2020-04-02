package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 *
 */
public class Monster extends AbstractEntity implements Attackable{
    
    Dungeon myDungeon;
    
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
    boolean isDying = false;
    
    /**
     * Stats
     */
    int health = 3;
    int AS = 2;
    int actionPoints = 4;
    int maxActionPoints = 4;
    int defense = 5;
    int armor = 0;
    Weapon myWeapon = new Weapon();
    
    /**
     * Create a new blank monster
     */
    public Monster(Dungeon d)
    {
        super();
        myDungeon = d;
    }
    
    /**
     * Perform actions until 
     * @return true if I acted, false if I had nothing to do
     */
    public boolean Act()
    {
        // Do we have anything to do?
        if (actionPoints > 0)
        {
            System.out.println("Monster acting...");
            // If we are not in range of the player, move closer...
            Player p = myDungeon.theGame.thePlayer;
            if (Game.DistanceBetween(p.xCoord, p.yCoord, xCoord, yCoord) > myWeapon.range)
            {
                int distX = Math.abs(p.xCoord-xCoord);
                int distY = Math.abs(p.yCoord-yCoord);
                if ((distX) == (distY))
                {
                    // Randomly move
                    actionPoints--;
                    int dieFlip = MathUtils.random(1,2);
                    if (dieFlip == 1)
                    {
                        if (xCoord < p.xCoord)
                        {
                            if (myDungeon.CanMove(xCoord + 1, yCoord))
                            {
                                xCoord++;
                            }
                        }
                        else
                        {
                            if (myDungeon.CanMove(xCoord - 1, yCoord))
                            {
                                xCoord--;
                            }
                        }
                    }
                    else
                    {
                        if (yCoord < p.yCoord)
                        {
                            if (myDungeon.CanMove(xCoord, yCoord+1))
                            {
                                yCoord++;
                            }
                        }
                        else
                        {
                            if (myDungeon.CanMove(xCoord, yCoord-1))
                            {
                                yCoord--;
                            }
                        }
                    }
                    
                }
                else if (distX > distY)
                {
                    // Move left or right
                    actionPoints--;
                    if (xCoord < p.xCoord)
                    {
                        if (myDungeon.CanMove(xCoord + 1, yCoord))
                        {
                            xCoord++;
                        }
                    }
                    else
                    {
                        if (myDungeon.CanMove(xCoord - 1, yCoord))
                        {
                            xCoord--;
                        }
                    }                   
                        
                }
                else
                {
                    // Move up or down
                    actionPoints--;
                    if (yCoord < p.yCoord)
                    {
                        if (myDungeon.CanMove(xCoord, yCoord+1))
                        {
                            yCoord++;
                        }
                    }
                    else
                    {
                        if (myDungeon.CanMove(xCoord, yCoord-1))
                        {
                            yCoord--;
                        }
                    }   
                }
            }
            else
            {
                // Otherwise attack the player
                ANIMATE_STEP = 0.10f;
                actTimer = 0.5f;
                p.Attack(AS, myWeapon);
                actionPoints -= myWeapon.speed;
                if (actionPoints < 0) 
                    actionPoints = 0;
            }         

            return true;
        }
        
        return false;
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
     * Update this entity
     */
    @Override
    public void Update(float delta)
    {
        /**
         * Perform animations
         */
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
    
    /**
     * 
     */
    public void RefillActionPoints()
    {
        actionPoints = maxActionPoints;
    }

}  // end class
