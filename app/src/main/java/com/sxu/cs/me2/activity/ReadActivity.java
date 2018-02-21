package com.sxu.cs.me2.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SimpleExpandableListAdapter;

import com.sxu.cs.me2.R;

public class ReadActivity extends Activity {
	private ExpandableListView el_read_artical;
	private List<Map<String, String>> groupData = new ArrayList<Map<String,String>>();
	private List<ArrayList<Map<String, String>>> childData = new ArrayList<ArrayList<Map<String,String>>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read);
		getInfo();
		el_read_artical = (ExpandableListView) findViewById(R.id.el_read_artical);

		el_read_artical.setAdapter(new SimpleExpandableListAdapter(this,
				groupData, android.R.layout.simple_expandable_list_item_1,
				new String[] { "book" }, new int[] { android.R.id.text1 },
				childData, R.layout.item_read,
				new String[] { "title", "description" }, new int[] {
				R.id.tv_title_read, R.id.tv_description_read}));

		el_read_artical.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
										int groupPosition, int childPosition, long id) {
				
				String book = groupData.get(groupPosition).get("book");
				String title = childData.get(groupPosition).get(childPosition).get("title");
				Intent intent = new Intent(ReadActivity.this,BottomTabActivity.class).putExtra("book",book ).putExtra("title", title).putExtra("db", "read.db");
				System.out.println(book+" "+title);
				startActivity(intent);
				overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out);

				return true;
			}
		});
	}

	private void getInfo() {
		SQLiteDatabase database = SQLiteDatabase.openDatabase(
				getDatabasePath("read.db").toString(), null,
				SQLiteDatabase.OPEN_READONLY);
		Cursor cursor = database.rawQuery(
				"select name from sqlite_master where type= ?",
				new String[] { "table" });
		while (cursor.moveToNext()) {// 所有的书
			String book = cursor.getString(0);
			System.out.println("正在查询："+book);
			Map<String, String> curGroupMap = new HashMap<String, String>();
			curGroupMap.put("book", book);
			groupData.add(curGroupMap);
			Cursor artCursor = database.rawQuery(
					"select title,description  from " + book, null);
			ArrayList<Map<String, String>> children = new ArrayList<Map<String, String>>();
			while (artCursor.moveToNext()) {// 一本书的所有title和description
				String title = artCursor.getString(0);
				String description = artCursor.getString(1);
				Map<String, String> curChildMap = new HashMap<String, String>();
				curChildMap.put("title", title);
				curChildMap.put("description", description);
				children.add(curChildMap);
			}
			artCursor.close();
			childData.add(children);
		}
		cursor.close();
	}
}
