package com.cht.emm.vo;

import java.sql.Timestamp;

import com.cht.emm.model.Application;
import com.cht.emm.model.ApplicationVersion;
import com.cht.emm.model.User;
import com.cht.emm.util.TimestampUtil;
import com.cht.emm.util.UUIDGen;


public class ApplicationVO {

	private static String OS_ANDROID = "ANDROID";
	private static String OS_IOS = "IOS";

	private String id;
	private String name;
	private int type;
	private int kind;
	private String version_name;
	private int version_code;
	private String os;
	private String pkg_name;
	private String icon;
	private String url;
	private int score;
	private int score_count;
	private int download_count;
	private String publisher;
	private String desc;
	private String create;
	private String update;
	private String os_version;
	private String category;
	private int status;
	private String screenshot;
	private String updateDesc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getVersion_name() {
		return version_name;
	}

	public void setVersion_name(String version_name) {
		this.version_name = version_name;
	}

	public int getVersion_code() {
		return version_code;
	}

	public void setVersion_code(int version_code) {
		this.version_code = version_code;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getPkg_name() {
		return pkg_name;
	}

	public void setPkg_name(String pkg_name) {
		this.pkg_name = pkg_name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore_count() {
		return score_count;
	}

	public void setScore_count(int score_count) {
		this.score_count = score_count;
	}

	public int getDownload_count() {
		return download_count;
	}

	public void setDownload_count(int download_count) {
		this.download_count = download_count;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCreate() {
		return create;
	}

	public void setCreate(String create) {
		this.create = create;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String getOs_version() {
		return os_version;
	}

	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getScreenshot() {
		return screenshot;
	}

	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
	}

	public String getUpdateDesc() {
		return updateDesc;
	}

	public void setUpdateDesc(String updateDesc) {
		this.updateDesc = updateDesc;
	}

	public Application toApp(Application app) {
		if (app == null) {
			app = new Application();
			app.setId(id);
			app.setName(name);
			app.setPkg_name(pkg_name);
			app.setVersion_code(version_code);
			app.setVersion_name(version_name);
			if (os.equals(OS_ANDROID)) {
				app.setOs(0);
			} else if (os.equals(OS_IOS)) {
				app.setOs(1);
			}
			app.setKind(kind);
			app.setCreate(new Timestamp(System.currentTimeMillis()));
			app.setOs_version(os_version);
		}

		app.setType(type);
		app.setDesc(desc);
		app.setScreenshot(screenshot);
		app.setUser(new User(publisher));
		app.setUpdate(new Timestamp(System.currentTimeMillis()));
		return app;
	}

	public void fromApp(Application app) {
		this.setId(app.getId());
		this.setName(app.getName());
		this.setPkg_name(app.getPkg_name() == null ? "" : app.getPkg_name());
		this.setVersion_code(app.getVersion_code());
		this.setVersion_name(app.getVersion_name() == null ? "" : app
				.getVersion_name());
		if (app.getOs() == 0) {
			this.setOs(OS_ANDROID);
		} else if (app.getOs() == 1) {
			this.setOs(OS_IOS);
		}
		this.setType(app.getType());
		this.setKind(app.getKind());
		this.setDownload_count(app.getDownload_count());
		this.setCreate(TimestampUtil.toString(app.getCreate()));
		this.setDesc(app.getDesc());
		this.setOs_version(app.getOs_version());
		this.setScore(app.getScore());
		this.setScore_count(app.getScore_count());
		this.setStatus(app.getStatus());
		this.setScreenshot(app.getScreenshot() == null ? "" : app
				.getScreenshot());
		this.setUpdate(TimestampUtil.toString(app.getUpdate()));
		this.setPublisher(app.getUser().getUsername());
	}

	public ApplicationVersion toAppVersion(ApplicationVersion appVersion) {
		if (appVersion == null) {
			appVersion = new ApplicationVersion();
			appVersion.setId(UUIDGen.getUUID());
			appVersion.setApp(new Application(id));
			appVersion.setIcon(icon);
			appVersion.setUrl(url);
			appVersion.setVersion_code(version_code);
			appVersion.setVersion_name(version_name);
			appVersion.setUpdateDesc(updateDesc);
		}
		return appVersion;
	}

	public void fromAppVersion(ApplicationVersion appVersion) {
		this.setIcon(appVersion.getIcon());
		this.setUrl(appVersion.getUrl());
		this.setUpdateDesc(appVersion.getUpdateDesc());
	}
}
