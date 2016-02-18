/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.sprites;

import com.kylecorry.attackstronghold.ImageLoader;
import com.kylecorry.spritetemplates.ProjectileSprite;
import com.kylecorry.spritetemplates.Robot;
import com.kylecorry.spritetemplates.Sprite;

/**
 *
 * @author kyle
 */
public class Noodle extends ProjectileSprite {

    public Noodle(int x, int y) {
        super(x, y, 10, 0, ImageLoader.getNoodle());
    }

    @Override
    public int getDamage() {
        return 20;
    }

    @Override
    public void collision(Sprite s) {
        if(s instanceof Robot && isAlive()){
            s.damage(getDamage());
            damage(1);
        }
    }
    
}
