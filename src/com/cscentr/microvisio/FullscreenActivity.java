package com.cscentr.microvisio;

import com.cscentr.microvisio.draw.Draw;
import com.cscentr.microvisio.model.Model;
import com.cscentr.microvisio.readWrite.Reader;
import com.cscentr.microvisio.readWrite.Writer;

import android.app.Activity;
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
		public FileDialogDepends refresh(String filename) {
			read.readFileSD(filename, model);
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
		case R.id.help:
			showHelp();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		model = new Model();
		read = new Reader();
		write = new Writer();

	   // Resources res = this.getResources();
		//read.readFromTxtFile(res.openRawResource(R.raw.textfile), model);
	   // write.writeFileSD("Test1.txt", model);
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
