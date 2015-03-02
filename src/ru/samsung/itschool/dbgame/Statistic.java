package ru.samsung.itschool.dbgame;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Statistic extends Activity {
	private DBManager dbManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistic);
		dbManager = DBManager.getInstance(this);

		TextView min = (TextView) findViewById(R.id.min);
		TextView max = (TextView) findViewById(R.id.max);
		TextView sum = (TextView) findViewById(R.id.sum);
		TextView count = (TextView) findViewById(R.id.count);
		TextView plCount = (TextView) findViewById(R.id.plCount);

		min.setText("" + dbManager.getMin(null));
		max.setText("" + dbManager.getMax(null));
		sum.setText("" + dbManager.getSum(null));
		count.setText("" + dbManager.getCount(null));
		plCount.setText("" + dbManager.getPlCount());
	}
}
