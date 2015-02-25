package ru.samsung.itschool.dbgame;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	/*
	 * TABLES: ------- RESULTS SCORE INTEGER USER VARCHAR
	 */
	private Context context;
	private String DB_NAME = "game.db";

	private SQLiteDatabase db;

	private static DBManager dbManager;

	public static DBManager getInstance(Context context) {
		if (dbManager == null) {
			dbManager = new DBManager(context);
		}
		return dbManager;
	}

	private DBManager(Context context) {
		this.context = context;
		db = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
		createTablesIfNeedBe();
	}

	void addResult(String username, int score) {
		db.execSQL("INSERT INTO RESULTS VALUES ('" + username + "', " + score
				+ ");");
	}

	ArrayList<Result> getAllResults() {

		ArrayList<Result> data = new ArrayList<Result>();
		Cursor cursor = db.rawQuery("SELECT * FROM RESULTS;", null);
		boolean hasMoreData = cursor.moveToFirst();

		while (hasMoreData) {
			String name = cursor.getString(cursor.getColumnIndex("USERNAME"));
			int score = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex("SCORE")));
			data.add(new Result(name, score));
			hasMoreData = cursor.moveToNext();
		}

		return data;
	}

	void clr() {
		db.delete("RESULTS", null, null);
	}

	int getSum() {

		Cursor cursor = db.rawQuery("SELECT SUM (SCORE) FROM RESULTS;", null);
		cursor.moveToFirst();
		int score = cursor.getInt(0);

		return score;
	}

	int getCount() {

		Cursor cursor = db.rawQuery("SELECT COUNT (SCORE) FROM RESULTS;", null);
		cursor.moveToFirst();
		int score = cursor.getInt(0);

		return score;
	}

	int getPlCount() {

		Cursor cursor = db.rawQuery("SELECT COUNT (DISTINCT USERNAME) FROM RESULTS;", null);
		cursor.moveToFirst();
		int score = cursor.getInt(0);

		return score;
	}

	int getMax() {

		Cursor cursor = db.rawQuery("SELECT MAX (SCORE) FROM RESULTS;", null);
		cursor.moveToFirst();
		int score = cursor.getInt(0);

		return score;
	}

	int getMin() {

		Cursor cursor = db.rawQuery("SELECT MIN (SCORE) FROM RESULTS;", null);
		cursor.moveToFirst();
		int score = cursor.getInt(0);

		return score;
	}

	private void createTablesIfNeedBe() {
		db.execSQL("CREATE TABLE IF NOT EXISTS RESULTS (USERNAME TEXT, SCORE INTEGER);");
	}

	private boolean dbExist() {
		File dbFile = context.getDatabasePath(DB_NAME);
		return dbFile.exists();
	}

}
