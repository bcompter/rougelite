package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * Represents the dungeon
 */
public class Dungeon {
    
    /* Welcome to the Dungeon! */
    
    Tile floors = new Tile();
    Tile walls = new Tile();
    Tile borders = new Tile();
    
    String map =    
            "FFfffFFffF\n" +
            "FfffFFffFF\n" +
            "fffFffFffF\n" +
            "ffFffFffFf\n" +
            "FFfffFFffF\n" +
            "FfffFFffFF\n" +
            "FfFFffFffF\n" +
            "fFFffFffFf\n" +
            "WwwMMwwMWw\n";
    
    /**
     * Create a new dungeon
     * Keeping it simple for now.
     */
    public Dungeon(Texture t)
    {
        floors.AddSprite(t, 56, 85, 8, 8);
        floors.AddSprite(t, 56, 94, 8, 8);
        floors.AddSprite(t, 56, 103, 8, 8);
        floors.AddSprite(t, 65, 85, 8, 8);
        floors.AddSprite(t, 65, 103, 8, 8);
        floors.AddSprite(t, 74, 85, 8, 8);
        floors.AddSprite(t, 74, 94, 8, 8);
        floors.AddSprite(t, 74, 103, 8, 8);
        
        walls.AddSprite(t, 47, 76, 8, 8);
        walls.AddSprite(t, 47, 85, 8, 8);
        walls.AddSprite(t, 47, 94, 8, 8);
        walls.AddSprite(t, 47, 103, 8, 8);

    }
    
    /**
     * Render the dungeon
     * @param batch 
     */
    public void Render(SpriteBatch batch)
    {
        int x = 1;
        int y = 0;
        
        for (int i = 0; i < map.length(); i++)
        {
            switch (map.charAt(i))
            {
                case '\n':
                    x = 0;
                    y++;
                    break;
                case 'W':
                    walls.SetActiveSprite(0);
                    walls.Render(batch, x, y);
                    break;
                case 'w':
                    walls.SetActiveSprite(0);
                    walls.Render(batch, x, y);
                    break;
                case 'M':
                    walls.SetActiveSprite(3);
                    walls.Render(batch, x, y);
                    break;  
                case 'F':
                    floors.SetActiveSprite(2);
                    floors.Render(batch, x, y);
                    break;
                case 'f':
                    floors.SetActiveSprite(3);
                    floors.Render(batch, x, y);
                    break;
            }
            x++;
        }
    }
    
    /**
     * Update the Dungeon
     * Animate effects if needed...
     */
    public void Update(float delta)
    {
        
    }  // end Update
}  // end Dungeon
