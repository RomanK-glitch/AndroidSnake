package com.roman.androidsnake;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class Figure {
    protected ArrayList<Point> pList;

    public boolean IsHit(Figure figure){
        for (Point p : pList) {
            if (figure.IsHit(p)){
                return true;
            }
        }
        return false;
    }

    public boolean IsHit(Point p){
        for (Point p1: pList) {
            if(p1.IsHit(p)){
                return true;
            }
        }
        return false;
    }

    public void Draw(Canvas canvas, Paint paint){
        for (Point p: pList) {
            p.Draw(canvas, paint);
        }
    }
}
