/**
 * @Title: NodeCommon.java
 * @Package: nari.mip.backstage.vo
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2014-12-26 下午3:34:40
 * @Version: 1.0
 */
package com.cht.emm.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Class: NodeCommon
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
public class NodeCommon implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8654433628973924323L;
	private String id;
	private String text;
	private String type;
	private String icon;
	private String index;
	private boolean assystem;
	private String other;
	/**
	 * @return the assystem
	 */
	public boolean isAssystem() {
		return assystem;
	}
	/**
	 * @param assystem the assystem to set
	 */
	public void setAssystem(boolean assystem) {
		this.assystem = assystem;
	}
	private boolean root;
	
	private Map<String, Boolean> state =new HashMap<String, Boolean>();
	
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	 
	public Map<String, Boolean> getState() {
		return state;
	}
	public void setState(Map<String, Boolean> state) {
		this.state = state;
	}
	public boolean isRoot() {
		return root;
	}
	public void setRoot(boolean root) {
		this.root = root;
	}
	/**
	 * @return the other
	 */
	public String getOther() {
		return other;
	}
	/**
	 * @param other the other to set
	 */
	public void setOther(String other) {
		this.other = other;
	}
}
