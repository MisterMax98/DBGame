package ru.samsung.itschool.dbgame;

import java.util.ArrayList;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HoFActivity extends Activity implements
		android.view.View.OnClickListener {

	private DBManager dbManager;
	Button clr;
	TextView restv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ho_f);
		dbManager = DBManager.getInstance(this);
		clr = (Button) findViewById(R.id.clr);
		restv = (TextView) this.findViewById(R.id.results);
		loadRes();

		clr.setOnClickListener(this);
	}

	void loadRes() {
		ArrayList<Result> results = dbManager.getAllResults();
		String resStr = "";
		for (Result res : results) {
			resStr += res.name + ": " + res.score + "\n";
		}
		restv.setText(resStr);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.clr:
			dbManager.clr();
			loadRes();
			break;

		default:
			break;
		}

	}

}
