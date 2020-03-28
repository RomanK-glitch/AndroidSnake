package com.roman.androidsnake;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class HorizontalLine extends Figure {

    public HorizontalLine(int x, int y, int length){
        pList = new ArrayList<Point>();
        for (int i = 0; i < length; i++){
            Point p = new Point(x, y);
            pList.add(p);
            x += 20;
        }
    }
}
