package com.cscentr.microvisio;

import com.cscentr.microvisio.draw.Draw;
import com.cscentr.microvisio.model.Model;
import com.cscentr.microvisio.readWrite.Reader;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class FullscreenActivity extends Activity {
	// MY

	Model model;
	Reader read;

	// MY

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		model = new Model();
		read = new Reader();
		Resources res = this.getResources();
		read.readFromTxtFile(res.openRawResource(R.raw.textfile), model);
		// Узнаем размеры экрана из ресурсов
		DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
		Draw d = new Draw(this, model, displaymetrics.widthPixels,
				displaymetrics.heightPixels);
		setContentView(d);
		// setContentView(R.layout.activity_fullscreen);

	}

}
