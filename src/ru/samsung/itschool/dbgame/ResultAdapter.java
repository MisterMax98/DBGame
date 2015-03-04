package ru.samsung.itschool.dbgame;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ResultAdapter extends ArrayAdapter<Result> {
	Context context;

	public ResultAdapter(Context context, ArrayList<Result> objects) {
		super(context, R.layout.res_row, objects);
		this.context = context;
	}

	public View getView(int pos, View v, ViewGroup vg) {
		LayoutInflater infl = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (v == null)
			v = infl.inflate(R.layout.res_row, null);
		Result result = this.getItem(pos);
		TextView tv = (TextView) v.findViewById(R.id.result);
		tv.setText("" + result.score);
		TextView tv2 = (TextView) v.findViewById(R.id.player);
		tv2.setText("" + result.name);
		return v;
	}

}
