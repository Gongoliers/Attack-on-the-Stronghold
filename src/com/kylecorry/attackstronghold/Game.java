/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.attackstronghold;

import javax.swing.JFrame;

/**
 *
 * @author kyle
 */
public class Game {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Attack on the Stronghold 0.0.1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        GamePanel gp = new GamePanel();
        frame.add(gp);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        
        JFrame frame2 = new JFrame("Game Pieces");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setResizable(false);
        GamePieceSelector gp2 = new GamePieceSelector(gp);
        frame2.add(gp2);
        frame2.setVisible(true);
        frame2.pack();
        frame2.setLocationRelativeTo(null);
    }
    
}
