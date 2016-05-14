package com.cht.emm.vo;

import com.cht.emm.model.Application;

public class ApplicationVO1 {
	private String id;
	private String appId;
	private String name;
	private int type;
	private String versionName;
	private int versionCode;
	private String icon;
	private int commentScore;
	private int commentCount;
	private int downloadCount;
	private String category;
	private int appType;
	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getCommentScore() {
		return commentScore;
	}

	public void setCommentScore(int commentScore) {
		this.commentScore = commentScore;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getAppType() {
		return appType;
	}

	public void setAppType(int appType) {
		this.appType = appType;
	}

	public int getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}

	public void fromApp(Application app) {
		this.setId(app.getId());
		this.setName(app.getName());
		this.setAppId(app.getPkg_name());
		this.setType(app.getType());
		this.setVersionCode(app.getVersion_code());
		this.setVersionName(app.getVersion_name());
		this.setAppType(app.getKind());
		this.setCommentCount(app.getScore_count());
		this.setCommentScore(app.getScore());
		this.setDownloadCount(app.getDownload_count());
		this.setDesc(app.getDesc());
	}

}
