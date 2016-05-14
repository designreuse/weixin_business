package com.cht.emm.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModelUnDeletable;
import com.cht.emm.model.id.ConfigUser;


/**
 * 设备配置信息
 * 
 * @author luoyupan
 * 
 */
@Entity
@Table(name = "mip_sys_config")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Config extends AbstractModelUnDeletable {

	private static final long serialVersionUID = 6118807749145530834L;
	@Id
	private String id;
	/**
	 * 配置名称
	 */
	private String name;
	/**
	 * 配置描述
	 */
	private String desc;
	/**
	 * 创建者
	 */
	private User creator;
	/**
	 * 配置详细内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private Timestamp time;
	/**
	 * 配置类型（如：自动发布，按需发布等）
	 */
	private int type;
	/**
	 * 用户和设备配置的关联记录
	 */
	private List<ConfigUser> configUsers = new ArrayList<ConfigUser>();

	public Config() {

	}

	public Config(String deviceID) {
		id = deviceID;
	}

	@Id
	@Column(name = "id", length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "config_name", length = 255, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "config_desc", length = 255)
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "config_creator")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	@Lob
	@Column(name = "config_content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "create_time")
	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	@Column(name = "config_type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "config")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public List<ConfigUser> getConfigUsers() {
		return configUsers;
	}

	public void setConfigUsers(List<ConfigUser> configUsers) {
		this.configUsers = configUsers;
	}

	@Column(name = "deleted", nullable = false)
	@Override
	public Integer getDeleted() {
		return deleted;
	}
	
	@Column(name="is_system")
	@Override
	public Integer getIsSystem() {
		return isSystem;
	}
}
