package com.cscentr.microvisio;

import com.cscentr.microvisio.draw.Draw;
import com.cscentr.microvisio.model.Model;
import com.cscentr.microvisio.readWrite.Reader;
import com.cscentr.microvisio.readWrite.Writer;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class FullscreenActivity extends Activity {
	// MY

	Model model;
	Reader read;
	Writer write;
	
	 public class FileDialogDepends{
		    public FileDialogDepends refresh(String filename){
		       read.readFileSD(filename, model);
		     // refresh();
		       return this;
		    }
		    public FileDialogDepends refresh(){
		        refresh();
		       return this;
		    }
		 }
	 
	// MY

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		model = new Model();
		read = new Reader();
		write = new Writer();
		
		Resources res = this.getResources();
		read.readFromTxtFile(res.openRawResource(R.raw.textfile), model);
		write.writeFileSD("Test1.txt", model);
		model = new Model();
		new FileDialog(this).openFileDialog(new FileDialogDepends());
		// Узнаем размеры экрана из ресурсов
		DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
		Draw d = new Draw(this, model, displaymetrics.widthPixels,
				displaymetrics.heightPixels);
		setContentView(d);
		// setContentView(R.layout.activity_fullscreen);

	}

}
