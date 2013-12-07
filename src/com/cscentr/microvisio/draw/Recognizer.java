package com.cscentr.microvisio.draw;

import com.cscentr.microvisio.model.Ellipse;
import com.cscentr.microvisio.model.Figure;
import com.cscentr.microvisio.model.Line;
import com.cscentr.microvisio.model.Point;
import com.cscentr.microvisio.model.Rectangle;

public class Recognizer {

	private int size;

	Recognizer(int isize) {
		setSize(isize);
	}

	private int minX = 0, maxX = 0, minY = 0, maxY = 0;
	private int w = 0, h = 0;
	private int constant = 20;

	public Figure recognize(Track track) {
		int xbegin = track.trackRace.get(0).getX();
		int ybegin = track.trackRace.get(0).getY();
		int xend = track.trackRace.get(track.trackRace.size() - 1).getX();
		int yend = track.trackRace.get(track.trackRace.size() - 1).getY();
		Point point = track.trackRace.get(0);
		maxX = point.getX();
		minX = point.getX();
		maxY = point.getY();
		minY = point.getY();
		if (track.trackRace.size() < 3)
			return null;
		for (int i = 1; i < track.trackRace.size(); i++) {
			point = track.trackRace.get(i);
			if (point.getX() > maxX)
				maxX = point.getX();
			else if (point.getX() < minX)
				minX = point.getX();
			if (point.getY() > maxY)
				maxY = point.getY();
			else if (point.getY() < minY)
				minY = point.getY();
		}
		w = maxX - minX;
		h = maxY - minY;
		float E = (float) (Math.abs(xbegin - xend) + Math.abs(ybegin - yend))
				/ (float) (w + h);
		if (E < 0.33f)
			return recognizeFigure(track);
		else
			return recognizeLine(track);
	}

	public Figure recognizeFigure(Track track) {
		float sum = 0;
		for (int i = 0; i < track.trackRace.size(); i++) {
			sum = sum + lx(track.trackRace.get(i)) / (float) w
					+ ly(track.trackRace.get(i)) / (float) h;
		}
		float H = 1f / (float) (track.trackRace.size()) * sum;
		if (H < 0.3f)
			return rectangle();
		else
			return ellipse();
	}

	public float lx(Point point) {
		return ((float) Math.min(point.x - minX, maxX - point.x));
	}

	public float ly(Point point) {
		return ((float) Math.min(maxY - point.y, point.y - minY));
	}

	public Figure recognizeLine(Track track) {
		Point point = null, point1 = null, point2 = null;
		int hatchingX = 0;
		//int hatchingY = 0;
		int angel = 0;
		double cosAlpha, predCosAlpha = 3;
		boolean waitChangeOfDirectionXMore = true;
		//boolean waitChangeOfDirectionYMore = true;
		for (int i = 0; i < track.trackRace.size(); i++) {
			point = track.trackRace.get(i);
			if (i + 2 < track.trackRace.size()) {
				point1 = track.trackRace.get(i + 1);
				point2 = track.trackRace.get(i + 2);
				cosAlpha = angleBetweenPoint(point, point1, point2);
				if (predCosAlpha >= 1.8)
					if (cosAlpha < 1.8)
						angel++;
				predCosAlpha = cosAlpha;
				if (waitChangeOfDirectionXMore && point.getX() > point1.getX()) {
					waitChangeOfDirectionXMore = false;
					hatchingX++;
				} else if (!waitChangeOfDirectionXMore
						&& point.getX() < point1.getX()) {
					waitChangeOfDirectionXMore = true;
					hatchingX++;
				}
				/*
				 * if (waitChangeOfDirectionYMore && point.getY() >
				 * point1.getY()) { waitChangeOfDirectionYMore = false;
				 * hatchingY++; } else if (!waitChangeOfDirectionYMore &&
				 * point.getY() < point1.getY()) { waitChangeOfDirectionYMore =
				 * true; hatchingY++; }
				 */
			}
		}
		point = track.trackRace.get(track.trackRace.size() - 1);
		if (angel == 3) {
			Line arrow = line(track.trackRace.get(0), point);
			/*
			 * if (point.getX() < track.trackRace.get(0).getX())
			 * arrow.setArrow(-1); else
			 */
			arrow.setArrow(1);
			return arrow;
		} else if (w > h && hatchingX >= 4) {
			Line hatch = hatchingLine();
			hatch.setHatching(1);
			return hatch;
		} else
			return line(track.trackRace.get(0), point);

	}

	public double angleBetweenPoint(Point a, Point b, Point c) {
		double x1 = a.getX() - b.getX(), x2 = c.getX() - b.getX();
		double y1 = a.getY() - b.getY(), y2 = c.getY() - b.getY();
		double d1 = Math.sqrt(x1 * x1 + y1 * y1);
		double d2 = Math.sqrt(x2 * x2 + y2 * y2);
		return Math.acos((x1 * x2 + y1 * y2) / (d1 * d2));
	}

	public Line hatchingLine() {
		Point centre = new Point((int) (minX + maxX) / 2,
				(int) (minY + maxY) / 2);
		Line line = new Line(centre, centre.getX() - minX,
				centre.getY() - minY, getSize(), -16777216);
		return line;
	}

	public Line line(Point point1, Point point2) {
		Line line = new Line(point1, point2, getSize(), -16777216);
		return line;
	}

	public Rectangle rectangle() {
		Point centre = new Point((int) ((maxX + minX) / 2),
				(int) ((maxY + minY) / 2));
		Rectangle rectangle = new Rectangle(centre, centre.getX() - minX,
				centre.getY() - minY, getSize(), -16777216);
		return rectangle;
	}

	public Ellipse ellipse() {
		Point centre = new Point((int) ((maxX + minX) / 2),
				(int) ((maxY + minY) / 2));
		Ellipse ellipse = new Ellipse(centre, centre.getX() - minX,
				centre.getY() - minY, getSize(), -16777216);
		return ellipse;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
