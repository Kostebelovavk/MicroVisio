package com.cscentr.microvisio.model;

public class Ellipse extends Figure {

	private int constant;

	public Ellipse(Point pCentre, int irx, int iry, int isize, String sColor) {
		super(pCentre, irx, iry, isize, sColor, "Ellipse");
		constant = 5;
	}

	public boolean isBoundary(Point point) {
		Point centre = getPoint();
		if ((point.x - centre.x) * (point.x - centre.x) / (getRx() * getRx())
				+ (point.y - centre.y) * (point.y - centre.y)
				/ (getRy() * getRy()) == 1)
			return true;
		else if ((point.x + 1 - centre.x) * (point.x + 1 - centre.x)
				/ (getRx() * getRx()) + (point.y + 1 - centre.y)
				* (point.y + 1 - centre.y) / (getRy() * getRy()) == 1)
			return true;
		else if ((point.x - 1 - centre.x) * (point.x - 1 - centre.x)
				/ (getRx() * getRx()) + (point.y - 1 - centre.y)
				* (point.y - 1 - centre.y) / (getRy() * getRy()) == 1)
			return true;
		else if ((point.x + 2 - centre.x) * (point.x + 2 - centre.x)
				/ (getRx() * getRx()) + (point.y + 2 - centre.y)
				* (point.y + 2 - centre.y) / (getRy() * getRy()) == 1)
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
