package ru.samsung.itschool.dbgame;

import java.util.ArrayList;
import java.util.LinkedList;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HoFActivity extends Activity implements
		android.view.View.OnClickListener {

	private DBManager dbManager;
	Button clr;
	ListView lw;
	TextView restv;
	LinkedList<String> name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_hof);
		dbManager = DBManager.getInstance(this);
		clr = (Button) findViewById(R.id.clr);
		lw = (ListView) findViewById(R.id.LV);
		clr.setOnClickListener(this);
		loadRes();

		ArrayAdapter<String> ad = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, name);

		lw.setAdapter(ad);

		// restv = (TextView) this.findViewById(R.id.results);
		// loadRes();

	}

	void loadRes() {
		name = new LinkedList<String>();
		ArrayList<Result> results = dbManager.getAllResults();
		String resStr = "";
		for (Result res : results) {
			resStr = res.name + ": " + res.score + "\n";
			name.add(resStr);
		}
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
