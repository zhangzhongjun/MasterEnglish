package com.sxu.cs.me2.util;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.sxu.cs.me2.bean.Word;

public class WordService {
	private final static Word word = new Word();

	public static Word parseWord(InputStream in) {
		XmlPullParser mParse;
		try {
			mParse = Xml.newPullParser();
			mParse.setInput(in, "utf-8");

			int event = mParse.getEventType();
			int cur = 1;
			while (true) {
				if (event == XmlPullParser.START_TAG) {
					if (mParse.getName().trim().equals("string") && cur == 1) {
						String yuanCi = mParse.nextText().toString();
						word.setYuanCi(yuanCi);
						cur++;
					} else if (mParse.getName().trim().equals("string")
							&& cur == 2) {
						String yinBiao = mParse.nextText().toString();
						word.setYinBiao(yinBiao);
						cur++;
					} else if (mParse.getName().trim().equals("string")
							&& cur == 3) {
						String example = mParse.nextText().toString();
						if (!example.equals("")) {
							word.setExamples(example);
							cur++;
						}

						System.out.println("设置 example");
						System.out.println("example = " + example);

					} else if (mParse.getName().trim().equals("string")
							&& cur == 4) {
						String duYin = mParse.nextText().toString();
						word.setDuYin(duYin);
					}

				} else if (event == XmlPullParser.END_TAG) {
					if (mParse.getName().trim().equals("ArrayOfString")) {
						// cur = 0;
						break;
					}
				}
				event = mParse.next();
			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
			System.out.println("获取解析器时发生异常");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return word;
	}
}
