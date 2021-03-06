/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.attackstronghold;

import javax.swing.JPanel;


/**
 *
 * @author kyle
 */
public class GameThread extends Thread{
    public static final int FPS = 30;
    private JPanel gp;
    private boolean running;
    
    public void pause(){
        running = false;
    }
    
    public GameThread(JPanel gp){
        this.gp = gp;
        running = true;
    }

    @Override
    public void run() {
        while(running){
            gp.repaint();
            try {
                Thread.sleep(1000 / FPS);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
