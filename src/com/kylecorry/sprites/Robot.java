/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.sprites;

import com.kylecorry.gpvr.GamePanel;
import com.kylecorry.gpvr.ImageLoader;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

/**
 *
 * @author kyle
 */
public class Robot implements Sprite {

    public static final int WIDTH = GamePanel.TILE_WIDTH;
    public static final int HEIGHT = GamePanel.TILE_HEIGHT;
    public static final int TOP_HEALTH = 100;
    private int x;
    private final int y;
    private final BufferedImage[] images;
    private int health;
    private boolean alive;
    private int keyframe;
    private int counter;
    private int direction;
    private boolean stopped;

    public Robot(int x, int y) {
        images = ImageLoader.getRobot();
        health = TOP_HEALTH;
        this.x = x;
        this.y = y;
        alive = true;
        keyframe = 2;
        counter = 0;
        direction = -1;
        stopped = false;
    }

    public void stop() {
        stopped = true;
    }

    public void move() {
        stopped = false;
    }

    public void damage() {
        health--;
    }

    public void collision(Sprite s) {

    }

    public boolean isAlive() {
        return health > 0 && x + WIDTH > 0;
    }

    public void update() {
        if (counter == 2) {
            if (keyframe == 0) {
                direction = 1;
            } else if (keyframe == 2) {
                direction = -1;
            }
            keyframe += direction;
            counter = 0;
        } else {
            counter += 1;
        }
        if (!stopped) {
            x--;
        }
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
        if (alive) {
            g.drawImage(images[keyframe], x, y, null);
        }
    }
}
