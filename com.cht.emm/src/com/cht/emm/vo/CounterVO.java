package com.cht.emm.vo;

import java.io.Serializable;

public class CounterVO implements Serializable{
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -6061500887225167481L;
	private String id;
	private String name;
	private Integer count;

	public CounterVO(){
		
	}
	public CounterVO(String id,String name,Integer count){
		this.id = id;
		this.name = name;
		this.count = count;
	}
	
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
