/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.spritetemplates;

import com.kylecorry.attackstronghold.GamePanel;
import java.awt.image.BufferedImage;

/**
 *
 * @author kyle
 */
public abstract class ProjectileSprite extends Sprite {

    private int velX, velY;
    
    public ProjectileSprite(int x, int y, int velX, int velY, BufferedImage[] images){
        super(x, y, 1, images);
        setVelocity(velX, velY);
    }

    public final void setVelocity(int x, int y) {
        velX = x;
        velY = y;
    }
    
     @Override
    public boolean isAlive() {
        boolean goodHealth = super.isAlive();
        return goodHealth && getX() < GamePanel.WIDTH;
    }

    @Override
    public abstract int getDamage();

    @Override
    public abstract void collision(Sprite s);

    @Override
    public void update() {
        move(velX, velY);
    }

}
