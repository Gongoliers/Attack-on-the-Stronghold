/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.sprites;

import java.awt.Graphics;
import java.awt.Polygon;

/**
 *
 * @author kyle
 */
public interface Sprite {
    public abstract void update();
    public abstract void draw(Graphics g);
    public abstract Polygon getRect();
    public abstract int getX();
    public abstract int getY();
    public abstract boolean isAlive();
    public abstract void collision(Sprite s);
}
