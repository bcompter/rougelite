package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.InputProcessor;

public class Game extends ApplicationAdapter implements InputProcessor {
    
    // Tile Size
    final static int TILE_SIZE = 64;
    
    // Screen size
    final int SCREEN_HEIGHT = TILE_SIZE * (10+3+2);
    final int SCREEN_WIDTH = TILE_SIZE * (10+2+3);
    
    // Camera
    FitViewport viewPort;
    OrthographicCamera camera;
    
    // Spritebatch for rendering 
    SpriteBatch batch;
    
    /**
     * The player
     */
    Player thePlayer;
    
    /**
     * The Dungeon!
     */
    Dungeon theDungeon;
    
    /**
     * The dungeon
     */
    Array <Tile> theTiles = new Array();
    
    /**
     * Other things
     */
    GenericEntity heart;
    
    /**
     * Timing of monster actions
     */
    float monsterTimer = 0.0f;
    float MONSTER_TIME_PERIOD = 1.0f;    
    
    /**
     * Game state
     */
    enum STATE
    {
        GAME_START,
        GAME_PLAYER_TURN,
        GAME_MONSTER_TURN,
        GAME_OVER
    }
    
    STATE theGameState = STATE.GAME_START;
    
    @Override
    public void create () 
    {
        // Create our spritebatch to handle rendering
        batch = new SpriteBatch();
        
        // Load up some textures and stuff   
        Texture t = new Texture("8x.png");
        thePlayer = new Player(t, 0, 0, 11, 11);
        thePlayer.AddSprite(t, 0, 12, 11, 11);
        thePlayer.xCoord = 1;
        thePlayer.yCoord = 1;
        
        theDungeon = new Dungeon(t, this);
        theDungeon.GenerateNextLevel();
        
        heart = new GenericEntity(t, 80, 56, 5, 4);
        heart.width = 32;
        heart.height = 32;
        
        Tile tile = new Tile();
        tile.AddSprite(t, 56, 85, 8, 8);
        tile.AddSprite(t, 56, 94, 8, 8);
        tile.AddSprite(t, 56, 103, 8, 8);
        tile.AddSprite(t, 65, 85, 8, 8);
        tile.AddSprite(t, 65, 103, 8, 8);
        tile.AddSprite(t, 74, 85, 8, 8);
        tile.AddSprite(t, 74, 94, 8, 8);
        tile.AddSprite(t, 74, 103, 8, 8);
        theTiles.add(tile);

        // Setup our camera for 2D and update
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        
        // Set the input processor
        Gdx.input.setInputProcessor(this);
    }
    
    /**
     * Update the game
     */
    public void Update(float delta)
    {
        switch (theGameState)
        {
            case GAME_START:
                // Advance to player turn
                theGameState = STATE.GAME_PLAYER_TURN;
                break;
            case GAME_PLAYER_TURN:
                if (theDungeon.monsters.size == 0)
                {
                    thePlayer.RefillActionPoints();
                } 
                else if (thePlayer.actionPoints <= 0)
                {
                    theGameState = STATE.GAME_MONSTER_TURN;
                    for (Monster m : theDungeon.monsters)
                    {
                        m.RefillActionPoints();
                    }
                }
                break;
            case GAME_MONSTER_TURN:
                
                // Perform actions until all Monsters are done
                monsterTimer += delta;
                if (monsterTimer > MONSTER_TIME_PERIOD)
                {
                    monsterTimer = 0;
                    boolean areWeDone = true;
                    for (Monster m : theDungeon.monsters)
                    {
                        if (m.Act())
                        {
                            areWeDone = false;
                            break;
                        }
                    }
                    if (areWeDone)
                    {
                        thePlayer.RefillActionPoints();
                        theGameState = STATE.GAME_PLAYER_TURN;
                    }
                }
                break;
            case GAME_OVER:
                /* Dead! */
                break;
        }
    }

    @Override
    public void render () 
    {
        float delta = Gdx.graphics.getDeltaTime();
        
        // Update the camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Detect end of level
        if (theDungeon.IsAtGate(thePlayer))
        {
            theDungeon.GenerateNextLevel();
            thePlayer.xCoord = 1;
            thePlayer.yCoord = 1;
        }
        
        // Update the game
        Update(delta);
        thePlayer.Update(delta);
        theDungeon.Update(delta);       
        
        // Render the scene
        batch.begin();
        
        // The dungeon
        theDungeon.Render(batch);
        
        // The player
        thePlayer.Render(batch);
        
        // Hearts
        heart.xPosition = 70;
        heart.yPosition = 20;
        for (int i = 0; i < thePlayer.health; i++)
        {
            heart.Render(batch);
            heart.xPosition += 50;
        }

        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }
    
