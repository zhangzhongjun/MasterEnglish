package com.sxu.cs.me2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sxu.cs.me2.R;

public class GuanYuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guanyu);
		
		
	}
	public void back(View c){
		finish();
		overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
	}
	public void team(View v){
		startActivity(new Intent(this,TeamActivity.class));
	}
	public void help(View v){
		startActivity(new Intent(this,HelpActivity.class));		
	}
}
