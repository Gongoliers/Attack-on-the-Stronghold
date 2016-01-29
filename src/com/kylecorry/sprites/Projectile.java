/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.sprites;

import com.kylecorry.attackstronghold.GamePanel;
import com.kylecorry.attackstronghold.ImageLoader;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

/**
 *
 * @author kyle
 */
public class Projectile implements Sprite {

    public static final int WIDTH = GamePanel.TILE_WIDTH;
    public static final int HEIGHT = GamePanel.TILE_HEIGHT;
    public static final int TOP_HEALTH = 1;
    private int x;
    private final int y;
    BufferedImage image;
    private int health;
    public int damage = 25;

    public Projectile(int x, int y) {
        image = ImageLoader.getBin();
        health = TOP_HEALTH;
        this.x = x;
        this.y = y;
    }

    public void collision(Sprite s) {
        health = 0;
        ((Robot) s).damage(damage);
    }

    public boolean isAlive() {
        return health > 0 && x < GamePanel.WIDTH;
    }
    
    public GamePanel.SpriteType getType(){
        return GamePanel.SpriteType.PROJECTILE;
    }

    public void update() {
        x+=10;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Polygon getRect() {
        int[] xs = {x, x, x + WIDTH, x + WIDTH};
        int[] ys = {y, y + HEIGHT, y, y + HEIGHT};
        return new Polygon(xs, ys, 4);
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }
}
