package com.sxu.cs.me2.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxu.cs.me2.R;
import com.sxu.cs.me2.bean.Word;
import com.sxu.cs.me2.util.StreamTools;
import com.sxu.cs.me2.util.WordService;

public class ArticleActivity extends Activity {
    private TextView tv_title_article;
    private String text;
    private TextView tv_article_title;
    private TextView tv_article_body;

    private ClipboardManager mClip;
    private mClipListener mListener;

    private TextView tv_article_yuanci;
    private TextView tv_article_yinbiao;
    private TextView tv_article_example;

    private RelativeLayout ll_article_example;

    private ScrollView sv_article;

    private SharedPreferences sp;

    public void toTop(View c) {
        sv_article.fullScroll(ScrollView.FOCUS_UP);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        final String book = getIntent().getStringExtra("book");
        title = getIntent().getStringExtra("title");
        String db = getIntent().getStringExtra("db");
        getInfo(db, book, title);
        tv_title_article = (TextView) findViewById(R.id.tv_title_article);
        tv_article_body = (TextView) findViewById(R.id.tv_article_body);
        tv_article_title = (TextView) findViewById(R.id.tv_article_title);
        tv_title_article.setText(title);
        tv_article_title.setText(title);
        tv_article_body.setText(text);

        tv_article_yuanci = (TextView) findViewById(R.id.tv_article_yuanci);
        tv_article_yinbiao = (TextView) findViewById(R.id.tv_article_yinbiao);
        tv_article_example = (TextView) findViewById(R.id.tv_article_example);

        // 设置音标字体
        Typeface mTypeface = Typeface.createFromAsset(getAssets(),
                "font/SEGOEUI.TTF");
        tv_article_yinbiao.setTypeface(mTypeface);

        mListener = new mClipListener();
        mClip = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        mClip.addPrimaryClipChangedListener(mListener);

        ll_article_example = (RelativeLayout) findViewById(R.id.ll_article_example);

        sv_article = (ScrollView) findViewById(R.id.sv_article);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                sp = getSharedPreferences("config", MODE_PRIVATE);
                if (title.equals(sp.getString("lastBook", ""))) {
                    System.out.println("lastScrollY: "
                            + sp.getInt("lastScrollY", 0));
                    sv_article.smoothScrollTo(0, sp.getInt("lastScrollY", 0));
                }
            }
        }, 100);

    }

    @Override
    protected void onStop() {

        Editor editor = sp.edit();
        editor.putString("lastBook", title);
        editor.putInt("lastScrollY", sv_article.getScrollY());
        editor.commit();
        super.onStop();
    }

    private void getInfo(String db, String book, String title) {
        SQLiteDatabase database = SQLiteDatabase.openDatabase(
                getDatabasePath(db).toString(), null,
                SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = database.rawQuery("select body from " + book
                + " where title = ?", new String[]{title});
        while (cursor.moveToNext()) {
            text = cursor.getString(0);
        }
    }

    @Override
    public void onBackPressed() {

        // super.onBackPressed();
        if (ll_article_example.getVisibility() == View.VISIBLE)
            ll_article_example.setVisibility(View.GONE);
        else {
            overridePendingTransition(R.anim.left_to_right_in,
                    R.anim.left_to_right_out);
            finish();
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        mClip.removePrimaryClipChangedListener(mListener);
    }

    private final String baseUrl1 = "http://fy.webxml.com.cn/webservices/EnglishChinese.asmx/TranslatorString?wordKey=";

    private class mClipListener implements OnPrimaryClipChangedListener {

        @Override
        public void onPrimaryClipChanged() {

            System.out.println(mClip.getText());
            final String word = mClip.getText().toString();
            new Thread() {
                private Message msg;

                @Override
                public void run() {

                    super.run();
                    try {
                        URL url = new URL(baseUrl1 + word);
                        // 联网
                        HttpURLConnection conn = (HttpURLConnection) url
                                .openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(4000);
                        conn.setReadTimeout(5000);
                        int code = conn.getResponseCode();
                        System.out.println(code);
                        if (code == 200) {
                            InputStream in = conn.getInputStream();
                            // String result = StreamTools.readFromStream(in);
                            // System.out.println(result);
                            Word word = WordService.parseWord(in);
                            System.out.println(word);
                            msg = new Message();
                            msg.obj = word;
                            handler.sendMessage(msg);
                        }
                    } catch (MalformedURLException e) {

                        e.printStackTrace();
                        System.out.println("url 错误");
                        msg = new Message();
                        msg.obj = "url 错误";
                        handler.sendMessage(msg);
                    } catch (IOException e) {

                        e.printStackTrace();
                        System.out.println("联网错误");
                        msg = new Message();
                        msg.obj = "联网错误";
                        handler.sendMessage(msg);
                    }
                }

            }.start();
        }
    }

    private void RefreshUI(Word word) {

        ll_article_example.setVisibility(View.VISIBLE);
        tv_article_yuanci.setText(" " + word.getYuanCi() + " ");
        tv_article_yinbiao.setText(" " + word.getYinBiao() + " ");
        tv_article_example.setText(" " + word.getExamples() + " ");
    }

    private String duYin;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            if (msg.obj.toString().equals("url 错误"))
                Toast.makeText(getApplicationContext(), "url错误", Toast.LENGTH_SHORT).show();
            else if (msg.obj.toString().equals("联网错误"))
                Toast.makeText(getApplicationContext(), "联网错误", Toast.LENGTH_SHORT).show();
            else {
                Word word = (Word) msg.obj;
                RefreshUI(word);
                duYin = word.getDuYin();
            }
        }

    };
    private MediaPlayer mediaPlayer;
    private String title;

    public void duYin(View v) {

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource("http://fy.webxml.com.cn/sound/" + duYin);
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {

                    mediaPlayer.release();
                }
            });
        } catch (IllegalArgumentException e) {

            e.printStackTrace();
        } catch (SecurityException e) {

            e.printStackTrace();
        } catch (IllegalStateException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}
