package com.roman.androidsnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Point {
    int x1;
    int x2;
    int y1;
    int y2;

    public Point(){

    }

    public Point(int x, int y){
        x1 = x;
        x2 = x1+20;
        y1 = y;
        y2 = y1+20;
    }

    public Point(Point p){
        x1 = p.x1;
        x2 = x1+20;
        y1 = p.y1;
        y2 = y1+20;
    }

    public void Move(int offset, Direction direction){
        if(direction == Direction.RIGHT){
            x1 += offset;
            x2 += offset;
        }
        if(direction == Direction.LEFT){
            x1 -= offset;
            x2 -= offset;
        }
        if(direction == Direction.UP){
            y1 -= offset;
            y2 -= offset;
        }
        if(direction == Direction.DOWN){
            y1 += offset;
            y2 += offset;
        }
    }

    public boolean IsHit (Point p){
        return p.x1 == this.x1 && p.y1 == this.y1;
    }

    public void Draw(Canvas canvas, Paint paint){
        canvas.drawRect(x1, y1, x2, y2, paint);
    }
}
