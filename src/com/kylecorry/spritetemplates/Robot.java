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
public abstract class Robot extends Sprite {

    private int velX, velY;

    private final int originalVelocityX, originalVelocityY;

    public Robot(int x, int y, int velX, int velY, int maxHealth, BufferedImage[] images) {
        super(x, y, maxHealth, images);
        setVelocity(velX, velY);
        originalVelocityX = velX;
        originalVelocityY = velY;
    }

    public final void setVelocity(int x, int y) {
        velX = x;
        velY = y;
    }

    @Override
    public abstract int getDamage();

    @Override
    public abstract void collision(Sprite s);

    @Override
    public void update() {
        move(velX, velY);
    }

    public void stopMoving() {
        setVelocity(0, 0);
    }

    public void continueMoving() {
        setVelocity(originalVelocityX, originalVelocityY);
    }

}
