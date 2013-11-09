package com.cscentr.microvisio.model;

//координаты
public class Point {
	public int x;
	public int y;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Point(int x1, int y1) {
		x = x1;
		y = y1;
	}

	public boolean more(Point point) {
		if (this.x > point.x)
			return true;
		else if (this.x == point.x)
			if (this.y > point.y)
				return true;
			else
				return false;
		return false;
	}

}
