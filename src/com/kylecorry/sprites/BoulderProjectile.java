/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.sprites;

import com.kylecorry.attackstronghold.ImageLoader;

/**
 *
 * @author kyle
 */
public class BoulderProjectile extends Projectile {

    public BoulderProjectile(int x, int y) {
        super(x, y);
        image = ImageLoader.getBoulder()[0];
        damage = 30;
    }

}