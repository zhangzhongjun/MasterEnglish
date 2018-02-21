package com.sxu.cs.me2.activity;



import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TextView;

import com.sxu.cs.me2.R;

public class BottomTabActivity extends TabActivity {
    /** Called when the activity is first created. */
    private TabHost tabHost;

    private RadioButton main_tab_article;
    private RadioButton main_tab_translate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bottomtab);

        Intent targetIntent = getIntent();

        main_tab_article = (RadioButton) findViewById(R.id.main_tab_article);
        main_tab_translate = (RadioButton) findViewById(R.id.main_tab_translate);

        tabHost=this.getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent=new Intent().setClass(this, ArticleActivity.class).putExtras(targetIntent);
        spec=tabHost.newTabSpec("课文").setIndicator("课文").setContent(intent);
        tabHost.addTab(spec);


        intent=new Intent().setClass(this, TranslateActivity.class).putExtras(targetIntent);
        spec=tabHost.newTabSpec("翻译").setIndicator("翻译").setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);

        RadioGroup radioGroup=(RadioGroup) this.findViewById(R.id.main_tab_group);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_tab_article://添加考试
                        tabHost.setCurrentTabByTag("课文");
                        break;
                    case R.id.main_tab_translate://我的考试
                        tabHost.setCurrentTabByTag("翻译");
                        break;
                    default:
                        //tabHost.setCurrentTabByTag("我的考试");
                        break;
                }
            }
        });
    }
}
