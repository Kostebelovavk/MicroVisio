package com.cscentr.microvisio.readWrite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import com.cscentr.microvisio.model.Ellipse;
import com.cscentr.microvisio.model.Line;
import com.cscentr.microvisio.model.Model;
import com.cscentr.microvisio.model.Point;
import com.cscentr.microvisio.model.Rectangle;

import android.content.res.AssetManager;

public class Reader {

	public void readFromTxtFile(InputStream inputStream, Model model) {
		//
		// ќбъ€вл€ем класс Scanner, инициализируем его с параметром file
		// —оздаем цикл, который будет считывать строки, пока не дойдем
		// до конца файла.
		//
		Scanner scanner = new Scanner(inputStream);
		while (scanner.hasNextLine()) {
			String st = scanner.next();
			if (!scanner.hasNextInt()) {
				st = scanner.next();
			}
			if (st.equals("Ellipse")) {
				Point centre = new Point(scanner.nextInt(), scanner.nextInt());
				int rx = scanner.nextInt();
				int ry = scanner.nextInt();
				int size = scanner.nextInt();
				String color= scanner.next();
				Ellipse ellipse = new Ellipse(centre, rx, ry, size,color);
				model.addFigure(ellipse);
			} else if (st.equals("Rectangle")) {
				Point centre = new Point(scanner.nextInt(), scanner.nextInt());
				int rx = scanner.nextInt();
				int ry = scanner.nextInt();
				int size = scanner.nextInt();
				String color= scanner.next();
				Rectangle rectangle = new Rectangle(centre, rx, ry, size,color);
				model.addFigure(rectangle);
			} else if (st.equals("Line")) {
				int firstID = scanner.nextInt();
				int secondID = scanner.nextInt();
				int size = scanner.nextInt();
				String color= scanner.next();
				Line line = new Line(model.figureList.get(firstID),
						model.figureList.get(secondID), size,color);
				model.addFigure(line);
			}
		}
	}

}
