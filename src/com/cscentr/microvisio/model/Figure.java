package com.cscentr.microvisio.model;


import android.graphics.RectF;

public class Figure {
	private Point centre;
	private int rx; // рассто€ние до вершины по оси x
	private int ry;
	private int size;
	private String color;
	private String className;
	private String text; // “екст внутри фигуры

	public Figure(Point pcenter, int irx, int iry, int isize, String sColor,String sClassName) {
		centre = pcenter;
		setRx(irx);
		setRy(iry);
		setSize(isize);
		setColor(sColor);
		setClassName(sClassName);
	}

	public RectF getRectf() {
		RectF rect = new RectF(centre.x - rx, centre.y - ry, centre.x + rx,
				centre.y + ry);
		return rect;
	}

	public Point getPoint() {
		return centre;
	}

	public void setPoint(Point point) {
		centre.x = point.x;
		centre.y = point.y;
	}

	public int getRy() {
		return ry;
	}

	public void setRy(int ry) {
		this.ry = ry;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getRx() {
		return rx;
	}

	public void setRx(int rx) {
		this.rx = rx;
	}

	public String getText() {
		return text;
	}

	public void setText(String sText) {
		text = sText;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void move(Point newPoint) {
		centre = newPoint;
	}

	public boolean isBoundary(Point point) {
		return false;
	}

	public boolean isInsideFigure(Point point) {
		return false;
	}

	public String getClassName() {
		return (className);
	}
	public void setClassName(String className) {
		this.className = className;
	}
}
