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
    public static final int NUM_ROWS = 5;
    public static final int NUM_COLS = 10;
    public static final int TILE_WIDTH = WIDTH / NUM_COLS;
    public static final int TILE_HEIGHT = HEIGHT / NUM_ROWS;

    public static int houseHealth = 50;

    private GameThread gt;
    private Mode mode;
    private CurrentSprite currentSpriteType;

    private SpriteMap spriteMap;
    private RobotSpawner robotSpawner;

    private ArrayList<Robot> robots, robotRemoveList;
    private ArrayList<ProjectileSprite> projectiles, projectileRemoveList;

    private Random random;

    private static enum Mode {
        END,
        PLAYING,
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
        spriteMap = new SpriteMap(NUM_ROWS, NUM_COLS);
        robotSpawner = new RobotSpawner(NUM_ROWS, 2, WIDTH, TILE_HEIGHT);
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
                        if (!spriteMap.isOccupied(row, col)) {
                            switch (currentSpriteType) {
                                case TOTE:
                                    spriteMap.putSprite(row, col, new Tote(x, y));
                                    break;
                                case BIN:
                                    spriteMap.putSprite(row, col, new Bin(x, y));
                                    break;
                                case CATAPULT:
                                    spriteMap.putSprite(row, col, new Catapult(x, y));
                                    break;
                            }
                        }
                    } else if (me.getButton() == MouseEvent.BUTTON3) {
                        spriteMap.removeSprite(row, col);
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
                if (mode == Mode.END) {
                    mode = Mode.START;
                    houseHealth = 50;
                    spriteMap = new SpriteMap(HEIGHT / TILE_HEIGHT, WIDTH / TILE_WIDTH);
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
//                robotSpawner.generateRandomRobot();
                for (int row = 0; row < spriteMap.getNumberOfRows(); row++) {
                    for (int col = 0; col < spriteMap.getNumberOfCols(); col++) {
                        if (spriteMap.isOccupied(row, col)) {
                            spriteMap.getSprite(row, col).update();
                            spriteMap.getSprite(row, col).draw(g);
                        }
                    }
                }
                for (int row = 0; row < spriteMap.getNumberOfRows(); row++) {
                    for (int col = 0; col < spriteMap.getNumberOfCols(); col++) {
                        if (spriteMap.isOccupied(row, col) && !spriteMap.getSprite(row, col).isAlive()) {
                            spriteMap.removeSprite(row, col);
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
                    for (int s = 0; s < spriteMap.getNumberOfCols(); s++) {
                        if (spriteMap.isOccupied(y, s) && spriteMap.getSprite(y, s).isColliding(robot.getRect())) {
                            spriteMap.getSprite(y, s).collision(robot);
                            robot.collision(spriteMap.getSprite(y, s));
                            robot.stopMoving();
                            hit = true;
                            break;
                        }
                    }
                    if (!hit) {
                        robot.continueMoving();
                    }
                    for (int s = 0; s < Math.min(robot.getColumn(TILE_WIDTH) + 1, spriteMap.getNumberOfRows()); s++) {
                        if (spriteMap.isOccupied(y, s) && spriteMap.getSprite(y, s) instanceof ShooterSprite) {
                            if (((ShooterSprite) spriteMap.getSprite(y, s)).canFire()) {
                                projectiles.add(((ShooterSprite) spriteMap.getSprite(y, s)).fire());
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
                    mode = Mode.END;
                } else {
                    g.drawString("Lives: " + (houseHealth / 5), 10, 20);
                }
                break;
            case END:
                g.setColor(Color.WHITE);
                g.drawString("Game Over", WIDTH / 2 - 20, HEIGHT / 2);
                break;
        }
    }

}
