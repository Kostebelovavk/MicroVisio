package com.cscentr.microvisio.readWrite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ListIterator;
import java.util.Scanner;

import com.cscentr.microvisio.model.Figure;
import com.cscentr.microvisio.model.Line;
import com.cscentr.microvisio.model.Model;

import android.os.Environment;
import android.util.Log;

public class Writer {
	final String LOG_TAG = "myLogs";
	final String DIR_SD = "MircoVisio";

	public void writeFileSD(String filename, Model model) {
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
		// ������� �������
		sdPath.mkdirs();
		// ��������� ������ File, ������� �������� ���� � �����
		File sdFile = new File(sdPath, filename);
		try {
			// ��������� ����� ��� ������
			BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
			// ����� ������
			// out.write(c.toString()+"\n");
			ListIterator<Figure> iter;
			iter = model.getIter();
			int i = 0;
			while (iter.hasNext()) {
				Figure figure = iter.next();
				if (figure.getClassName().equals("Line")) {
					Line line = (Line) figure;
					int ID1 = model.figureList.indexOf(line.getFirstFigure());
					int ID2 = model.figureList.indexOf(line.getSecondFigure());
					bw.write(i + " ");
					bw.write(figure.getClassName().toString() + " ");
					bw.write(ID1 + " " + ID2 + " ");
					bw.write(figure.getSize() + " ");
					bw.write(figure.getColor().toString() + "\n");
				} else {
					bw.write(i + " ");
					bw.write(figure.getClassName().toString() + " ");
					bw.write(figure.getPoint().getX() + " "
							+ figure.getPoint().getY() + " ");
					bw.write(figure.getRx() + " ");
					bw.write(figure.getRy() + " ");
					bw.write(figure.getSize() + " ");
					bw.write(figure.getColor().toString()+"\n");
				}
				i++;
				if (i == model.figureList.size() - 1) {
					figure = iter.next();
					if (figure.getClassName().equals("Line")) {
						Line line = (Line) figure;
						int ID1 = model.figureList.indexOf(line
								.getFirstFigure());
						int ID2 = model.figureList.indexOf(line
								.getSecondFigure());
						bw.write(i + " ");
						bw.write(figure.getClassName().toString() + " ");
						bw.write(ID1 + " " + ID2 + " ");
						bw.write(figure.getSize() + " ");
						bw.write(figure.getColor().toString());
					} else {
						bw.write(i + " ");
						bw.write(figure.getClassName().toString() + " ");
						bw.write(figure.getPoint().getX() + " "
								+ figure.getPoint().getY() + " ");
						bw.write(figure.getRx() + " ");
						bw.write(figure.getRy() + " ");
						bw.write(figure.getSize() + " ");
						bw.write(figure.getColor().toString());
					}
				}
			}
			// bw.write("���������� ����� �� SD");
			// ��������� �����
			bw.close();
			Log.d(LOG_TAG, "���� ������� �� SD: " + sdFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
