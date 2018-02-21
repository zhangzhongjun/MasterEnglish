package com.sxu.cs.me2.ui;


import com.sxu.cs.me2.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItem extends RelativeLayout {
	
	private TextView tv_desc_xianshi_setting;
	private CheckBox cb_status_setting;
	
	private void init(Context context){
		inflate(context, R.layout.item_setting, this);
		tv_desc_xianshi_setting = (TextView) findViewById(R.id.tv_desc_xianshi_setting);
		cb_status_setting = (CheckBox) findViewById(R.id.cb_status_setting);
	}
	
	public void setDesc(boolean on){
		cb_status_setting.setChecked(on);
		if(on){
			tv_desc_xianshi_setting.setText(getResources().getString(R.string.on_xianshi));
		}else{
			tv_desc_xianshi_setting.setText(getResources().getString(R.string.off_xianshi));
		}
	}
	
	public boolean getDesc(){
		return cb_status_setting.isChecked();
	}

	public SettingItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public SettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public SettingItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}
	

}
