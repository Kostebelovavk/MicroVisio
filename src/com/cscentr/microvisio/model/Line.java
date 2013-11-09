package com.cscentr.microvisio.model;

public class Line extends Figure {
	private Figure first;
	private Figure second;
	private int hatching; // 0-не удаление, 1-горизантальное, -1-вертикальное
	private int arrow; // 0-не стрелка, 1-стрелка, -1-стрелка на оборот

	public Line(Point centre, int rx, int ry, int isize, String sColor) {
		super(centre, rx, ry, isize, sColor, "Line");
		setHatching(0);
		setArrow(0);
	}

	public Line(Figure fFirst, Figure fSecond, int isize, String sColor) {
		super(
				new Point(
						((fFirst.getPoint().x + fFirst.getRx()) + (fSecond.getPoint().x - fSecond
								.getRx())) / 2, ((fFirst.getPoint().y + fFirst
								.getRy()) + (fSecond.getPoint().y - fSecond
								.getRy())) / 2), Math
						.abs((fSecond.getPoint().x - fSecond.getRx())
								- (fFirst.getPoint().x + fFirst.getRx())) / 2,
				isize / 2, isize, sColor, "Line");
		first = fFirst;
		second = fSecond;
		if (first.getPoint().more(second.getPoint()))
			recalculation();

	}

	public void recalculation() {
		Figure temp = first;
		first = second;
		second = temp;
	}

	public Figure getFirstFigure() {
		return first;
	}

	public Figure getSecondFigure() {
		return second;
	}

	public boolean setFirstFigure(Figure fFirst) {
		if (fFirst != null) {
			first = fFirst;
			return true;
		} else
			return false;

	}

	public boolean setSecondFigure(Figure fSecond) {
		if (fSecond != null) {
			second = fSecond;
			return true;
		} else
			return false;
	}

	public int isHatching() {
		return hatching;
	}

	public void setHatching(int hatching) {
		this.hatching = hatching;
	}

	public boolean isInsideSegment(Point point1, Point point2) {
		double d, da, db, ta, tb;
		Point b1 = first.getPoint();
		Point b2 = second.getPoint();
		d = (point1.getX() - point2.getX()) * (b2.getY() - b1.getY())
				- (point1.getY() - point2.getY()) * (b2.getX() - b1.getX());
		da = (point1.getX() - b1.getX()) * (b2.getY() - b1.getY())
				- (point1.getY() - b1.getY()) * (b2.getX() - b1.getX());
		db = (point1.getX() - point2.getX()) * (point1.getY() - b1.getY())
				- (point1.getY() - point2.getY()) * (point1.getX() - b1.getX());
		if (Math.abs(d) < 0.000001)
			return false;
		else {
			ta = da / d;
			tb = db / d;
			if ((0 <= ta) && (ta <= 1) && (0 <= tb) && (tb <= 1)) {
				return true;
			} else
				return false;
		}
		/*
		 * Point x1 = new Point(getPoint().getX() - getRx(), getPoint().getY() -
		 * getRy()); Point x2 = new Point(getPoint().getX() - getRx(),
		 * getPoint().getY() + getRy()); Point x3 = new Point(getPoint().getX()
		 * + getRx(), getPoint().getY() + getRy()); Point x4 = new
		 * Point(getPoint().getX() + getRx(), getPoint().getY() - getRy()); //
		 * (x-x1)/(x2-x1)=(y-y1)/(y2-y1) if (((point.getX() - x1.getX()) /
		 * (x3.getX() - x1.getX())) == (point .getY() - x1.getY()) / (x3.getY()
		 * - x1.getY())) { return true; } else if (((point.getX() - x1.getX()) /
		 * (x4.getX() - x1.getX())) == (point .getY() - x1.getY()) / (x4.getY()
		 * - x1.getY())) { return true; } else if (((point.getX() - x2.getX()) /
		 * (x3.getX() - x2.getX())) == (point .getY() - x2.getY()) / (x3.getY()
		 * - x2.getY())) { return true; } else if (((point.getX() - x2.getX()) /
		 * (x4.getX() - x2.getX())) == (point .getY() - x2.getY()) / (x4.getY()
		 * - x2.getY())) { return true; }
		 */
	}

	public int isArrow() {
		return arrow;
	}

	public void setArrow(int arrow) {
		this.arrow = arrow;
	}

}
