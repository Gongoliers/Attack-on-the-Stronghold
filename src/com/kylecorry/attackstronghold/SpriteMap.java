/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.attackstronghold;

import com.kylecorry.spritetemplates.Sprite;

/**
 *
 * @author kylecorry16
 */
public class SpriteMap {
    private final Sprite[][] sprites;
    private final int rows;
    private final int cols;
    
    public SpriteMap(int rows, int cols){
        sprites = new Sprite[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }
    
    public Sprite getSprite(int row, int col){
        return sprites[row][col];
    }
    
    public void putSprite(int row, int col, Sprite sprite){
        sprites[row][col] = sprite;
    }
    
    public void removeSprite(int row, int col){
        sprites[row][col] = null;
    }
    
    public boolean isOccupied(int row, int col){
        return sprites[row][col] != null;
    }
    
    public int getNumberOfRows(){
        return rows;
    }
    
    public int getNumberOfCols(){
        return cols;
    }
}
