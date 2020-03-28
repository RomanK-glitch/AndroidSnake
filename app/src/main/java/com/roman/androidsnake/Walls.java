package com.roman.androidsnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class Walls {
    ArrayList<Figure> wallsList;

    public Walls(int width, int height){
        wallsList =  new ArrayList<Figure>();

        HorizontalLine hzTop = new HorizontalLine(0, 0, width);
        VerticalLine vlLeft = new VerticalLine(0, 0, height);
        HorizontalLine hzBottom = new HorizontalLine(0, height-60, width);
        VerticalLine vlRight = new VerticalLine(width-20, 0, height);

        wallsList.add(hzTop);
        wallsList.add(hzBottom);
        wallsList.add(vlLeft);
        wallsList.add(vlRight);
    }

    public boolean IsHit(Figure figure){
        for (Figure wall : wallsList) {
            if (wall.IsHit(figure)){
                return true;
            }
        }
        return false;
    }

    public void Draw(Canvas canvas, Paint paintPurple){
        for (Figure wall : wallsList) {
            wall.Draw(canvas, paintPurple);
        }
    }
}
