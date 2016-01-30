/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.spritetemplates;

import java.awt.image.BufferedImage;

/**
 *
 * @author kyle
 */
public abstract class ShooterSprite extends Sprite {

    private long lastFire = 0;

    public ShooterSprite(int x, int y, int maxHealth, BufferedImage[] images) {
        super(x, y, maxHealth, images);
    }

    @Override
    public abstract void collision(Sprite s);

    @Override
    public abstract void update();

    @Override
    public abstract int getDamage();
    
    public abstract ProjectileSprite getProjectile();

    public boolean canFire() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastFire) >= getTimeout();
    }

    @Override
    public SpriteType getType() {
        return SpriteType.SHOOTER;
    }

    public ProjectileSprite fire(){
         if (canFire()) {
            lastFire = System.currentTimeMillis();
        }
         return getProjectile();
    }

    public abstract long getTimeout();

}
