package com.cht.emm.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModelUnDeletable;


/**
 * 资讯详细信息
 * 
 * 
 * @author luoyupan
 * 
 */
@Entity
@Table(name = "mip_sys_news_detail")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class NewsDetail extends AbstractModelUnDeletable {

	private static final long serialVersionUID = 6118807749145530834L;
	@Id
	private String id;
	/**
	 * 标记（如：推荐，热门，首发，独家，收藏等）
	 */
	private int mark;
	/**
	 * 标签
	 */
	private String local;
	/**
	 * 标题图片地址（最多3张），多张图片以“,”分隔
	 */
	private String pics;
	/**
	 * 资讯基本信息
	 */
	private News news;

	public NewsDetail() {

	}

	@Id
	@Column(name = "id", length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "mark")
	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	@Column(name = "local", length = 255)
	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	@Column(name = "pics", length = 1024)
	public String getPics() {
		return pics;
	}

	public void setPics(String pics) {
		this.pics = pics;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	@PrimaryKeyJoinColumn
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	/**
	 * @return   news
	 */
	public News getNews() {
		return news;
	}

	/**
	 * @param news
	 *            要设置的 news
	 */
	public void setNews(News news) {
		this.news = news;
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
