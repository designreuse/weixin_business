package com.cht.emm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModelUnDeletable;


/**
 * 资讯基本信息
 * 
 * 
 * @author luoyupan
 * 
 */
@Entity
@Table(name = "mip_sys_news")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class News extends AbstractModelUnDeletable {

	private static final long serialVersionUID = 6118807749145530834L;
	/**
	 * 资讯内容模板
	 */
	public static final String NEWS_TEMPLATE = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
			+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
			+ "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"
			+ "<title></title></head><body>{body}</body></html>";
	@Id
	private String id;
	/**
	 * 资讯标题图片展示方式（如：大图展示，小图展示等）
	 */
	private int type;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 导语
	 */
	private String lead;
	/**
	 * 标题图片（目前未使用）
	 */
	private String photo;
	/**
	 * 发布时间
	 */
	private Timestamp time;
	/**
	 * 资讯网页地址
	 */
	private String url;
	/**
	 * 作者
	 */
	private String editor;
	/**
	 * 资讯详情
	 */
	private String body;
	/**
	 * 资讯详细信息
	 */
	private NewsDetail detail;

	public News() {

	}

	@Id
	@Column(name = "id", length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column(name = "title", length = 255, nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "lead", length = 1024)
	public String getLead() {
		return lead;
	}

	public void setLead(String lead) {
		this.lead = lead;
	}

	@Column(name = "photo", length = 1024)
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Column(name = "create_time")
	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	@Column(name = "url", length = 255, nullable = false)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "editor", length = 255)
	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	@Lob
	@Column(name = "body")
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@OneToOne(mappedBy = "news", optional = false)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public NewsDetail getDetail() {
		return detail;
	}

	public void setDetail(NewsDetail detail) {
		this.detail = detail;
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
