package com.cht.emm.vo;

import java.util.ArrayList;
import java.util.List;

import com.cht.emm.model.Application;


public class ApplicationVO3 {

	private String desc;
	private String screenshot;
	private long time;
	private List<ApplicationScoreVO1> scores = new ArrayList<ApplicationScoreVO1>();

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getScreenshot() {
		return screenshot;
	}

	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public List<ApplicationScoreVO1> getScores() {
		return scores;
	}

	public void setScores(List<ApplicationScoreVO1> scores) {
		this.scores = scores;
	}

	public void fromApp(Application app) {
		this.setDesc(app.getDesc() == null ? "" : app.getDesc());
		this.setScreenshot(app.getScreenshot() == null ? "" : app
				.getScreenshot());
		this.setTime(app.getCreate().getTime());
	}

}
