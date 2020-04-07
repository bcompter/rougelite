package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 * Represents the dungeon
 */
public class Dungeon {
    
    /* Welcome to the Dungeon! */
    
    /**
     * Reference to the parent game
     */
    Game theGame;
    
    /**
     * Tiles used to render the dungeon
     */
    Tile floors = new Tile();
    Tile walls = new Tile();
    Tile borders = new Tile();
    Gate gate = new Gate();
    Chest chest = new Chest();
    
    /**
     * List of monsters in the dungeon
     */
    Array <Monster> monsters = new Array();
    Array <Monster> removeList = new Array();
    
    /**
     * A factory for monsters
     */
    MonsterFactory theFactory;

    /**
     * The current dungeon level
     */
    int level = -1;
    
    /**
     * Map of the dungeon
     */
    char [][] theMap = new char[12][13];

    /**
     * Texture used to render the dungeon
     */
    Texture tex;
    
    /**
     * Create a new dungeon
     * Keeping it simple for now.
     */
    public Dungeon(Texture t, Game g)
    {
        tex = t;
        theGame = g;
        
        theFactory = new MonsterFactory(tex, theGame);
        
        floors.AddSprite(t, 56, 85, 8, 8);
        floors.AddSprite(t, 56, 94, 8, 8);
        floors.AddSprite(t, 56, 103, 8, 8);
        floors.AddSprite(t, 65, 85, 8, 8);
        floors.AddSprite(t, 65, 103, 8, 8);
        floors.AddSprite(t, 74, 85, 8, 8);
        floors.AddSprite(t, 74, 94, 8, 8);
        floors.AddSprite(t, 74, 103, 8, 8);
        floors.AddSprite(t, 29, 85, 8, 8);
        
        walls.AddSprite(t, 47, 76, 8, 8);
        walls.AddSprite(t, 47, 85, 8, 8);
        walls.AddSprite(t, 47, 94, 8, 8);
        walls.AddSprite(t, 47, 103, 8, 8);

        borders.AddSprite(t, 20, 76, 8, 8); // NW
        borders.AddSprite(t, 29, 76, 8, 8); // N
        borders.AddSprite(t, 38, 76, 8, 8); // NE
        borders.AddSprite(t, 20, 85, 8, 8); // W
        borders.AddSprite(t, 38, 85, 8, 8); // E
        borders.AddSprite(t, 20, 94, 8, 8); // SW
        borders.AddSprite(t, 29, 94, 8, 8); // S
        borders.AddSprite(t, 38, 94, 8, 8); // SE
        
        gate.AddSprite(t, 101, 76, 8, 8);
        gate.AddSprite(t, 110, 76, 8, 8);
        gate.AddSprite(t, 119, 76, 8, 8);
        gate.AddSprite(t, 128, 76, 8, 8);
        
        chest.AddSprite(t, 119, 84, 8, 9);
        chest.AddSprite(t, 110, 84, 8, 9);
    }
    
    /**
     * Generate the next level
     */
    public void GenerateNextLevel()
    {
       level++;
       System.out.println("Generating level " + level);
       gate.Close();

       // Generate dungeon tiles
       for (int y = 0; y < 13; y++)
       {
           for (int x = 0; x < 12; x++)
           {
               if (x == 0 && y == 0)
               {
                   theMap[x][y] = 'Z';
               }
               else if (x == 11 && y == 0)
               {
                   theMap[x][y] = 'C';
               }
               else if (x == 11 && y == 12)
               {
                   theMap[x][y] = 'E';
               }
               else if (x == 0 && y == 12)
               {
                   theMap[x][y] = 'Q';
               }
               else if (y == 12)
               {
                   theMap[x][y] = 'W';                  
               }
               else if ( (y == 11 && x != 0) && (y == 11 && x != 11))
               {
                   int moss = MathUtils.random(1, 3);
                   if (moss == 1)
                       theMap[x][y] = 'M';
                   else
                       theMap[x][y] = 'w';

               }
               else if (x == 0)
               {
                   theMap[x][y] = 'A';
               }
               else if (x == 11)
               {
                   theMap[x][y] = 'D';
               }
               else if (y == 0)
               {
                   theMap[x][y] = 'X';
               }
               else
               {
                   // Floor
                   theMap[x][y] = 'f';
               }
           }  // end for
       } // end for
       
       // Randomly place the gate
       int xGate = MathUtils.random(1, 10);
       theMap[xGate][11] = 'G';
       
       // Randomly place the chest
       int x = 0;
       int y = 0;
       while (!CanMove(x, y))
       {
           x = MathUtils.random(1, 11);
           y = MathUtils.random(1, 12);
           System.out.println("x " + x + " y " + y);
       }
       chest = new Chest();
       chest.AddSprite(tex, 119, 84, 8, 9);
       chest.AddSprite(tex, 110, 84, 8, 9);
       chest.xCoord = x;
       chest.yCoord = y;
       
       // Add one to three monsters
       int numMonsters = MathUtils.random(1, 3);
       System.out.println("Num monsters " + numMonsters);
       for (int i = 0; i < numMonsters; i++)
       {
           x = 0;
           y = 0;
           while (!CanMove(x, y))
           {
               x = MathUtils.random(1, 11);
               y = MathUtils.random(1, 12);
               System.out.println("x " + x + " y " + y);
           }
           Monster m = theFactory.GetNewMonster(level);
           m.xCoord = x;
           m.yCoord = y;
           monsters.add(m);
       }
       
    }  // end GenerateNextLevel
    
