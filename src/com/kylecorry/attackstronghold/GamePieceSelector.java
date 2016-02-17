/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.attackstronghold;

import com.kylecorry.attackstronghold.GamePanel.CurrentSprite;
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
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author kyle
 */
public class GamePieceSelector extends JPanel {

    public static final int WIDTH = 200;
    public static final int HEIGHT = 280;

    private GameThread gt;
    private Mode mode;
    private CurrentSprite currentSpriteType;
    private GamePanel mainGame;

    private List<Sprite> sprites;

    private Random random;

    private static enum Mode {

        HOME,
        PLAYING,
        PAUSED,
        START
    };

    public GamePieceSelector(GamePanel mainGame) {
        setFocusable(true);
        requestFocusInWindow(false);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        currentSpriteType = CurrentSprite.BIN;
        mainGame.setCurrentSpriteType(currentSpriteType);
        this.mainGame = mainGame;
        sprites = new ArrayList<>();
        sprites.add(new Bin(60, 0));
        sprites.add(new Tote(60, 100));
        sprites.add(new Catapult(60, 200));
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {

                if (me.getButton() == MouseEvent.BUTTON1) {
                    int y = me.getY();
                    if (y < 90) {
                        currentSpriteType = CurrentSprite.BIN;
                    } else if (y < 190) {
                        currentSpriteType = CurrentSprite.TOTE;
                    } else {
                        currentSpriteType = CurrentSprite.CATAPULT;
                    }
                }
                mainGame.setCurrentSpriteType(currentSpriteType);

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
                    mode = Mode.PLAYING;
                }
                switch (ke.getKeyCode()) {
                    case 49:
                        currentSpriteType = CurrentSprite.TOTE;
                        break;
                    case 50:
                        currentSpriteType = CurrentSprite.BIN;
                        break;
                    case 51:
                        currentSpriteType = CurrentSprite.CATAPULT;
                        break;
                }
                mainGame.setCurrentSpriteType(currentSpriteType);
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
        switch (currentSpriteType) {
            case BIN:
                g.fillRect(0, 0, WIDTH, 90);
                break;
            case TOTE:
                g.fillRect(0, 90, WIDTH, 100);
                break;
            case CATAPULT:
                g.fillRect(0, 190, WIDTH, 90);
                break;
        }
        for (Sprite s : sprites) {
            s.draw(g);
        }
    }

}
