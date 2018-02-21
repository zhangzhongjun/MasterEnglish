package com.sxu.cs.me2.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxu.cs.me2.R;

public class SettingItem2 extends RelativeLayout {
	private ImageView iv_icon_setting;
	private TextView tv_text_setting;


	private void init(Context context) {
		inflate(context, R.layout.item2_setting, this);
		iv_icon_setting = (ImageView) findViewById(R.id.iv_icon_setting);
		tv_text_setting = (TextView) findViewById(R.id.tv_text_setting);
		System.out.println(iv_icon_setting==null);
		System.out.println(tv_text_setting==null);

	}

	public SettingItem2(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public SettingItem2(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public SettingItem2(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
		int text = attrs.getAttributeResourceValue(
				"http://schemas.android.com/apk/res/com.sxu.cs.me2", "myText",0);
		int icon = attrs.getAttributeResourceValue(
				"http://schemas.android.com/apk/res/com.sxu.cs.me2", "myIcon",
				R.drawable.takeout_ic_feedback);
		iv_icon_setting.setImageResource(icon);
		System.out.println(text);
		tv_text_setting.setText(getResources().getString(text));
	}

}
