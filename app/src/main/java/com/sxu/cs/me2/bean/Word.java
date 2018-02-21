package com.sxu.cs.me2.bean;

public class Word {
	private String yuanCi;
	private String yinBiao;
	private String examples;
	private String duYin;

	public String getYuanCi() {
		return yuanCi;
	}

	public void setYuanCi(String yuanCi) {
		this.yuanCi = yuanCi;
	}

	public String getYinBiao() {
		return yinBiao;
	}

	public void setYinBiao(String yinBiao) {
		this.yinBiao = yinBiao;
	}

	public String getExamples() {
		return examples;
	}

	public void setExamples(String examples) {
		this.examples = examples;
	}

	public Word(String yuanCi, String yinBiao, String examples) {
		super();
		this.yuanCi = yuanCi;
		this.yinBiao = yinBiao;
		this.examples = examples;
	}

	public Word() {
		super();
	}

	@Override
	public String toString() {
		return "Word [yuanCi=" + yuanCi + ", yinBiao=" + yinBiao + "example="
				+ examples+"duYIn"+duYin;

	}

	public String getDuYin() {
		return duYin;
	}

	public void setDuYin(String duYin) {
		this.duYin = duYin;
	}

}
