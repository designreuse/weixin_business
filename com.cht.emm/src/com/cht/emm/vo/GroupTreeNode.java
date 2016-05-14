package com.cht.emm.vo;

import java.util.ArrayList;
import java.util.List;

public class GroupTreeNode {

	private String id;
	private String text;
	private List<GroupTreeNode> children = new ArrayList<GroupTreeNode>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<GroupTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<GroupTreeNode> children) {
		this.children = children;
	}

}
