package com.cscentr.microvisio.readWrite;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
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

	public void readFileSD(String filename, Model model)
			throws FileNotFoundException {
		// провер€ем доступность SD
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Log.d(LOG_TAG,
					"SD-карта не доступна: "
							+ Environment.getExternalStorageState());
			return;
		}
		// получаем путь к SD
		File sdPath = Environment.getExternalStorageDirectory();
		// добавл€ем свой каталог к пути
		sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
		// формируем объект File, который содержит путь к файлу
		File sdFile = new File(sdPath, filename);
		// открываем поток дл€ чтени€
		Scanner scanner = new Scanner(sdFile);
		// читаем содержимое
		while (scanner.hasNextLine()) {
			if (scanner.hasNext()) {
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
					int color = scanner.nextInt();
					Ellipse ellipse = new Ellipse(centre, rx, ry, size, color);
					model.addFigure(ellipse);
				} else if (st.equals("Rectangle")) {
					Point centre = new Point(scanner.nextInt(),
							scanner.nextInt());
					int rx = scanner.nextInt();
					int ry = scanner.nextInt();
					int size = scanner.nextInt();
					int color = scanner.nextInt();
					Rectangle rectangle = new Rectangle(centre, rx, ry, size,
							color);
					model.addFigure(rectangle);
				} else if (st.equals("Line")) {
					int firstID = scanner.nextInt();
					int secondID = scanner.nextInt();
					int size = scanner.nextInt();
					int color = scanner.nextInt();
					Line line = new Line(model.figureList.get(firstID),
							model.figureList.get(secondID), size, color);
					model.addFigure(line);
				} else if (st.equals("Arrow")) {
					int firstID = scanner.nextInt();
					int secondID = scanner.nextInt();
					int size = scanner.nextInt();
					int color = scanner.nextInt();
					Line arrow = new Line(model.figureList.get(firstID),
							model.figureList.get(secondID), size, color);
					arrow.setArrow(1);
					model.addFigure(arrow);
				}
			} else
				break;
		}
	}

	public void readFromTxtFile(InputStream inputStream, Model model)
			throws FileNotFoundException {
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
				int color = scanner.nextInt();
				Ellipse ellipse = new Ellipse(centre, rx, ry, size, color);
				model.addFigure(ellipse);
			} else if (st.equals("Rectangle")) {
				Point centre = new Point(scanner.nextInt(), scanner.nextInt());
				int rx = scanner.nextInt();
				int ry = scanner.nextInt();
				int size = scanner.nextInt();
				int color = scanner.nextInt();
				Rectangle rectangle = new Rectangle(centre, rx, ry, size, color);
				model.addFigure(rectangle);
			} else if (st.equals("Line")) {
				int firstID = scanner.nextInt();
				int secondID = scanner.nextInt();
				int size = scanner.nextInt();
				int color = scanner.nextInt();
				Line line = new Line(model.figureList.get(firstID),
						model.figureList.get(secondID), size, color);
				model.addFigure(line);
			}
		}
	}

	public void readXmlFile(String filename, Model model, Context context)
			throws ParserConfigurationException, IOException, SAXException {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		fis = context.openFileInput(filename);
		isr = new InputStreamReader(fis);
		char[] inputBuffer = new char[fis.available()];
		isr.read(inputBuffer);
		String data = new String(inputBuffer);
		isr.close();
		fis.close();

		/*
		 * converting the String data to XML format so that the DOM parser
		 * understand it as an XML input.
		 */
		InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));
		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		NodeList figure, items = null;
		Document dom;

		dbf = DocumentBuilderFactory.newInstance();
		db = dbf.newDocumentBuilder();
		dom = db.parse(is);
		// normalize the document
		dom.getDocumentElement().normalize();

		figure = dom.getElementsByTagName("Line");
		for (int i = 0; i < figure.getLength(); i++) {
			Line line;// = new Line(line, line, i, i);
			Node nNode = figure.item(i);
			// items = dom.getElementsByTagName("Line");
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				NodeList firstNameList = eElement.getElementsByTagName("id1");
				Element firstNameElement = (Element) firstNameList.item(0);
				NodeList textFNList = firstNameElement.getChildNodes();
				int ID1 = Integer.valueOf(((Node) textFNList.item(0))
						.getNodeValue().trim());
				firstNameList = eElement.getElementsByTagName("id2");
				firstNameElement = (Element) firstNameList.item(0);
				textFNList = firstNameElement.getChildNodes();
				int ID2 = Integer.valueOf(((Node) textFNList.item(0))
						.getNodeValue().trim());
				firstNameList = eElement.getElementsByTagName("size");
				firstNameElement = (Element) firstNameList.item(0);
				textFNList = firstNameElement.getChildNodes();
				int size = Integer.valueOf(((Node) textFNList.item(0))
						.getNodeValue().trim());
				firstNameList = eElement.getElementsByTagName("color");
				firstNameElement = (Element) firstNameList.item(0);
				textFNList = firstNameElement.getChildNodes();
				int color = Integer.valueOf(((Node) textFNList.item(0))
						.getNodeValue().trim());
				line = new Line(model.figureList.get(ID1),
						model.figureList.get(ID2), size, color);
			}
		}
		/*figure = dom.getElementsByTagName("Ellipse");
		for (int i = 0; i < figure.getLength(); i++) {
			Ellipse ellipse;// = new Line(line, line, i, i);
			Node nNode = figure.item(i);
			// items = dom.getElementsByTagName("Line");
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				NodeList firstNameList = eElement.getElementsByTagName("id1");
				Element firstNameElement = (Element) firstNameList.item(0);
				NodeList textFNList = firstNameElement.getChildNodes();
				int ID1 = Integer.valueOf(((Node) textFNList.item(0))
						.getNodeValue().trim());
				firstNameList = eElement.getElementsByTagName("id2");
				firstNameElement = (Element) firstNameList.item(0);
				textFNList = firstNameElement.getChildNodes();
				int ID2 = Integer.valueOf(((Node) textFNList.item(0))
						.getNodeValue().trim());
				firstNameList = eElement.getElementsByTagName("size");
				firstNameElement = (Element) firstNameList.item(0);
				textFNList = firstNameElement.getChildNodes();
				int size = Integer.valueOf(((Node) textFNList.item(0))
						.getNodeValue().trim());
				firstNameList = eElement.getElementsByTagName("color");
				firstNameElement = (Element) firstNameList.item(0);
				textFNList = firstNameElement.getChildNodes();
				int color = Integer.valueOf(((Node) textFNList.item(0))
						.getNodeValue().trim());
				line = new Line(model.figureList.get(ID1),
						model.figureList.get(ID2), size, color);
			}
		}*/
	}

}
