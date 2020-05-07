package com.mod.graph;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;




public class PlotDot{

    private Rect dot;
    private int x, y, stroke, color;

    public PlotDot(int x, int y, int stroke, int color){
        dot = new Rect(x, y, x + stroke, y + stroke);
        this.x = x;
        this.y = y;
        this.stroke = stroke;
        this.color = color;
    }

    public void render(Canvas canvas) {
        Frame.paint.setColor(color);
        canvas.drawRect(x, y, x+stroke, y+stroke, Frame.paint);
    }

    public void setX(int x){
        this.x = x;
    }

    public int getX(){
        return this.x;
    }

    public int getStroke(){
        return this.stroke;
    }
}
