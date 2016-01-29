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
public class Bin implements Sprite {

    public static final int WIDTH = GamePanel.TILE_WIDTH;
    public static final int HEIGHT = GamePanel.TILE_HEIGHT;
    public static final int TOP_HEALTH = 300;
    private final int x;
    private final int y;
    private final BufferedImage image;
    private int health;

    public Bin(int x, int y) {
        image = ImageLoader.getBin();
        health = TOP_HEALTH;
        this.x = x;
        this.y = y;
    }
    
    public GamePanel.SpriteType getType(){
        return GamePanel.SpriteType.BIN;
    }

    public void collision(Sprite s) {
        health--;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void update() {
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
