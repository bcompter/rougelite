package com.mygdx.game;

/**
 * A weapon
 */
public class Weapon extends abItem {
    
    /**
     * Standard attributes
     */
    String name;
    int speed;
    int damage;
    int range;
    
    /**
     * Create a new weapon with basic stats
     */
    public Weapon()
    {
        name = "None";
        speed = 2;
        damage = 1;
        range = 1;
    }
    
    /**
     * Create a new weapon with given stats
     */
    public Weapon(String n, int spd, int dmg, int rng)
    {
        name    = n;
        speed   = spd;
        damage  = dmg;
        range   = rng;
    }
    
}  // end class
