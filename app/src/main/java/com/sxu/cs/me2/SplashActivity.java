package com.sxu.cs.me2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sxu.cs.me2.activity.HomeActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {
	private SharedPreferences sp;
	private TextView tv_splash_hope;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		tv_splash_hope = (TextView)findViewById(R.id.tv_splash_hope);
		// 设置音标字体
		Typeface mTypeface = Typeface.createFromAsset(getAssets(),
				"font/xindi.ttf");
		tv_splash_hope.setTypeface(mTypeface);
		if(sp.getBoolean("if_hope", true)) {
			String hope = sp.getString("hope", getResources().getString(R.string.hope));
			tv_splash_hope.setText(hope);
		} else {
			tv_splash_hope.setText("");
		}
		copyDB();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				enterHome();
			}
		},3000);
	}

	private void enterHome(){
		startActivity(new Intent(this,HomeActivity.class));
		finish();
	}

	private void copyDB() {
		File file = new File("/data/data/com.sxu.cs.me2/databases/");
		if (!file.exists()) {
			file.mkdir();
		}
		file = getDatabasePath("read.db");
		if (!file.exists()) {
			try {
				copy(file, getResources().getAssets().open("read.db"));
			} catch (IOException e) {
				
				e.printStackTrace();
				Toast.makeText(this, "文件read.db不存在", Toast.LENGTH_SHORT).show();
			}
		}else{
			System.out.println("read已经存在");
		}
		file = getDatabasePath("read2.db");
		if (!file.exists()) {
			try {
				copy(file, getResources().getAssets().open("read2.db"));
			} catch (IOException e) {
				
				e.printStackTrace();
				Toast.makeText(this, "文件read2.db不存在", Toast.LENGTH_SHORT).show();
			}
		}else{
			System.out.println("read2已经存在");
		}
	}

	private void copy(File dest, InputStream database) {
		try {
			OutputStream os = new FileOutputStream(dest);
			int len;
			byte[] buf = new byte[2014];
			while ((len = database.read(buf)) != -1) {
				os.write(buf, 0, len);
			}
			os.flush();
			os.close();
			database.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			Toast.makeText(this, "数据库文件无法创建", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			
			e.printStackTrace();
			Toast.makeText(this, "copy数据库文件是发生错误", Toast.LENGTH_SHORT).show();
		}
	}

}
