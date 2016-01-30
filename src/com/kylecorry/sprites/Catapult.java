/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.sprites;

import com.kylecorry.attackstronghold.ImageLoader;
import com.kylecorry.spritetemplates.ProjectileSprite;
import com.kylecorry.spritetemplates.ShooterSprite;
import com.kylecorry.spritetemplates.Sprite;

/**
 *
 * @author kyle
 */
public class Catapult extends ShooterSprite {

    public Catapult(int x, int y) {
        super(x, y, 200, ImageLoader.getBoulder());
    }

    @Override
    public void collision(Sprite s) {
    }

    @Override
    public void update() {
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public ProjectileSprite getProjectile() {
        return new Boulder(getX(), getY());
    }

    @Override
    public long getTimeout() {
        return 1500;
    }
    
}
