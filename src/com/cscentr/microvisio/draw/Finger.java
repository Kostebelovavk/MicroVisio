package com.cscentr.microvisio.draw;

import com.cscentr.microvisio.model.Point;

public class Finger {
    public int ID;			// Идентификатор пальца
    public Point Now;
    public Point Before;
    boolean enabled;		// Было ли уже сделано движение
    
    public Finger(int id, int x, int y){
        ID = id;
        Before = new Point(x, y);
        Now = new Point(x, y);
        enabled = true;
    }
    
    public void setNow(int x, int y){
            Before = Now;
            Now = new Point(x, y);
    }
}