package com.cscentr.microvisio.readWrite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ListIterator;
import java.util.Scanner;

import org.xmlpull.v1.XmlSerializer;

import com.cscentr.microvisio.model.Figure;
import com.cscentr.microvisio.model.Line;
import com.cscentr.microvisio.model.Model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

public class Writer {
	final String LOG_TAG = "myLogs";
	final String DIR_SD = "MircoVisio";

	public void writeFileSD(String filename, Model model) {
		// проверяем доступность SD
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Log.d(LOG_TAG,
					"SD-карта не доступна: "
							+ Environment.getExternalStorageState());
			return;
		}
		// получаем путь к SD
		File sdPath = Environment.getExternalStorageDirectory();
		// добавляем свой каталог к пути
		sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
		// создаем каталог
		sdPath.mkdirs();
		// формируем объект File, который содержит путь к файлу
		File sdFile = new File(sdPath, filename);
		try {
			// открываем поток для записи
			BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
			// пишем данные
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
					bw.write(figure.getColor() + "\n");
				} else {
					bw.write(i + " ");
					bw.write(figure.getClassName().toString() + " ");
					bw.write(figure.getPoint().getX() + " "
							+ figure.getPoint().getY() + " ");
					bw.write(figure.getRx() + " ");
					bw.write(figure.getRy() + " ");
					bw.write(figure.getSize() + " ");
					bw.write(figure.getColor() + "\n");
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
						bw.write(figure.getColor() + " ");
					} else {
						bw.write(i + " ");
						bw.write(figure.getClassName().toString() + " ");
						bw.write(figure.getPoint().getX() + " "
								+ figure.getPoint().getY() + " ");
						bw.write(figure.getRx() + " ");
						bw.write(figure.getRy() + " ");
						bw.write(figure.getSize() + " ");
						bw.write(figure.getColor() + " ");
					}
				}
			}
			// bw.write("Содержимое файла на SD");
			// закрываем поток
			bw.close();
			Log.d(LOG_TAG, "Файл записан на SD: " + sdFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeXmlFile(String filename, Model model, Context context)
			throws IllegalArgumentException, IllegalStateException, IOException {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Log.d(LOG_TAG,
					"SD-карта не доступна: "
							+ Environment.getExternalStorageState());
			return;
		}
		// получаем путь к SD
		File sdPath = Environment.getExternalStorageDirectory();
		// добавляем свой каталог к пути
		sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
		// создаем каталог
		sdPath.mkdirs();
		// формируем объект File, который содержит путь к файлу
		File sdFile = new File(sdPath, filename);
		FileOutputStream fos = new FileOutputStream(sdFile);
		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(fos, "UTF-8");
		serializer.startDocument(null, Boolean.valueOf(true));
		serializer.setFeature(
				"http://xmlpull.org/v1/doc/features.html#indent-output", true);
		serializer.startTag(null, "root");
		ListIterator<Figure> iter;
		iter = model.getIter();
		int i = 0;
		while (iter.hasNext()) {
			Figure figure = iter.next();
			if (figure.getClassName().equals("Line")) {
				Line line = (Line) figure;
				int ID1 = model.figureList.indexOf(line.getFirstFigure());
				int ID2 = model.figureList.indexOf(line.getSecondFigure());
				serializer.startTag(null, "Line");
				serializer.startTag(null, "num");
				serializer.text(String.valueOf(i));
				serializer.endTag(null, "num");
				serializer.startTag(null, "class");
				serializer.text(figure.getClassName().toString());
				serializer.endTag(null, "class");
				serializer.startTag(null, "id1");
				serializer.text(String.valueOf(ID1));
				serializer.endTag(null, "id1");
				serializer.startTag(null, "id2");
				serializer.text(String.valueOf(ID2));
				serializer.endTag(null, "id2");
				serializer.startTag(null, "size");
				serializer.text(String.valueOf(figure.getSize()));
				serializer.endTag(null, "size");
				serializer.startTag(null, "color");
				serializer.text(String.valueOf(figure.getColor()));
				serializer.endTag(null, "color");
				serializer.endTag(null, "Line");
			} else {
				serializer.startTag(null, "Figure");
				serializer.startTag(null, "num");
				serializer.text(String.valueOf(i));
				serializer.endTag(null, "num");
				serializer.startTag(null, "class");
				serializer.text(figure.getClassName().toString());
				serializer.endTag(null, "class");
				serializer.startTag(null, "point");
				serializer.text(String.valueOf(figure.getPoint().getX()));
				serializer.text(String.valueOf(figure.getPoint().getY()));
				serializer.endTag(null, "point");
				serializer.startTag(null, "rx");
				serializer.text(String.valueOf(figure.getRx()));
				serializer.endTag(null, "rx");
				serializer.startTag(null, "ry");
				serializer.text(String.valueOf(figure.getRy()));
				serializer.endTag(null, "ry");
				serializer.startTag(null, "size");
				serializer.text(String.valueOf(figure.getSize()));
				serializer.endTag(null, "size");
				serializer.startTag(null, "color");
				serializer.text(String.valueOf(figure.getColor()));
				serializer.endTag(null, "color");
				serializer.endTag(null, "Figure");
			}
			i++;
		}
		serializer.endDocument();
		serializer.flush();
		fos.close();
	}

}
