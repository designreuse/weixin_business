package com.cht.emm.model.id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModel;
import com.cht.emm.model.Authority;
import com.cht.emm.model.Resource;


@Entity
@Table(name = "mip_sys_resource_authority")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ResourceAuth extends AbstractModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3991601885095987437L;
	@Id
	private String id;
	private Resource resource;
	private Authority auth;
	private String subUri;
	
	
	public ResourceAuth() {

	}

	public ResourceAuth(String id) {
		super();
		this.id = id;
	}

	public ResourceAuth(String id, Resource resource, Authority auth,
			String subUri) {
		super();
		this.id = id;
		this.resource = resource;
		this.auth = auth;
		this.subUri = subUri;
	}

	@Id
	@Column(name = "id", length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "resource_id", nullable = true)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@ManyToOne
	@JoinColumn(name = "authority_id", nullable = true)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Authority getAuth() {
		return auth;
	}

	public void setAuth(Authority auth) {
		this.auth = auth;
	}

	@Column(name = "url", nullable = false, length = 255)
	public String getSubUri() {
		return subUri;
	}

	public void setSubUri(String subUri) {
		this.subUri = subUri;
	}

	
	@Column(name="is_system")
	@Override
	public Integer getIsSystem() {
		return isSystem;
	}
}
