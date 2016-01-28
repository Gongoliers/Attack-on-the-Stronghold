/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.gpvr;

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
        JFrame frame = new JFrame("Game Pieces vs Robots");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        GamePanel gp = new GamePanel();
        frame.add(gp);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
    
}
