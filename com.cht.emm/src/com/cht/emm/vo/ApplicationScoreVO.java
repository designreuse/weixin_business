package com.cht.emm.vo;

import com.cht.emm.model.id.ApplicationScore;
import com.cht.emm.util.TimestampUtil;

public class ApplicationScoreVO {

	private String id;
	private String app_id;
	private int score;
	private String comment;
	private String user;
	private String time;

	public ApplicationScoreVO() {
		id = "";
		app_id = "";
		comment = "";
		user = "";
		time = "";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void fromAppScore(ApplicationScore score) {
		this.setApp_id(score.getApp().getId());
		this.setComment(score.getComment() == null ? "" : score.getComment());
		this.setId(score.getId());
		this.setScore(score.getScore());
		this.setTime(TimestampUtil.toString(score.getTime()));
		this.setUser(score.getUser().getUsername());
	}
}
