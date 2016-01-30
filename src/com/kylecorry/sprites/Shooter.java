/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.sprites;

/**
 *
 * @author kyle
 */
public interface Shooter {

    public abstract long getTimeout();

    public abstract boolean canFire();

    public abstract Projectile fire();

}
