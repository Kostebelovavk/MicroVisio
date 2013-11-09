package com.cscentr.microvisio.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Model {
	public List<Figure> figureList;

	public Model() {
		figureList = new ArrayList<Figure>();
	}

	public ListIterator<Figure> getIter() {
		return figureList.listIterator();
	}

	public void addFigure(Figure figure) {
		figureList.add(figure);
	}

	public void deleteFigure(Figure figure) {
		figureList.remove(figure);
		ListIterator<Figure> iter = getIter();
		if (!figure.getClassName().equals("Line")) {
			while (iter.hasNext()) {
				Figure fig = iter.next();
				if (fig.getClassName().equals("Line")) {
					Line line = (Line) fig;
					if (line.getFirstFigure().equals(figure)
							|| line.getSecondFigure().equals(figure)) {
						figureList.remove(line);
						iter = getIter();
					}
				}
			}
		}
	}

	public Figure isBoundary(Point point) {
		ListIterator<Figure> iter = getIter();
		while (iter.hasNext()) {
			Figure figure = iter.next();
			if (figure.isBoundary(point))
				return figure;
		}
		return null;
	}

	public Figure isInsideAndNotLine(Point point) {
		ListIterator<Figure> iter = getIter();
		while (iter.hasNext()) {
			Figure figure = iter.next();
			if (!figure.getClassName().equals("Line"))
				if (figure.isInsideFigure(point))
					return figure;
		}
		return null;
	}

	public Figure isInsideLine(Point point1, Point point2) {
		ListIterator<Figure> iter = getIter();
		while (iter.hasNext()) {
			Figure figure = iter.next();
			if ((figure.getClassName().equals("Line"))) {
				Line line = (Line) figure;
				if (line.isInsideSegment(point1, point2))
					return figure;
			}
		}
		return null;
	}

	public String className(Figure figure) {
		return (figure.getClass().getName());
	}

}
