package com.cscentr.microvisio.draw;

import com.cscentr.microvisio.model.Point;

public class Finger {
    public int ID;			// Идентификатор пальца
    public Point Now;
    public Point Before;
    //boolean enabled = false;
    public long wasDown;
    public boolean enabledLongTouch = true;
    Point startPoint;
    
    public Finger(int id, int x, int y,boolean enabledLongTouch1){
        wasDown = System.currentTimeMillis();
        ID = id;
        enabledLongTouch = enabledLongTouch1;
        Now = Before = startPoint = new Point(x, y);
    }

    public void setNow(int x, int y){
     /*   if(!enabled){
            enabled = true;
            Now = Before = startPoint = new Point(x, y);
        }*/
        Before = Now;
        Now = new Point(x, y);
      //  if(Draw.checkDistance(Now, startPoint) > Draw.getDensity() * 25)
       //     enabledLongTouch = false;
    }
}