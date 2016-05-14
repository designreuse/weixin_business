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
import com.cht.emm.model.id.StrategyUser;


/**
 * 设备策略信息
 * 
 * @author luoyupan
 * 
 */
@Entity
@Table(name = "mip_sys_strategy")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Strategy extends AbstractModelUnDeletable {

	private static final long serialVersionUID = 6118807749145530834L;
	@Id
	private String id;
	/**
	 * 策略名称
	 */
	private String name;
	/**
	 * 策略描述
	 */
	private String desc;
	/**
	 * 创建者
	 */
	private User creator;
	/**
	 * 策略详细内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private Timestamp time;
	/**
	 * 用户和设备策略的关联记录
	 */
	private List<StrategyUser> strategyUsers = new ArrayList<StrategyUser>();

	public Strategy() {

	}

	public Strategy(String deviceID) {
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

	@Column(name = "strategy_name", length = 255, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "strategy_desc", length = 255)
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "strategy_creator")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	@Lob
	@Column(name = "strategy_content")
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

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "strategy")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public List<StrategyUser> getStrategyUsers() {
		return strategyUsers;
	}

	public void setStrategyUsers(List<StrategyUser> strategyUsers) {
		this.strategyUsers = strategyUsers;
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
