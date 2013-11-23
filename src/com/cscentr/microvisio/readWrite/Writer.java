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
				i++;
				Figure figure = iter.next();
				if (figure.getClassName().equals("Line")) {
					Line line = (Line) figure;
					int ID1 = model.figureList.indexOf(line.getFirstFigure());
					int ID2 = model.figureList.indexOf(line.getSecondFigure());
					bw.write(i+" ");
					bw.write(ID1+" "+ID2+" ");
					bw.write(figure.getSize()+" ");
					bw.write(figure.getColor().toString()+" ");
					bw.write(figure.getClassName().toString()+"\n");
				}
				else {
					bw.write(i+" ");
					bw.write(figure.getPoint().getX()+" "+figure.getPoint().getY()+" ");
					bw.write(figure.getRx()+" ");
					bw.write(figure.getRy()+" ");
					bw.write(figure.getSize()+" ");
					bw.write(figure.getColor().toString()+" ");
					bw.write(figure.getClassName().toString()+"\n");
				}
			}
		//	bw.write("Содержимое файла на SD");
			// закрываем поток
			bw.close();
			Log.d(LOG_TAG, "Файл записан на SD: " + sdFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
