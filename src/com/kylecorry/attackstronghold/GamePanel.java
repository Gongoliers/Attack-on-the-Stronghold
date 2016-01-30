/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.attackstronghold;

import com.kylecorry.sprites.*;
import com.kylecorry.util.GameMath;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author kyle
 */
public class GamePanel extends JPanel {

    public static final int WIDTH = 850;
    public static final int HEIGHT = 425;
    public static final int TILE_WIDTH = WIDTH / 10;
    public static final int TILE_HEIGHT = HEIGHT / 5;

    private GameThread gt;
    private Mode mode;
    private SpriteType currentSpriteType;

    private Sprite[][] sprites;
    private ArrayList<Robot> robots, robotRemoveList;
    private ArrayList<Projectile> projectiles, projectileRemoveList;

    private Random random;

    private static enum Mode {
        HOME,
        PLAYING,
        PAUSED
    };

    public static enum SpriteType {
        BIN,
        TOTE,
        BOULDER,
        ROBOT,
        PROJECTILE
    };

    public GamePanel() {
        setFocusable(true);
        requestFocusInWindow(false);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mode = Mode.HOME;
        sprites = new Sprite[HEIGHT / TILE_HEIGHT][WIDTH / TILE_WIDTH];
        currentSpriteType = SpriteType.TOTE;
        robots = new ArrayList<>();
        projectiles = new ArrayList<>();
        projectileRemoveList = new ArrayList<>();
        robotRemoveList = new ArrayList<>();
        random = new Random();
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {

                int x = GameMath.toGrid(me.getX(), TILE_WIDTH);
                int y = GameMath.toGrid(me.getY(), TILE_HEIGHT);
                int col = x / TILE_WIDTH;
                int row = y / TILE_HEIGHT;
                if (me.getButton() == MouseEvent.BUTTON1) {
                    if (sprites[row][col] == null) {
                        switch (currentSpriteType) {
                            case TOTE:
                                sprites[row][col] = new Tote(x, y);
                                break;
                            case BIN:
                                sprites[row][col] = new Bin(x, y);
                                break;
                            case BOULDER:
                                sprites[row][col] = new Boulder(x, y);
                                break;
                        }
                    }
                } else if (me.getButton() == MouseEvent.BUTTON3) {
                    sprites[row][col] = null;
                }
            }

            @Override

            public void mousePressed(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        }
        );
        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                switch (ke.getKeyCode()) {
                    case 49:
                        currentSpriteType = SpriteType.TOTE;
                        break;
                    case 50:
                        currentSpriteType = SpriteType.BIN;
                        break;
                    case 51:
                        currentSpriteType = SpriteType.BOULDER;
                        break;

                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {

            }

        });
        start();
    }

    public void start() {
        gt = new GameThread(this);
        gt.start();
    }

    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(24, 107, 16));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);
        for (int i = 0; i < WIDTH / TILE_WIDTH; i++) {
            g.drawLine(i * TILE_WIDTH, 0, i * TILE_WIDTH, HEIGHT);
        }
        for (int i = 0; i < HEIGHT / TILE_HEIGHT; i++) {
            g.drawLine(0, i * TILE_HEIGHT, WIDTH, i * TILE_HEIGHT);
        }
        if (random.nextInt(100) == 1) {
            robots.add(new Robot(WIDTH, random.nextInt(HEIGHT / TILE_HEIGHT) * TILE_HEIGHT));
        }
        for (int row = 0; row < sprites.length; row++) {
            for (int col = 0; col < sprites[row].length; col++) {
                if (sprites[row][col] != null) {
                    sprites[row][col].update();
                    sprites[row][col].draw(g);
                }
            }
        }
        for (int row = 0; row < sprites.length; row++) {
            for (int col = 0; col < sprites[row].length; col++) {
                if (sprites[row][col] != null && !sprites[row][col].isAlive()) {
                    sprites[row][col] = null;
                }
            }
        }
        for (int i = 0; i < robots.size(); i++) {
            Robot robot = robots.get(i);
            robot.update();
            if (!robot.isAlive()) {
                robotRemoveList.add(robot);
                break;
            }
            robot.draw(g);
            int x = GameMath.toGrid(robot.getX(), TILE_WIDTH) / TILE_WIDTH;
            int y = GameMath.toGrid(robot.getY(), TILE_HEIGHT) / TILE_WIDTH;
            if (x >= 0 && sprites[y][x] != null) {
                sprites[y][x].collision(robot);
                robot.stop();
            } else {
                robot.move();
            }
            for (int s = 0; s < x; s++) {
                if (sprites[y][s] != null && (sprites[y][s].getType() == SpriteType.BOULDER || sprites[y][s].getType() == SpriteType.BIN)) {
                    if (((Shooter) sprites[y][s]).canFire()) {
                        projectiles.add(((Shooter) sprites[y][s]).fire());
                    }
                }
            }
        }
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);
            projectile.update();
            if (!projectile.isAlive()) {
                projectileRemoveList.add(projectile);
                break;
            }
            projectile.draw(g);
            int x = GameMath.toGrid(projectile.getX(), TILE_WIDTH) / TILE_WIDTH;
            int y = GameMath.toGrid(projectile.getY(), TILE_HEIGHT) / TILE_WIDTH;
            for (Robot r : robots) {
                if (GameMath.toGrid(r.getX(), TILE_WIDTH) / TILE_WIDTH == x && GameMath.toGrid(r.getY(), TILE_HEIGHT) / TILE_WIDTH == y) {
                    projectile.collision(r);
                }
            }
        }
        for (Projectile p : projectileRemoveList) {
            projectiles.remove(p);
        }
        projectileRemoveList.clear();

        for (Robot r : robotRemoveList) {
            robots.remove(r);
        }
        robotRemoveList.clear();
    }

}
