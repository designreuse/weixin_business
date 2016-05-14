package com.cht.emm.vo;

import com.cht.emm.model.Application;
import com.cht.emm.model.Group;
import com.cht.emm.model.id.ApplicationAuthorization;
import com.cht.emm.util.StringUtil;
import com.cht.emm.util.UUIDGen;

public class ApplicationAuthorizationVO {

	private String id;
	private String app_id;
	private String group_id;
	private String group_name;
	private int type;

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

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public ApplicationAuthorization toAppAuth() {
		ApplicationAuthorization appAuth = new ApplicationAuthorization();
		appAuth.setId(StringUtil.isNullOrEmpty(id) ? UUIDGen.getUUID() : id);
		appAuth.setGroup(new Group(group_id));
		appAuth.setApp(new Application(app_id));
		appAuth.setType(type);
		return appAuth;
	}

	public void fromAppAuth(ApplicationAuthorization appAuth) {
		id = appAuth.getId();
		group_id = appAuth.getGroup().getId();
		group_name = appAuth.getGroup().getGroupName();
		app_id = appAuth.getApp().getId();
		type = appAuth.getType();
	}

}
