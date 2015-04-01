package ru.samsung.itschool.dbgame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayerStat extends Activity {
	String name;

	DBManager dbManager;
	String userPics_path = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_stat);
		dbManager = DBManager.getInstance(this);

		Intent in = this.getIntent();
		name = in.getExtras().getString("player");
		userPics_path = this.getExternalFilesDir(null).getAbsolutePath()
				+ "/userpics";
		updateInfo();
	}

	void updateInfo() {
		ContentValues cv;
		cv = dbManager.loadInfo(name);
		if (cv != null) {
			TextView phone = (TextView) findViewById(R.id.phone);
			phone.setText(cv.getAsString("PHONE"));
		}
		ImageView iv = (ImageView) findViewById(R.id.pic);
		Bitmap bmp = loadPic();
		if (bmp != null)
			iv.setImageBitmap(bmp);
		TextView pl = (TextView) findViewById(R.id.Player);
		TextView min = (TextView) findViewById(R.id.min);
		TextView max = (TextView) findViewById(R.id.max);
		TextView sum = (TextView) findViewById(R.id.sum);
		TextView count = (TextView) findViewById(R.id.count);
		TextView chetT = (TextView) findViewById(R.id.chet);
		TextView nechetT = (TextView) findViewById(R.id.nechet);
		TextView plCount = (TextView) findViewById(R.id.plCount);

		double chet = dbManager.getChet(name);
		double nechet;
		if (chet == -1) {
			chet = 0;
			nechet = 0;
		} else {
			nechet = 100 - chet;
		}

		pl.setText("Статистика " + name);
		min.setText("" + dbManager.getMin(name));
		max.setText("" + dbManager.getMax(name));
		sum.setText("" + dbManager.getSum(name));
		count.setText("" + dbManager.getCount(name));
		chetT.setText("" + chet + "%");
		nechetT.setText("" + nechet + "%");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_pl, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item1: {
			dbManager.deletePlSc(name);
			updateInfo();
			break;
		}
		case R.id.item2: {
			dbManager.updateInfo(name, ((TextView) findViewById(R.id.phone))
					.getText().toString(), null);
			updateInfo();
			break;
		}
		case R.id.item3: {
			getPicture(null);
			updateInfo();
			break;
		}
		}
		return true;

	}

	static final int REQUEST_IMAGE_CAPTURE = 1;

	public void getPicture(View v) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Bitmap imageBitmap = null;
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			imageBitmap = (Bitmap) extras.get("data");

		}
		String pic = "";
		if (imageBitmap != null)
			pic = savePic(imageBitmap);
		// обновить:
		dbManager.updateInfo(name, ((TextView) findViewById(R.id.phone))
				.getText().toString(), pic);
		ImageView iv = (ImageView) findViewById(R.id.pic);
		Bitmap bmp = loadPic();
		if (bmp != null)
			iv.setImageBitmap(bmp);

	}

	private String savePic(Bitmap userpic) {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File dir = new File(userPics_path);
		if (!dir.exists())
			dir.mkdirs();
		File file = new File(dir, timeStamp + ".png");
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(file);

			userpic.compress(Bitmap.CompressFormat.PNG, 85, fOut);
			fOut.flush();
			fOut.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
		return timeStamp;
	}

	private Bitmap loadPic() {
		Bitmap bmp;
		try {
			bmp = BitmapFactory.decodeFile(userPics_path + "/"
					+ dbManager.getUserPic(name) + ".png");
		} catch (Exception e) {
			bmp = null;
		}

		return bmp;
	}

}
