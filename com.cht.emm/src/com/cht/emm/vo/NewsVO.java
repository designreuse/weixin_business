package com.cht.emm.vo;

import java.sql.Timestamp;

import com.cht.emm.model.News;
import com.cht.emm.util.TimestampUtil;


public class NewsVO {
	private String id;
	private String title;
	private String editor;
	private String lead;
	private int type;
	private String body;
	private String photo;
	private String url;
	private String news_time;

	private int mark;
	private String local;
	private String pics;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getLead() {
		return lead;
	}

	public void setLead(String lead) {
		this.lead = lead;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNews_time() {
		return news_time;
	}

	public void setNews_time(String news_time) {
		this.news_time = news_time;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getPics() {
		return pics;
	}

	public void setPics(String pics) {
		this.pics = pics;
	}

	public News toNews(News news) {
		if (news == null) {
			news = new News();
		}
		news.setId(id);
		news.setEditor(editor);
		news.setLead(lead);
		news.setPhoto(photo);
		news.setTitle(title);
		news.setType(type);
		news.setBody(body);
		news.setUrl(url);
		news.setTime(new Timestamp(System.currentTimeMillis()));
		return news;
	}

	public void fromNews(News news) {
		this.id = news.getId();
		this.type = news.getType();
		this.title = news.getTitle();
		this.editor = news.getEditor() == null ? "" : news.getEditor();
		this.news_time = TimestampUtil.toString(news.getTime());
		this.photo = news.getPhoto() == null ? "" : news.getPhoto();
		this.body = news.getBody();
		this.url = news.getUrl();
		this.lead = news.getLead();
		this.mark = news.getDetail().getMark();
		this.local = news.getDetail().getLocal() == null ? "" : news
				.getDetail().getLocal();
		this.pics = news.getDetail().getPics() == null ? "" : news.getDetail()
				.getPics();
	}
}
