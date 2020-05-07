package com.mod.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;



public class Frame extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    public static Point p;
    public static int WIDTH,HEIGHT;
    public static Paint paint = new Paint();
    public Frame(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size);
        } else {
            display.getSize(size); // correct for devices with hardware navigation buttons
        }

        WIDTH = size.x;
        HEIGHT = size.y;
        p = new Point(200,200);

        System.out.println("width: " + WIDTH + " height: " + HEIGHT);

        PlotScreen s1 = new PlotScreen(140, 100, 800, 600, true, 0, 100, 0, 100, Color.rgb(50, 50, 50), Color.rgb(50, 150, 50), 5, "EMG SENSOR (1)");
        PlotScreen s2 = new PlotScreen(140, 800, 800, 600, true, 0, 100, 0, 100, Color.rgb(50, 50, 50), Color.rgb(250, 50, 50), 5, "EMG SENSOR (2)");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch (Exception e){
                retry = false;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                p.set((int)event.getRawX(), (int)event.getRawY());
                break;
            case MotionEvent.ACTION_DOWN:
                p.set((int)event.getRawX(), (int)event.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                p.set(0, 0);
                break;
        }
        return true;
    }

    public void update(){
        try {
            for (int i = 0; i < Element.elements.size(); i++) {
                Element.elements.get(i).tick();
            }
        } catch (Exception e) {
            System.err.println("No such element to tick");
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        paint.setColor(Color.rgb(200, 255, 200));
        canvas.drawRect(new Rect(0,0,WIDTH,HEIGHT),paint);
        try {
            synchronized (Element.elements) {
                for (int i = Element.elements.size() - 1; i > -1; i--) {
                    Element.elements.get(i).render(canvas);
                }
            }
        } catch (Exception e) {
            System.err.println("No such element to render");
        }
    }

}
