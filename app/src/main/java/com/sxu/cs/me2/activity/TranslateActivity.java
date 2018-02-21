package com.sxu.cs.me2.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxu.cs.me2.R;

public class TranslateActivity extends Activity {
	private TextView tv_title_tran;
	private String text;
	private String title_tran;
	private TextView tv_tran_title;
	private TextView tv_tran_body;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tran);
		String book = getIntent().getStringExtra("book");
		String title = getIntent().getStringExtra("title");
		String db = getIntent().getStringExtra("db");
		getInfo(db,book, title);
		tv_title_tran = (TextView) findViewById(R.id.tv_title_tran);
		tv_tran_body = (TextView) findViewById(R.id.tv_tran_body);
		tv_tran_title = (TextView) findViewById(R.id.tv_tran_title);
		tv_title_tran.setText(title);
		tv_tran_title.setText(title_tran);
		tv_tran_body.setText(text);

	}

	private void getInfo(String db,String book, String title) {
		SQLiteDatabase database = SQLiteDatabase.openDatabase(
				getDatabasePath(db).toString(), null,
				SQLiteDatabase.OPEN_READONLY);
		Cursor cursor = database.rawQuery("select title_tran,body_tran from "
				+ book + " where title = ?", new String[] { title });
		while (cursor.moveToNext()) {
			title_tran = cursor.getString(0);
			text = cursor.getString(1);
		}
	}
}
