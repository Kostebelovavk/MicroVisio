package com.cscentr.microvisio.model;

public class Ellipse extends Figure {

	private int constant;
	private Point leftTop;
	private Point rightTop;
	private Point leftBottom;
	private Point rightBottom;

	public Ellipse(Point pCentre, int irx, int iry, int isize, String sColor) {
		super(pCentre, irx, iry, isize, sColor, "Ellipse");
		calculatePoint();
		constant = 15;
		if ((int) ((irx + iry) / 7) > constant)
			constant = (int) ((irx + iry) / 7);
	}

	public void move(Point newCentre) {
		super.move(newCentre);
		calculatePoint();
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

	public boolean inside(Point point, int constant) {
		if ((point.y >= leftTop.y - constant)
				&& (point.y <= leftBottom.y + constant)
				&& (point.x >= leftTop.x - constant)
				&& (point.x <= rightTop.x + constant))
			return true;
		else
			return false;
	}

	public boolean isBoundary(Point point) {
		/*
		 * Point centre = getPoint(); if ((point.x - centre.x) * (point.x -
		 * centre.x) / (getRx() * getRx()) + (point.y - centre.y) * (point.y -
		 * centre.y) / (getRy() * getRy()) == 1) return true; else if ((point.x
		 * + 1 - centre.x) * (point.x + 1 - centre.x) / (getRx() * getRx()) +
		 * (point.y + 1 - centre.y) (point.y + 1 - centre.y) / (getRy() *
		 * getRy()) == 1) return true; else if ((point.x - 1 - centre.x) *
		 * (point.x - 1 - centre.x) / (getRx() * getRx()) + (point.y - 1 -
		 * centre.y) (point.y - 1 - centre.y) / (getRy() * getRy()) == 1) return
		 * true; else if ((point.x + 2 - centre.x) * (point.x + 2 - centre.x) /
		 * (getRx() * getRx()) + (point.y + 2 - centre.y) (point.y + 2 -
		 * centre.y) / (getRy() * getRy()) == 1) return true; else return false;
		 */
		if (inside(point, constant) && !inside(point, -constant))
			return true;
		else
			return false;
	}

	public boolean isInsideFigure(Point point) {
		Point centre = getPoint();
		if ((point.x - centre.x) * (point.x - centre.x) / (getRx() * getRx())
				+ (point.y - centre.y) * (point.y - centre.y)
				/ (getRy() * getRy()) <= 1)
			return true;
		else
			return false;
	}

}
