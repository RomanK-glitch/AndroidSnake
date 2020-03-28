package com.roman.androidsnake;

import java.util.Random;

public class FoodCreator {
    int mapWidth;
    int mapHeight;

    Random random = new Random();

    public FoodCreator(int mapWidth, int mapHeight){
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    private static int GetRandomNumberInRange(int min, int max){
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public Point CreateFood(){
        int rangeX = mapWidth/20;
        int rangeY = mapHeight/20;
        int x = GetRandomNumberInRange(1, rangeX - 4);
        int y = GetRandomNumberInRange(1, rangeY - 4);
        int pointX = x * 20;
        int pointY = y * 20;
        Point p = new Point(pointX, pointY);
        return p;
    }
}
