package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;

/**
 * Creates monsters
 */
public class MonsterFactory {
    
    /**
     * Monster sprite arrays
     */
    Array <Sprite> mSprites1 = new Array();
    Array <Sprite> mSprites2 = new Array();
    Array <Weapon> mWeapons = new Array();
    
    Texture tex;
    
    Game game;
    
    /**
     * Create a new factory
     */
    public MonsterFactory(Texture t, Game g)
    {
        tex = t;
        game = g;
        
        // Load sprite data
        mSprites1.add(new Sprite(tex, 166, 0, 11, 12));
        mSprites1.add(new Sprite(tex, 155, 0, 11, 12));
        mSprites1.add(new Sprite(tex, 122, 0, 11, 12));
        mSprites1.add(new Sprite(tex, 144, 0, 11, 12));
        mSprites1.add(new Sprite(tex, 56, 0, 11, 12));
        mSprites1.add(new Sprite(tex, 78, 0, 11, 12));
        mSprites1.add(new Sprite(tex, 133, 0, 11, 12));
        mSprites1.add(new Sprite(tex, 89, 0, 11, 12));
        
        mSprites2.add(new Sprite(tex, 166, 12, 11, 12));
        mSprites2.add(new Sprite(tex, 155, 12, 11, 12));
        mSprites2.add(new Sprite(tex, 122, 12, 11, 12));
        mSprites2.add(new Sprite(tex, 144, 12, 11, 12));
        mSprites2.add(new Sprite(tex, 56, 12, 11, 12));
        mSprites2.add(new Sprite(tex, 78, 12, 11, 12));
        mSprites2.add(new Sprite(tex, 133, 12, 11, 12));
        mSprites2.add(new Sprite(tex, 89, 12, 11, 12));
        
        // Create weapons
        mWeapons.add(new Weapon("Spear",            2,1,1));
        mWeapons.add(new Weapon("Bite",             3,1,1));
        mWeapons.add(new Weapon("Claw",             3,1,1));
        mWeapons.add(new Weapon("Sword",            2,2,1));
        mWeapons.add(new Weapon("Axe",              3,3,1));
        mWeapons.add(new Weapon("Daggar",           1,1,1));
        mWeapons.add(new Weapon("Magic Missile",    3,1,6));
        mWeapons.add(new Weapon("Fire Bolt",        3,4,10));
    }
    
    /**
     * Get a random new monster with stats given the current level
     */
    public Monster GetNewMonster(int level)
    {
        int index = MathUtils.random(0, mSprites1.size - 1);
        
        Monster m = new Monster(game.theDungeon);
        m.AddSprite(mSprites1.get(index));
        m.AddSprite(mSprites2.get(index));
        
        switch (index)
        {
            case 0:
                // Bat
                m.AS                = 4;
                m.maxActionPoints   = 6;
                m.defense           = 4;
                m.armor             = 0;
                m.health            = 2;
                m.SetWeapon(GetWeaponByName("Bite"));
                break;
            case 1:
                // Slime
                m.AS                = 4;
                m.maxActionPoints   = 6;
                m.defense           = 4;
                m.armor             = 0;
                m.health            = 2;
                m.SetWeapon(GetWeaponByName("Bite"));
                break;
            case 2:
                // Goblin
                m.AS                = 4;
                m.maxActionPoints   = 6;
                m.defense           = 4;
                m.armor             = 0;
                m.health            = 2;
                m.SetWeapon(GetWeaponByName("Daggar"));
                break;
            case 3:
                // Ghost
                m.AS                = 4;
                m.maxActionPoints   = 6;
                m.defense           = 4;
                m.armor             = 0;
                m.health            = 2;
                m.SetWeapon(GetWeaponByName("Bite"));
                break;
            case 4:
                // Orc
                m.AS                = 4;
                m.maxActionPoints   = 6;
                m.defense           = 4;
                m.armor             = 0;
                m.health            = 2;
                m.SetWeapon(GetWeaponByName("Sword"));
                break;
            case 5:
                // Skeleton
                m.AS                = 4;
                m.maxActionPoints   = 6;
                m.defense           = 4;
                m.armor             = 0;
                m.health            = 2;
                m.SetWeapon(GetWeaponByName("Spear"));
                break;
            case 6:
                // Red Orc
                m.AS                = 4;
                m.maxActionPoints   = 6;
                m.defense           = 4;
                m.armor             = 0;
                m.health            = 2;
                m.SetWeapon(GetWeaponByName("Sword"));
                break;
            case 7:
                // Skeleton Mage
                m.AS                = 4;
                m.maxActionPoints   = 6;
                m.defense           = 4;
                m.armor             = 0;
                m.health            = 2;
                m.SetWeapon(GetWeaponByName("Magic Missile"));
                break;
        }
        
        return m;
    }  // end GetNewMonster
    
    /**
     * Get weapon by name from our weapon list
     */
    public Weapon GetWeaponByName(String n)
    {
        Weapon retval = new Weapon();
        
        for (Weapon w : mWeapons)
        {
            if (w.name.equalsIgnoreCase(n))
            {
                retval = w;
                break;
            }
        }
        
        return retval;
    }
}
