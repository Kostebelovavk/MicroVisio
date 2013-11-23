package com.cscentr.microvisio.readWrite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import android.os.Environment;
import android.util.Log;

import com.cscentr.microvisio.model.Ellipse;
import com.cscentr.microvisio.model.Line;
import com.cscentr.microvisio.model.Model;
import com.cscentr.microvisio.model.Point;
import com.cscentr.microvisio.model.Rectangle;

public class Reader {

	final String LOG_TAG = "myLogs";
	final String DIR_SD = "MircoVisio";

	public void readFileSD(String filename, Model model) {
		// ��������� ����������� SD
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Log.d(LOG_TAG,
					"SD-����� �� ��������: "
							+ Environment.getExternalStorageState());
			return;
		}
		// �������� ���� � SD
		File sdPath = Environment.getExternalStorageDirectory();
		// ��������� ���� ������� � ����
		sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
		// ��������� ������ File, ������� �������� ���� � �����
		File sdFile = new File(sdPath, filename);
		try {
			// ��������� ����� ��� ������
			Scanner scanner = new Scanner(sdFile);
			// ������ ����������
			while (scanner.hasNextLine()) {
				String st = scanner.next();
				if (!scanner.hasNextInt()) {
					st = scanner.next();
				}
				if (st.equals("Ellipse")) {
					Point centre = new Point(scanner.nextInt(),
							scanner.nextInt());
					int rx = scanner.nextInt();
					int ry = scanner.nextInt();
					int size = scanner.nextInt();
					String color = scanner.next();
					Ellipse ellipse = new Ellipse(centre, rx, ry, size, color);
					model.addFigure(ellipse);
				} else if (st.equals("Rectangle")) {
					Point centre = new Point(scanner.nextInt(),
							scanner.nextInt());
					int rx = scanner.nextInt();
					int ry = scanner.nextInt();
					int size = scanner.nextInt();
					String color = scanner.next();
					Rectangle rectangle = new Rectangle(centre, rx, ry, size,
							color);
					model.addFigure(rectangle);
				} else if (st.equals("Line")) {
					int firstID = scanner.nextInt();
					int secondID = scanner.nextInt();
					int size = scanner.nextInt();
					String color = scanner.next();
					Line line = new Line(model.figureList.get(firstID),
							model.figureList.get(secondID), size, color);
					model.addFigure(line);
				}
				else if (st.equals("Arrow")) {
					int firstID = scanner.nextInt();
					int secondID = scanner.nextInt();
					int size = scanner.nextInt();
					String color = scanner.next();
					Line arrow = new Line(model.figureList.get(firstID),
							model.figureList.get(secondID), size, color);
					arrow.setArrow(1);
					model.addFigure(arrow);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void readFromTxtFile(InputStream inputStream, Model model) {
		//
		// ��������� ����� Scanner, �������������� ��� � ���������� file
		// ������� ����, ������� ����� ��������� ������, ���� �� ������
		// �� ����� �����.
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
				String color = scanner.next();
				Ellipse ellipse = new Ellipse(centre, rx, ry, size, color);
				model.addFigure(ellipse);
			} else if (st.equals("Rectangle")) {
				Point centre = new Point(scanner.nextInt(), scanner.nextInt());
				int rx = scanner.nextInt();
				int ry = scanner.nextInt();
				int size = scanner.nextInt();
				String color = scanner.next();
				Rectangle rectangle = new Rectangle(centre, rx, ry, size, color);
				model.addFigure(rectangle);
			} else if (st.equals("Line")) {
				int firstID = scanner.nextInt();
				int secondID = scanner.nextInt();
				int size = scanner.nextInt();
				String color = scanner.next();
				Line line = new Line(model.figureList.get(firstID),
						model.figureList.get(secondID), size, color);
				model.addFigure(line);
			}
		}
	}

}
