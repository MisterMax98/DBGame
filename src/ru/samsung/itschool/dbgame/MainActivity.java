package ru.samsung.itschool.dbgame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	// Коммент!!!
	static DBManager dbManager;

	EditText playerName;
	TextView gameResult;
	ImageButton playButton;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		dbManager = DBManager.getInstance(this);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.fadin);
		gameResult = (TextView) this.findViewById(R.id.GameResult);
		playButton = (ImageButton) this.findViewById(R.id.playButton);
		playerName = (EditText) this.findViewById(R.id.playerName);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item1: {
			showHoF();
			break;
		}
		case R.id.item2: {
			showStat();
			break;
		}
		}
		return true;

	}

	public void play(View v) {

		Animation play = AnimationUtils.loadAnimation(this, R.anim.fadin);

		play.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				playButton.setClickable(false);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				playButton.setClickable(true);
				dbManager.addResult(MainActivity.this.playerName.getText()
						.toString(), Integer
						.parseInt(MainActivity.this.gameResult.getText()
								.toString()));
			}
		});

		int ch = ((int) (Math.random() * 1001));
		if (ch % 2 == 1) {
			gameResult.setTextColor(Color.RED);
		} else {
			gameResult.setTextColor(Color.GREEN);
		}
		gameResult.setText(ch + "");
		gameResult.startAnimation(play);

	}

	public void showHoF() {
		startActivity(new Intent(this, HoFActivity.class));
	}

	public void showStat() {
		startActivity(new Intent(this, Statistic.class));
	}

}
