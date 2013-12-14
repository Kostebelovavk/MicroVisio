package com.cscentr.microvisio;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.cscentr.microvisio.draw.Draw;
import com.cscentr.microvisio.model.Model;
import com.cscentr.microvisio.readWrite.Reader;
import com.cscentr.microvisio.readWrite.Writer;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class FullscreenActivity extends Activity {
	// MY

	Model model;
	Reader read;
	Writer write;
	Draw d;

	public class FileDialogDepends {
		public FileDialogDepends refresh(String filename){
			if (filename.contains("txt")) {
			try {
				read.readFileSD(filename, model);
			}
			catch (FileNotFoundException e) {
				Toast toast = Toast.makeText(getApplicationContext(), "Файл не найден",Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			}
			//else
				//read.readXmlFile(filename, model, getContext());
			// refresh();
			return this;
		}

		public FileDialogDepends refresh() {
			refresh();
			return this;
		}
	}

	// MY
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.micro_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Операции для выбранного пункта меню
		switch (item.getItemId()) {
		case R.id.mode:
			mode();
			return true;
		case R.id.color:
			color();
			return true;
		case R.id.openDialog:
			openDialog();
			return true;
		case R.id.saveDialog:
			try {
				saveDialog();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		case R.id.help:
			showHelp();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void saveDialog() throws IllegalArgumentException, IllegalStateException, IOException {
		// TODO Auto-generated method stub
		write.writeXmlFile("text1.xml", model, getContext());
	}

	private void openDialog() {
		model = new Model();
	    new FileDialog(this).openFileDialog(new FileDialogDepends());
	    DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
		d = new Draw(this, model, displaymetrics.widthPixels,
				displaymetrics.heightPixels);
		setContentView(d);
	}

	private void mode() {
		// TODO Auto-generated method stub
		String mode;
		if (d.getDrawingMode()) {
			mode = "Режим рисования выключен";
			d.setgetDrawingMode(false);
		} else {
			mode = "Режим рисования включен";
			d.setgetDrawingMode(true);
		}
		Toast toast = Toast.makeText(getApplicationContext(), mode,Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	private void color() {
		// TODO Auto-generated method stub

	}

	private void showHelp() {
		// TODO Auto-generated method stub

	}
	
	public Context getContext(){
		return context;
	}
	public void setContext(Context context1){
		context =  context1;
	}
	private Context context;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		model = new Model();
		read = new Reader();
		write = new Writer();

		setContext(this);
		try {
		Resources res = this.getResources();
		read.readFromTxtFile(res.openRawResource(R.raw.textfile), model);
		}
		catch (FileNotFoundException e) {
			Toast toast = Toast.makeText(getApplicationContext(), "Файл не найден",Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
		
	    //write.writeFileSD("Test1.txt", model);
		//model = new Model();
	   // new FileDialog(this).openFileDialog(new FileDialogDepends());
		// Узнаем размеры экрана из ресурсов
		DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
		d = new Draw(this, model, displaymetrics.widthPixels,
				displaymetrics.heightPixels);
		setContentView(d);
		// setContentView(R.layout.activity_fullscreen);

	}

}
