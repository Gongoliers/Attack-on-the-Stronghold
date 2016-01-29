/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.attackstronghold;

import com.kylecorry.util.ResourceLoader;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author kyle
 */
public class ImageLoader {

    public static BufferedImage getTote() {
        try {
            return ImageIO.read(ResourceLoader.load("tote.png"));
        } catch (IOException ex) {
            Logger.getLogger(ImageLoader.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        return null;
    }

    public static BufferedImage[] getBoulder() {
        BufferedImage[] images = new BufferedImage[5];
        try {
            images[0] = ImageIO.read(ResourceLoader.load("boulder-1.png"));
            images[1] = ImageIO.read(ResourceLoader.load("boulder-2.png"));
            images[2] = ImageIO.read(ResourceLoader.load("boulder-3.png"));
            images[3] = ImageIO.read(ResourceLoader.load("boulder-4.png"));
            images[4] = ImageIO.read(ResourceLoader.load("boulder-5.png"));
        } catch (IOException ex) {
            Logger.getLogger(ImageLoader.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        return images;
    }
    
    public static BufferedImage[] getRobot() {
        BufferedImage[] images = new BufferedImage[3];
        try {
            images[0] = ImageIO.read(ResourceLoader.load("robot-1.png"));
            images[1] = ImageIO.read(ResourceLoader.load("robot-2.png"));
            images[2] = ImageIO.read(ResourceLoader.load("robot-3.png"));
        } catch (IOException ex) {
            Logger.getLogger(ImageLoader.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        return images;
    }
    
    public static BufferedImage getBin(){
        try {
            return ImageIO.read(ResourceLoader.load("bin.png"));
        } catch (IOException ex) {
            Logger.getLogger(ImageLoader.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        return null;
    }
}
