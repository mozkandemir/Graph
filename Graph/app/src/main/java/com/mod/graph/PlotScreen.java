package com.mod.graph;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;


import java.util.ArrayList;
import java.util.logging.XMLFormatter;


public class PlotScreen extends Element{

    private int x, y, width, height;
    private boolean orientation = true; // orientation on the screen true -> vertical, false -> horizontal
    private int inMin, inMax, outMin, outMax; // mapping interval
    private int frameColor, dotColor;
    int dotStroke;
    private Rect frame, dataFrame;
    private ArrayList<PlotDot> data = new ArrayList<>();
    private String currentData = "", title = "";

    public PlotScreen(int x, int y, int width, int height, boolean orientation, int inMin, int inMax, int outMin, int outMax, int frameColor, int dotColor, int dotStroke, String title){
        super();
        frame = new Rect(x, y, x + width, y + height);
        dataFrame = new Rect(x, y+height-40, x+100, y+height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height =height;
        this.orientation = orientation;
        this.inMin = inMin;
        this.inMax = inMax;
        this.outMax = outMax;
        this.outMin = outMin;
        this.frameColor = frameColor;
        this.dotColor = dotColor;
        this.dotStroke = dotStroke;
        this.title = title;
    }

    int xx = Calc.random.nextInt(300) + 150, speed = Calc.random.nextInt(5);
    double degree = 0;
    @Override
    public void tick() {
        /*addNewData(xx);
        xx+= speed;
        if(xx > 500 || xx < 150) speed*=-1;
        currentData = xx + "";*/
        double rad = Math.toRadians(degree);
        degree += speed;
        addNewData(Calc.map(Math.cos(rad), 0.0, 1.0, 300, 400));
        if(degree > 360) degree = 0;
        currentData = degree + "";
    }

    @Override
    public void render(Canvas canvas) {
        Frame.paint.setColor(frameColor);
        Frame.paint.setStyle(Paint.Style.STROKE);
        Frame.paint.setStrokeWidth(5.0f);
        canvas.drawRect(frame, Frame.paint);
        canvas.drawRect(dataFrame, Frame.paint);
        Frame.paint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < data.size(); i++) {
            data.get(i).render(canvas);
        }
        Frame.paint.setTextSize(40);
        canvas.drawText(currentData,x+dotStroke,y+height-5,Frame.paint);
        canvas.drawText(title,x+270,y-8,Frame.paint);
    }

    public void addNewData(double val){
        data.add(new PlotDot(x + width, (int)val+y, dotStroke, dotColor));
        shift();
    }

    public void shift(){
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setX(data.get(i).getX() - data.get(i).getStroke());
            if(data.get(i).getX() < x) data.remove(i);
        }
    }
}
