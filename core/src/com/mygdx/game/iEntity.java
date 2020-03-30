/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Entity interface
 */
public interface iEntity {
    
    /**
     * Render this entity to the screen
     */
    public void Render(SpriteBatch batch);
    
    /**
     * Update this entity
     */
    public void Update(float delta);
    
}  // end iEntity
