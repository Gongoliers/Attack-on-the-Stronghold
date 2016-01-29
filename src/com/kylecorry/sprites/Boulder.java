/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.sprites;

import com.kylecorry.attackstronghold.GamePanel;
import static com.kylecorry.attackstronghold.GamePanel.SpriteType.BOULDER;
import com.kylecorry.attackstronghold.ImageLoader;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

/**
 *
 * @author kyle
 */
public class Boulder implements Sprite {

    public static final int WIDTH = GamePanel.TILE_WIDTH;
    public static final int HEIGHT = GamePanel.TILE_HEIGHT;
    public static final int TOP_HEALTH = 200;
    private final int x;
    private final int y;
    private final BufferedImage[] images;
    private int health;
    private boolean alive;
    private int keyframe;
    private int counter;
    private int direction;
    public final GamePanel.SpriteType TYPE = BOULDER;
    private long lastFire = 0;
    public static final long FIRE_TIMEOUT = 1500;

    public Boulder(int x, int y) {
        images = ImageLoader.getBoulder();
        health = TOP_HEALTH;
        this.x = x;
        this.y = y;
        alive = true;
        keyframe = 4;
        counter = 0;
        direction = -1;
    }

    public GamePanel.SpriteType getType() {
        return GamePanel.SpriteType.BOULDER;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void collision(Sprite s) {
        health--;
        ((Robot) s).damage();
    }

    public boolean canFire() {
        long currentTime = System.currentTimeMillis();
        boolean canFire = (currentTime - lastFire) >= FIRE_TIMEOUT;
        if (canFire) {
            lastFire = currentTime;
        }
        return canFire;
    }

    public Projectile fire() {
        return new BoulderProjectile(x + WIDTH / 2, y);
    }

    public void update() {
        if (counter == 2) {
            if (keyframe == 0) {
                direction = 1;
            } else if (keyframe == 4) {
                direction = -1;
            }
            keyframe += direction;
            counter = 0;
        } else {
            counter += 1;
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
