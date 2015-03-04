package ru.samsung.itschool.dbgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PlayerStat extends Activity {
	String name;

	DBManager dbManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_stat);
		dbManager = DBManager.getInstance(this);

		Intent in = this.getIntent();
		name = in.getExtras().getString("player");

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

		pl.setText(pl.getText() + " " + name);
		min.setText("" + dbManager.getMin(name));
		max.setText("" + dbManager.getMax(name));
		sum.setText("" + dbManager.getSum(name));
		count.setText("" + dbManager.getCount(name));
		chetT.setText("" + chet + "%");
		nechetT.setText("" + nechet + "%");
	}
}
