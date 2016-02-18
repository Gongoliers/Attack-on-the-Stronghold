/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.attackstronghold;

import com.kylecorry.sprites.BasicRobot;
import com.kylecorry.spritetemplates.Robot;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author kylecorry16
 */
public class RobotSpawner {
    private List<Robot> robots;
    private Random random;
    private final int rows;
    private int spawnPercent;
    private final int gameWidth;
    private final int tileHeight;
    
    public RobotSpawner(int numRows, int spawnPercent, int gameWidth, int tileHeight){
        robots = new ArrayList<>();
        random = new Random();
        rows = numRows;
        setSpawnPercent(spawnPercent);
        this.gameWidth = gameWidth;
        this.tileHeight = tileHeight;
    }
    
    public void setSpawnPercent(int percent){
        spawnPercent = percent;
    }
    
    private boolean shouldGenerateRobot(){
        return random.nextInt(100) <= spawnPercent;
    }
    
    public void putRobot(Robot robot){
        robots.add(robot);
    }
    
    public void spawnRobot(){
        if(shouldGenerateRobot())
            putRobot(new BasicRobot(gameWidth, selectRandomRow() * tileHeight));
    }
    
    private int selectRandomRow(){
        return random.nextInt(rows);
    }
}
