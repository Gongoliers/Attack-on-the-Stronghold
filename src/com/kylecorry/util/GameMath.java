/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.util;

/**
 *
 * @author kyle
 */
public class GameMath {

    public static int roundNearest(int value, int nearest) {
        return (int) roundNearest((double) value, (double) nearest);
    }

    public static double roundNearest(double value, double nearest) {
        return Math.round(value / nearest) * nearest;
    }

    public static double toGrid(double value, double gridSize) {
        return Math.floor(value / gridSize) * gridSize;
    }

    public static int toGrid(int value, int gridSize) {
        return (int) toGrid((double) value, (double) gridSize);
    }

}
