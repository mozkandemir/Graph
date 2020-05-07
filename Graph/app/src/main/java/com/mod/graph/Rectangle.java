package com.mod.graph;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.net.InterfaceAddress;

public class Rectangle extends Element{

    public Rect rect;
    int color = 0;
    public float degree = 0.0f;    private Tick tickMethod;
    int cx, cy, radious = 0;
    boolean drawCircle = false;

    public Rectangle(int x, int y, int width, int height, int color, Tick tickMethod){
        super();
        rect = new Rect(x, y, x+width, y+height);
        this.color = color;
        this.tickMethod = tickMethod;
    }

    @Override
    public void tick() {
        tickMethod.tick();
    }

    public void setDegree(float d){
        degree = d;
    }

    public void setX(int x){
        rect.set(x, rect.top, x+(rect.right - rect.left), rect.bottom);
    }

    public void setY(int y){
        rect.set(rect.left, y, rect.right, y+(rect.bottom - rect.top));
    }

    public void setWidth(int width){
        rect.set(rect.left, rect.top, rect.left+width, rect.bottom);
    }

    public void setHeight(int height){
        rect.set(rect.left, rect.top, rect.right, rect.top+height);
    }

    @Override
    public void render(Canvas canvas) {
        canvas.save();
        canvas.rotate(degree, rect.centerX(), rect.centerY());
        Frame.paint.setColor(color);
        canvas.drawRect(rect, Frame.paint);
        canvas.restore();
        if(drawCircle){
            Frame.paint.setColor(Color.argb(100, 50, 50, 50));
            canvas.drawCircle(cx, cy, radious, Frame.paint);
        }
    }

    public boolean containsMouse(){
        Point p = Calc.rotate(rect.centerX(), rect.centerY(), -degree, Frame.p);
        if(rect.contains(p.x,p.y)){
            if(!drawCircle){
                cx = Frame.p.x;
                cy = Frame.p.y;
            }
            drawCircle = true;
            radious+=3;

            return true;
        }else{
            radious = 0;
            drawCircle = false;
            return false;
        }
    }

}
