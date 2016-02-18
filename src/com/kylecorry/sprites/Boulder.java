/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.sprites;

import com.kylecorry.attackstronghold.GamePanel;
import com.kylecorry.attackstronghold.ImageLoader;
import com.kylecorry.spritetemplates.ProjectileSprite;
import com.kylecorry.spritetemplates.Robot;
import com.kylecorry.spritetemplates.Sprite;

/**
 *
 * @author kyle
 */
public class Boulder extends ProjectileSprite {

    public Boulder(int x, int y) {
        super(x, y, 8, 0, ImageLoader.getBoulder());
    }

    @Override
    public int getDamage() {
        return 30;
    }

    @Override
    public void collision(Sprite s) {
        if(s instanceof Robot && isAlive()){
            s.damage(getDamage());
            damage(1);
        }
    }
    
}
