package com.cscentr.microvisio.draw;

import java.util.ArrayList;

import com.cscentr.microvisio.model.Point;

public class Track {
	ArrayList<Point> trackRace;

	public Track() {
		trackRace = new ArrayList<Point>();
	}

	public boolean addPoint(Point point) {
		if (trackRace.add(point))
			return true;
		else
			return false;
	}

	public void deletePoint(int i) {
		trackRace.remove(i);
	}
}
