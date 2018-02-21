package com.sxu.cs.me2.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.sxu.cs.me2.R;
import com.sxu.cs.me2.ui.SettingItem;
import com.sxu.cs.me2.ui.SettingItem2;

public class SettingActivity extends Activity {

	private SettingItem si_if_on_setting;
	private EditText et_setting_zidingyi;
	private SettingItem2 si2_tuijian_setting;
	private SettingItem2 si2_gunayu_setting;

	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		et_setting_zidingyi = (EditText) findViewById(R.id.et_setting_zidingyi);
		si_if_on_setting = (SettingItem) findViewById(R.id.si_if_on_setting);

		boolean if_zidingyi = sp.getBoolean("if_hope", true);
		si_if_on_setting.setDesc(if_zidingyi);
		et_setting_zidingyi.setHint(sp.getString("hope", getResources()
				.getString(R.string.hope)));
		et_setting_zidingyi.setEnabled(if_zidingyi);

		si_if_on_setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Editor editor = sp.edit();
				editor.putBoolean("if_hope", !si_if_on_setting.getDesc());
				editor.commit();

				si_if_on_setting.setDesc(!si_if_on_setting.getDesc());
				System.out.println(si_if_on_setting.getDesc());
				et_setting_zidingyi.setEnabled(si_if_on_setting.getDesc());
			}
		});
		si2_tuijian_setting = (SettingItem2) findViewById(R.id.si2_tuijian_setting);
		si2_tuijian_setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Toast.makeText(SettingActivity.this,
						getResources().getString(R.string.tuijian_setting), 0)
						.show();
				startActivity(new Intent(Intent.ACTION_SENDTO, Uri
						.parse("smsto:")).putExtra("sms_body", getResources()
						.getString(R.string.tuijianyu_setting)));
			}
		});
		si2_gunayu_setting = (SettingItem2) findViewById(R.id.si2_gunayu_setting);
		si2_gunayu_setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				startActivity(new Intent(SettingActivity.this,
						GuanYuActivity.class));
			}
		});
	}

	@Override
	protected void onPause() {
		
		super.onPause();
		Editor editor = sp.edit();
		String str = et_setting_zidingyi.getText().toString();
		if(str.equals(""))str = et_setting_zidingyi.getHint().toString();
		System.out.println("str = "+str==null);
		editor.putString("hope",str);
		editor.commit();

	}
}
