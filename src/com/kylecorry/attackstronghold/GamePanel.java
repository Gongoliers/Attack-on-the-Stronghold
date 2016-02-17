/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.attackstronghold;

import com.kylecorry.sprites.BasicRobot;
import com.kylecorry.sprites.Bin;
import com.kylecorry.sprites.Catapult;
import com.kylecorry.sprites.Tote;
import com.kylecorry.spritetemplates.*;
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

    public static int houseHealth = 50;

    private GameThread gt;
    private Mode mode;
    private CurrentSprite currentSpriteType;

    private Sprite[][] sprites;
    private ArrayList<Robot> robots, robotRemoveList;
    private ArrayList<ProjectileSprite> projectiles, projectileRemoveList;

    private Random random;

    private static enum Mode {

        HOME,
        PLAYING,
        PAUSED,
        START
    };

    public static enum CurrentSprite {

        BIN, TOTE, CATAPULT
    };

    public void setCurrentSpriteType(CurrentSprite sprite) {
        currentSpriteType = sprite;
    }

    public GamePanel() {
        setFocusable(true);
        requestFocusInWindow(false);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mode = Mode.START;
        sprites = new Sprite[HEIGHT / TILE_HEIGHT][WIDTH / TILE_WIDTH];
        currentSpriteType = CurrentSprite.BIN;
        robots = new ArrayList<>();
        projectiles = new ArrayList<>();
        projectileRemoveList = new ArrayList<>();
        robotRemoveList = new ArrayList<>();
        random = new Random();
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (mode == Mode.PLAYING) {
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
                                case CATAPULT:
                                    sprites[row][col] = new Catapult(x, y);
                                    break;
                            }
                        }
                    } else if (me.getButton() == MouseEvent.BUTTON3) {
                        sprites[row][col] = null;
                    }
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
                if (mode == Mode.START) {
                    mode = Mode.PLAYING;
                }
                if (mode == Mode.HOME) {
                    mode = Mode.START;
                    houseHealth = 50;
                    sprites = new Sprite[HEIGHT / TILE_HEIGHT][WIDTH / TILE_WIDTH];
                    robots = new ArrayList<>();
                    projectiles = new ArrayList<>();
                    projectileRemoveList = new ArrayList<>();
                    robotRemoveList = new ArrayList<>();
                    random = new Random();
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
        switch (mode) {
            case START:
                g.setColor(Color.WHITE);
                g.drawString("Attack on the Stronghold", WIDTH / 2 - 20, HEIGHT / 2 - 30);
                g.drawString("Press any key to continue", WIDTH / 2 - 20, HEIGHT / 2 + 30);
                break;
            case PLAYING:
                g.setColor(Color.WHITE);
                for (int i = 0; i < WIDTH / TILE_WIDTH; i++) {
                    g.drawLine(i * TILE_WIDTH, 0, i * TILE_WIDTH, HEIGHT);
                }
                for (int i = 0; i < HEIGHT / TILE_HEIGHT; i++) {
                    g.drawLine(0, i * TILE_HEIGHT, WIDTH, i * TILE_HEIGHT);
                }
                if (random.nextInt(100) <= 2) {
                    robots.add(new BasicRobot(WIDTH, random.nextInt(HEIGHT / TILE_HEIGHT) * TILE_HEIGHT));
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
                    if (robot.getX() < 0) {
                        houseHealth -= robot.getDamage();
                        robotRemoveList.add(robot);
                    }
                    if (!robot.isAlive()) {
                        robotRemoveList.add(robot);
                    }
                    robot.draw(g);
                    int y = robot.getRow(TILE_HEIGHT);
                    boolean hit = false;
                    for (int s = 0; s < sprites[y].length; s++) {
                        if (sprites[y][s] != null && sprites[y][s].isColliding(robot.getRect())) {
                            sprites[y][s].collision(robot);
                            robot.collision(sprites[y][s]);
                            robot.stopMoving();
                            hit = true;
                            break;
                        }
                    }
                    if (!hit) {
                        robot.continueMoving();
                    }
                    for (int s = 0; s < Math.min(robot.getColumn(TILE_WIDTH) + 1, sprites.length); s++) {
                        if (sprites[y][s] != null && sprites[y][s].getType() == SpriteType.SHOOTER) {
                            if (((ShooterSprite) sprites[y][s]).canFire()) {
                                projectiles.add(((ShooterSprite) sprites[y][s]).fire());
                            }
                        }
                    }
                }
                for (int i = 0; i < projectiles.size(); i++) {
                    ProjectileSprite projectile = projectiles.get(i);
                    projectile.update();
                    if (!projectile.isAlive()) {
                        projectileRemoveList.add(projectile);
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
                for (ProjectileSprite p : projectileRemoveList) {
                    projectiles.remove(p);
                }
                projectileRemoveList.clear();

                for (Robot r : robotRemoveList) {
                    robots.remove(r);
                }
                robotRemoveList.clear();

                if (houseHealth <= 0) {
                    mode = Mode.HOME;
                } else {
                    g.drawString("Lives: " + (houseHealth / 5), 10, 20);
                }
                break;
            case HOME:
                g.setColor(Color.WHITE);
                g.drawString("Game Over", WIDTH / 2 - 20, HEIGHT / 2);
                break;
        }
    }

}
