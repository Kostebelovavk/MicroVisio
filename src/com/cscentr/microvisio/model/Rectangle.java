package com.cscentr.microvisio.model;

public class Rectangle extends Figure {

	private Point leftTop;
	private Point rightTop;
	private Point leftBottom;
	private Point rightBottom;
	private int constant;

	public Rectangle(Point pCentre, int irx, int iry, int isize, int sColor) {
		super(pCentre, irx, iry, isize, sColor, "Rectangle");
		calculatePoint();
		constant = 10;
		if ((int) ((irx + iry) / 7) > constant)
			constant = (int)((irx + iry) / 7);
	}

	public void calculatePoint() {
		int rx = getRx();
		int ry = getRy();
		Point centre = getPoint();
		leftTop = new Point(centre.x - rx, centre.y - ry);
		rightTop = new Point(centre.x + rx, centre.y - ry);
		leftBottom = new Point(centre.x - rx, centre.y + ry);
		rightBottom = new Point(centre.x + rx, centre.y + ry);
	}

	public void move(Point newCentre) {
		super.move(newCentre);
		calculatePoint();
	}

	public boolean isBoundary(Point point) {
		/*
		 * if (((point.x < leftTop.x + constant) && (point.x > leftTop.x -
		 * constant) && (point.y >= leftTop.y) && (point.y <= leftBottom.y)) ||
		 * ((point.y > leftTop.y - constant) && (point.y < leftTop.y + constant)
		 * && (point.x >= leftTop.x) && (point.x <= rightTop.x)) || ((point.x >
		 * rightTop.x - constant) && (point.x < rightTop.x + constant) &&
		 * (point.y >= rightTop.y) && (point.y <= rightBottom.y)) || ((point.y >
		 * rightBottom.y - constant) && (point.y < rightBottom.y + constant) &&
		 * (point.x >= leftBottom.x) && (point.x <= rightBottom.x))) return
		 * true; else return false;
		 */
		if (inside(point, constant) && !inside(point, -constant))
			return true;
		else
			return false;
	}

	public boolean inside(Point point, int constant) {
		if ((point.y >= leftTop.y - constant)
				&& (point.y <= leftBottom.y + constant)
				&& (point.x >= leftTop.x - constant)
				&& (point.x <= rightTop.x + constant))
			return true;
		else
			return false;
	}

	public boolean isInsideFigure(Point point) {
		if ((point.y >= leftTop.y) && (point.y <= leftBottom.y)
				&& (point.x >= leftTop.x) && (point.x <= rightTop.x))
			return true;
		else
			return false;
	}

	public Point getLeftTop() {
		return leftTop;
	}

	public void setLeftTop(Point leftTop) {
		this.leftTop = leftTop;
	}

	public Point getRightTop() {
		return rightTop;
	}

	public void setRightTop(Point rightTop) {
		this.rightTop = rightTop;
	}

	public Point getLeftBottom() {
		return leftBottom;
	}

	public void setLeftBottom(Point leftBottom) {
		this.leftBottom = leftBottom;
	}

	public Point getRightBottom() {
		return rightBottom;
	}

	public void setRightBottom(Point rightBottom) {
		this.rightBottom = rightBottom;
	}

}
