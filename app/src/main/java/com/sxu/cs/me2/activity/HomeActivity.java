package com.sxu.cs.me2.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxu.cs.me2.R;

public class HomeActivity extends Activity {
	private GridView gv_home;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		gv_home = (GridView) findViewById(R.id.gv_home);
		gv_home.setAdapter(new HomeAdapter());
		gv_home.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				
				switch (position) {
					case 1:
						startActivity(new Intent(HomeActivity.this,ReadActivity.class));
						break;
					case 3:
						startActivity(new Intent(HomeActivity.this,Read2Activity.class));
						break;
					case 5:
						startActivity(new Intent(HomeActivity.this,SettingActivity.class));
						break;

					default:
						break;
				}
			}
		});
	}

	@Override
	public void onBackPressed() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("确定退出").setIcon(R.drawable.ic_launcher)
				.setMessage("立即退出程序？").setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				HomeActivity.this.finish();
			}
		}).setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				//dialog.cancel();
			}
		}).show();

	}

	class HomeAdapter extends BaseAdapter {


		@Override
		public int getCount() {

			return 9;
		}

		@Override
		public Object getItem(int position) {

			return null;
		}

		@Override
		public long getItemId(int position) {

			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View view = View.inflate(HomeActivity.this, R.layout.home_list,
					null);
			ImageView iv_home_list_icon = (ImageView) view
					.findViewById(R.id.iv_home_list_icon);
			TextView tv_home_list_item = (TextView) view
					.findViewById(R.id.tv_home_list_item);
			switch (position) {
				case 1:
					iv_home_list_icon.setImageResource(R.drawable.read);
					tv_home_list_item.setText("新视野");
					break;
				case 3:
					iv_home_list_icon.setImageResource(R.drawable.guwen);
					tv_home_list_item.setText("新概念");
					break;
				case 5:
					iv_home_list_icon.setImageResource(R.drawable.setting);
					tv_home_list_item.setText("设置中心");
					break;
				default:
					view.setVisibility(View.GONE);
					break;
			}

			return view;
		}
	}

}
