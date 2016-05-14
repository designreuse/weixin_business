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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModelUnDeletable;
import com.cht.emm.model.id.ApplicationAuthorization;
import com.cht.emm.model.id.ApplicationDeploy;
import com.cht.emm.model.id.ApplicationScore;
import com.cht.emm.model.id.ApplicationTypeApp;


/**
 * 应用基本信息
 * 
 * 
 * @author luoyupan
 * 
 */
@Entity
@Table(name = "mip_sys_application")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Application extends AbstractModelUnDeletable {

	private static final long serialVersionUID = 6118807749145530834L;
	public static final int APP_KIND_APK = 0;
	public static final int APP_KIND_ZIP = 1;
	public static final int APP_KIND_WEB = 2;
	@Id
	private String id;
	/**
	 * 应用名称
	 */
	private String name;
	/**
	 * 应用类型（如：推荐应用等）
	 */
	private int type;
	/**
	 * 应用种类（如：apk，zip，web等）
	 */
	private int kind;
	/**
	 * 版本名称
	 */
	private String version_name;
	/**
	 * 版本号
	 */
	private int version_code;
	/**
	 * 适用的操作系统类型（如：ANDROID,IOS,WINDOWS等）
	 */
	private int os;
	/**
	 * 应用包名（ANDROID应用以此判断是否同一应用）
	 */
	private String pkg_name;
	/**
	 * 应用平均分
	 */
	private int score;
	/**
	 * 应用评分次数
	 */
	private int score_count;
	/**
	 * 应用下载次数
	 */
	private int download_count;
	/**
	 * 应用描述
	 */
	private String desc;
	/**
	 * 创建时间
	 */
	private Timestamp create;
	/**
	 * 更新时间
	 */
	private Timestamp update;
	/**
	 * 应用状态（如：启用，停用等）
	 */
	private int status;
	/**
	 * 应用截图地址，多个地址以“,”分隔
	 */
	private String screenshot;
	/**
	 * 适用的操作系统版本号
	 */
	private String os_version;
	/**
	 * 发布用户
	 */
	private User user;
	/**
	 * 应用版本信息
	 */
	private List<ApplicationVersion> appVersions = new ArrayList<ApplicationVersion>();
	/**
	 * 应用和应用分类的关联记录
	 */
	private List<ApplicationTypeApp> appTypeApps = new ArrayList<ApplicationTypeApp>();
	/**
	 * 应用的评分记录
	 */
	private List<ApplicationScore> appScores = new ArrayList<ApplicationScore>();
	/**
	 * 应用的部署记录
	 */
	private List<ApplicationDeploy> appDeploys = new ArrayList<ApplicationDeploy>();
	/**
	 * 应用的授权记录
	 */
	private List<ApplicationAuthorization> appAuths = new ArrayList<ApplicationAuthorization>();

	public Application() {

	}

	public Application(String id) {
		this.id = id;
	}

	@Id
	@Column(name = "id", length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "app_name", length = 255, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "app_type", nullable = false)
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column(name = "app_kind", nullable = false)
	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	@Column(name = "app_version_name", length = 255)
	public String getVersion_name() {
		return version_name;
	}

	public void setVersion_name(String version_name) {
		this.version_name = version_name;
	}

	@Column(name = "app_version_code")
	public int getVersion_code() {
		return version_code;
	}

	public void setVersion_code(int version_code) {
		this.version_code = version_code;
	}

	@Column(name = "app_os", nullable = false)
	public int getOs() {
		return os;
	}

	public void setOs(int os) {
		this.os = os;
	}

	@Column(name = "app_pkg_name", length = 255)
	public String getPkg_name() {
		return pkg_name;
	}

	public void setPkg_name(String pkg_name) {
		this.pkg_name = pkg_name;
	}

	@Column(name = "app_score")
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Column(name = "app_score_count", nullable = false)
	public int getScore_count() {
		return score_count;
	}

	public void setScore_count(int score_count) {
		this.score_count = score_count;
	}

	@Column(name = "app_download_count", nullable = false)
	public int getDownload_count() {
		return download_count;
	}

	public void setDownload_count(int download_count) {
		this.download_count = download_count;
	}

	@Column(name = "app_desc", length = 1024)
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Column(name = "app_create_time")
	public Timestamp getCreate() {
		return create;
	}

	public void setCreate(Timestamp create) {
		this.create = create;
	}

	@Column(name = "app_update_time")
	public Timestamp getUpdate() {
		return update;
	}

	public void setUpdate(Timestamp update) {
		this.update = update;
	}

	@Column(name = "app_os_version", length = 255)
	public String getOs_version() {
		return os_version;
	}

	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}

	@Column(name = "app_status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "app_screenshot", length = 1024)
	public String getScreenshot() {
		return screenshot;
	}

	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_publisher")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "app")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public List<ApplicationTypeApp> getAppTypeApps() {
		return appTypeApps;
	}

	public void setAppTypeApps(List<ApplicationTypeApp> appTypeApps) {
		this.appTypeApps = appTypeApps;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "app")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public List<ApplicationScore> getAppScores() {
		return appScores;
	}

	public void setAppScores(List<ApplicationScore> appScores) {
		this.appScores = appScores;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "app")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public List<ApplicationDeploy> getAppDeploys() {
		return appDeploys;
	}

	public void setAppDeploys(List<ApplicationDeploy> appDeploys) {
		this.appDeploys = appDeploys;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "app")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public List<ApplicationVersion> getAppVersions() {
		return appVersions;
	}

	public void setAppVersions(List<ApplicationVersion> appVersions) {
		this.appVersions = appVersions;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "app")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public List<ApplicationAuthorization> getAppAuths() {
		return appAuths;
	}

	public void setAppAuths(List<ApplicationAuthorization> appAuths) {
		this.appAuths = appAuths;
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