    /**
     * Render the dungeon
     * @param batch 
     */
    public void Render(SpriteBatch batch)
    {
        // The Map
        for (int y = 0; y < 13; y++)
        {
           for (int x = 0; x < 12; x++)
           {
               switch (theMap[x][y])
                {
                    case '.':
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
                        floors.SetActiveSprite(8);
                        floors.Render(batch, x, y);
                        break;
                    case 'V':
                        floors.SetActiveSprite(2);
                        floors.Render(batch, x, y);
                        break;
                    case 'v':
                        floors.SetActiveSprite(3);
                        floors.Render(batch, x, y);
                        break;
                    case 'Q':
                        borders.SetActiveSprite(0);
                        borders.Render(batch, x, y);
                        break;
                    case 'W':
                        borders.SetActiveSprite(1);
                        borders.Render(batch, x, y);
                        break;
                    case 'E':
                        borders.SetActiveSprite(2);
                        borders.Render(batch, x, y);
                        break;
                    case 'A':
                        borders.SetActiveSprite(3);
                        borders.Render(batch, x, y);
                        break;
                    case 'D':
                        borders.SetActiveSprite(4);
                        borders.Render(batch, x, y);
                        break;
                    case 'Z':
                        borders.SetActiveSprite(5);
                        borders.Render(batch, x, y);
                        break;
                    case 'X':
                        borders.SetActiveSprite(6);
                        borders.Render(batch, x, y);
                        break;
                    case 'C':
                        borders.SetActiveSprite(7);
                        borders.Render(batch, x, y);
                        break;
                    case 'G':
                        gate.Render(batch, x, y);
                        break;
                }  // end switch
           }  // end for x
        }  // end for y
        
        // Monsters
        for (Monster m : monsters)
        {
            m.Render(batch);
        }
        
        // Chest
        chest.Render(batch);
        
    }  // end Render
    
    /**
     * Update the Dungeon
     * Animate effects as needed...
     */
    public void Update(float delta)
    {
        // Update all the monsters
        for (Monster m : monsters)
        {
            m.Update(delta);
            if (m.isDead())
                removeList.add(m);
        }
        
        // Remove any dead monsters
        for (Monster m : removeList)
        {
            monsters.removeValue(m, true);
        }
        
        // Open the gate if there are no enemies
        if (monsters.size == 0 && !gate.isOpen)
        {
            gate.Open();
        }
        
        // Animate gate
        gate.Update(delta);
    }  // end Update
    
    /**
     * Can we move to the new location?
     */
    public boolean CanMove(int x, int y)
    {
        // Check bounds first
        if (x < 0 || y < 0)
            return false;
        if (x >= 12 || y >= 13)
            return false;
        
        // Search the map for conflicts
        boolean retval = false;
        switch (theMap[x][y])
        {
            case 'F':
            case 'f':
            case 'V':
            case 'v':
                retval = true;
                break;
        
            case 'G':
                if (gate.isOpen)
                    retval = true;
                else
                    retval = false;
                break;
                
            default:
                retval = false;
        }
        
        // Make sure we are not hitting any monsters
        for (Monster m : monsters)
        {
            if (m.xCoord == x && m.yCoord == y)
                retval = false;
        }
        
        // Make sure we are not hitting a chest
        if (chest.xCoord == x && chest.yCoord == y)
        {
            retval = false;
        }
        
        // Can't move onto the player either
        if (theGame.thePlayer.xCoord == x && theGame.thePlayer.yCoord == y)
        {
            retval = false;
        }
        
        return retval;
    }
    
    /**
     * Open the gate
     */
    public void OpenGate()
    {
        gate.isOpening = true;
        System.out.println("Opening the gate!");
    }
    
    /**
     * Is this player at the gate?
     */
    public boolean IsAtGate(Player p)
    {
        if (theMap[p.xCoord][p.yCoord] == 'G' && gate.isOpen)
        {
            return true;
        }
        else 
            return false;
    }
    
    /**
     * Is a monster here? 
     */
    public boolean IsMonsterAt(int x, int y)
    {
        for (Monster m : monsters)
        {
            if (m.xCoord == x && m.yCoord == y)
                return true;
        }
        return false;
    }
    
    /**
     * Is a chest here? 
     */
    public boolean IsChestAt(int x, int y)
    {
        if (chest.xCoord == x && chest.yCoord == y)
            return true;
        else
            return false;
    }
    
    /**
     * Return a monster 
     */
    public Monster GetMonsterAt(int x, int y)
    {
        for (Monster m : monsters)
        {
            if (m.xCoord == x && m.yCoord == y)
                return m;
        }
        return new Monster(this);
    }
    
}  // end Dungeon