    /**
     * Input processing...
     */
    public boolean keyTyped(char character)
    {
        System.out.println("Key pressed! " 
                + (int)character + " position: " + thePlayer.xCoord + ", " + thePlayer.yCoord
                + " Action Points: " + thePlayer.actionPoints);
        
        /**
         * Not allowed to move if it is not the players turn, or they are out of Action Points
         */
        if (theGameState != STATE.GAME_PLAYER_TURN || thePlayer.actionPoints == 0)
            return false;
        
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            if (theDungeon.CanMove(thePlayer.xCoord, thePlayer.yCoord+1))
            {
                thePlayer.actionPoints--;
                thePlayer.yCoord += 1;
            }
            else if (theDungeon.IsMonsterAt(thePlayer.xCoord, thePlayer.yCoord+1))
            {
                theDungeon.GetMonsterAt(thePlayer.xCoord, thePlayer.yCoord+1).Attack(thePlayer.AS, thePlayer.weaponSlot);
                thePlayer.Act();
                thePlayer.actionPoints -= thePlayer.weaponSlot.speed;
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {
            if (theDungeon.CanMove(thePlayer.xCoord, thePlayer.yCoord-1))
            {
                thePlayer.actionPoints--;
                thePlayer.yCoord -= 1;
            }
            else if (theDungeon.IsMonsterAt(thePlayer.xCoord, thePlayer.yCoord-1))
            {
                theDungeon.GetMonsterAt(thePlayer.xCoord, thePlayer.yCoord-1).Attack(thePlayer.AS, thePlayer.weaponSlot);
                thePlayer.Act();
                thePlayer.actionPoints -= thePlayer.weaponSlot.speed;
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            if (!thePlayer.isFacingLeft)
            {
                thePlayer.FlipSprites();
            }
            thePlayer.isFacingLeft = true;
            if (theDungeon.CanMove(thePlayer.xCoord-1, thePlayer.yCoord))
            {
                thePlayer.actionPoints--;
                thePlayer.xCoord -= 1;
            }
            else if (theDungeon.IsMonsterAt(thePlayer.xCoord-1, thePlayer.yCoord))
            {
                theDungeon.GetMonsterAt(thePlayer.xCoord-1, thePlayer.yCoord).Attack(thePlayer.AS, thePlayer.weaponSlot);
                thePlayer.Act();
                thePlayer.actionPoints -= thePlayer.weaponSlot.speed;
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            if (thePlayer.isFacingLeft)
            {
                thePlayer.FlipSprites();
            }
            thePlayer.isFacingLeft = false;
            if (theDungeon.CanMove(thePlayer.xCoord+1, thePlayer.yCoord))
            {
                thePlayer.actionPoints--;
                thePlayer.xCoord += 1;
            }
            else if (theDungeon.IsMonsterAt(thePlayer.xCoord+1, thePlayer.yCoord))
            {
                theDungeon.GetMonsterAt(thePlayer.xCoord+1, thePlayer.yCoord).Attack(thePlayer.AS, thePlayer.weaponSlot);
                thePlayer.Act();
                thePlayer.actionPoints -= thePlayer.weaponSlot.speed;
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            theDungeon.OpenGate();
        }
        
        return true;
    }
    public boolean scrolled(int amount)
    {
        return true;
    }
    public boolean mouseMoved(int screenX,
                   int screenY)
    {
        return true;
    }
    public boolean touchDragged(int screenX,
                     int screenY,
                     int pointer)
    {
        return true;
    }
    public boolean touchUp(int screenX,
                int screenY,
                int pointer,
                int button)
    {
        return true;
    }
    public boolean touchDown(int screenX,
                  int screenY,
                  int pointer,
                  int button)
    {
        return true;
    }
    
    public boolean keyUp(int keycode)
    {
        return true;
    }  
    public boolean keyDown(int keycode)
    {
        return true;
    }
    
    /**
     * What is the distance between two entities?
     * @return The distance in x/y coordinates
     */
    static int DistanceBetween(int x1, int y1, int x2, int y2)
    {
        int distX = Math.abs(x1-x2);
        int distY = Math.abs(y1-y2);
        
        return Math.max(distX, distY);
    }
}
