/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.sprites;

import com.kylecorry.attackstronghold.ImageLoader;
import com.kylecorry.spritetemplates.Sprite;
import com.kylecorry.spritetemplates.SpriteType;

/**
 *
 * @author kyle
 */
public class Tote extends Sprite {

    public Tote(int x, int y) {
        super(x, y, 1000, ImageLoader.getTote());
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public void collision(Sprite s) {
        if(s.getType() == SpriteType.ROBOT){
            damage(s.getDamage());
        }
    }

    @Override
    public void update() {
    }
    
}
