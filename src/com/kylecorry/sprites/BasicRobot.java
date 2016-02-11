/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.sprites;

import com.kylecorry.attackstronghold.ImageLoader;
import com.kylecorry.spritetemplates.Robot;
import com.kylecorry.spritetemplates.Sprite;

/**
 *
 * @author kyle
 */
public class BasicRobot extends Robot {
    
    public BasicRobot(int x, int y) {
        super(x, y, -5, 0, 100, ImageLoader.getBasicRobot());
    }
    
    @Override
    public int getDamage() {
        return 5;
    }
    
    @Override
    public void collision(Sprite s) {
    }
    
}
