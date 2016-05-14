package com.cht.emm.model.id;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModel;
import com.cht.emm.model.Application;
import com.cht.emm.model.User;


/**
 * 应用的评分记录
 * 
 * 
 * @author luoyupan
 * 
 */
@Entity
@Table(name = "mip_sys_app_score")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ApplicationScore extends AbstractModel {

	private static final long serialVersionUID = 6118807749145530834L;
	@Id
	private String id;
	/**
	 * 被评应用
	 */
	private Application app;
	/**
	 * 评分值
	 */
	private int score;
	/**
	 * 用户评论
	 */
	private String comment;
	/**
	 * 评分用户
	 */
	private User user;
	/**
	 * 评分时间
	 */
	private Timestamp time;

	public ApplicationScore() {

	}

	@Id
	@Column(name = "id", nullable = false, length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_id", nullable = false)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Application getApp() {
		return app;
	}

	public void setApp(Application app) {
		this.app = app;
	}

	@Column(name = "app_score", nullable = false)
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Column(name = "app_comment", length = 1024)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "score_time")
	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	
	@Column(name="is_system")
	@Override
	public Integer getIsSystem() {
		return isSystem;
	}
}
