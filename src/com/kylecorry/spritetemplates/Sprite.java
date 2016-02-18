/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.spritetemplates;

import com.kylecorry.attackstronghold.GamePanel;
import com.kylecorry.util.GameMath;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author kyle
 */
public abstract class Sprite {

    BufferedImage images[];
    int height = GamePanel.TILE_HEIGHT, width = GamePanel.TILE_WIDTH;
    int x, y;
    int health;

    private int frame = 0;
    private int direction = 1;
    private int counter;

    public Sprite(int x, int y, int maxHealth, BufferedImage[] images){
        this.x = x;
        this.y = y;
        health = maxHealth;
        this.images = images;
    }
    
    BufferedImage getNextFrame() {
        if (images == null) {
            return null;
        }
        if (images.length == 1) {
            return images[0];
        }
        if (counter == 2) {
            if (frame == images.length - 1) {
                direction = -1;
            } else if (frame == 0) {
                direction = 1;
            }
            frame += direction;
        } else {
            counter += 1;
        }
        return images[frame];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public abstract int getDamage();

    public void damage(int amount) {
        health -= amount;
    }

    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return getHealth() > 0;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isColliding(Rectangle r) {
        return r.intersects(getRect());
    }

    public abstract void collision(Sprite s);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRow(int rowHeight) {
        return GameMath.roundNearest(y, rowHeight) / rowHeight;
    }

    public int getColumn(int columnWidth) {
        return GameMath.roundNearest(x, columnWidth) / columnWidth;
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void draw(Graphics g) {
        if (isAlive()) {
            g.drawImage(getNextFrame(), x, y, null);
        }
    }

    public abstract void update();
}
