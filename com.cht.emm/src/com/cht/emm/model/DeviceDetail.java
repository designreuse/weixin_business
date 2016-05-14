package com.cht.emm.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModelUnDeletable;


/**
 * 设备详细信息
 * 
 * 
 * @author luoyupan
 * 
 */
@Entity
@Table(name = "mip_sys_device_detail")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class DeviceDetail extends AbstractModelUnDeletable {

	private static final long serialVersionUID = 6118807749145530834L;
	@Id
	private String id;
	/**
	 * 设备详情（以JSON格式保存）
	 */
	private String detail;
	/**
	 * 注册时间
	 */
	private Timestamp register;
	/**
	 * 注销时间
	 */
	private Timestamp destroy;
	/**
	 * 驳回申请或注销的说明
	 */
	private String remark;
	/**
	 * 设备基本信息
	 */
	private Device device;

	public DeviceDetail() {

	}

	public DeviceDetail(String deviceID) {
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

	@Lob
	@Column(name = "device_detail")
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Column(name = "register_time")
	public Timestamp getRegister() {
		return register;
	}

	public void setRegister(Timestamp register) {
		this.register = register;
	}

	@Column(name = "destroy_time")
	public Timestamp getDestroy() {
		return destroy;
	}

	public void setDestroy(Timestamp destroy) {
		this.destroy = destroy;
	}

	@Column(name = "remark", length = 255)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	@PrimaryKeyJoinColumn
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	/**
	 * @return   device
	 */
	public Device getDevice() {
		return device;
	}

	/**
	 * @param device
	 *            要设置的 device
	 */
	public void setDevice(Device device) {
		this.device = device;
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
