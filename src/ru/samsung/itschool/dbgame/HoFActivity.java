package ru.samsung.itschool.dbgame;

import java.util.ArrayList;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class HoFActivity extends Activity implements
		android.view.View.OnClickListener, OnItemClickListener {

	private DBManager dbManager;
	Button clr;
	ListView lw;
	TextView restv;
	ArrayList<Result> results;
	ResultAdapter ra;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_hof);
		dbManager = DBManager.getInstance(this);
		clr = (Button) findViewById(R.id.clr);
		lw = (ListView) findViewById(R.id.LV);
		clr.setOnClickListener(this);
		loadRes();
		final Context c = this;

		// ArrayAdapter<String> ad = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, );
		ra = new ResultAdapter(this, results);
		lw.setAdapter(ra);
		lw.setOnItemClickListener(this);
		ra.notifyDataSetInvalidated();

	}

	void loadRes() {
		results = dbManager.getAllResults();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.clr:
			dbManager.clr();
			loadRes();
			lw.setAdapter(new ResultAdapter(this, results));
			break;

		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent in = new Intent(this, PlayerStat.class);
		in.putExtra("player", results.get(position).name);
		startActivity(in);

	}

}
