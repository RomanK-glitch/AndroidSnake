package com.roman.androidsnake;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.telephony.mbms.MbmsErrors;

import java.util.ArrayList;

public class Snake extends Figure {
    public Direction direction;

    public Snake(Point tail, int length, Direction _direction){
        direction = _direction;
        pList = new ArrayList<Point>();
        for (int i = 0; i < length; i++){
            Point p = new Point(tail);
            p.Move(i*20, direction);
            pList.add(p);
        }
    }

    public void ChangeCoordinates(){
        Point tail = GetTail();
        pList.remove(tail);
        Point head = GetNextPoint();
        pList.add(head);
    }

    public void Move(Canvas canvas, Paint draw, Paint clear, Point tail, Point head){
        tail.Draw(canvas, clear);
        head.Draw(canvas, draw);
    }

    public boolean Eat(Point food){
        Point head = GetHead();
        if ( head.IsHit( food )) {
            Point tail = GetTail();
            Point previousPoint = new Point(tail);
            previousPoint.Move(-20, direction);
            pList.add(0, previousPoint);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean IsHitTail(){
        Point head = GetHead();
        for (int i = 0; i < pList.size() - 2; i++){
            if(head.IsHit(pList.get(i))){
                return true;
            }
        }
        return false;
    }

    public Point GetNextPoint(){
        Point head = GetHead();
        Point nextPoint = new Point(head);
        nextPoint.Move(20, direction);
        return nextPoint;
    }
    public Point GetHead(){
        Point head = pList.get(pList.size()-1);
        return head;
    }
    public Point GetTail(){
        Point tail = pList.get(0);
        return tail;
    }
}
