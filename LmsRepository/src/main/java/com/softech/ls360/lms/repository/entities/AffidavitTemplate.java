package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AffidavitTemplate extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String templateGuid;
	private Boolean status;
	private LocalDateTime createDate;
	private VU360User createdUser;
	private String templateName;

	@Column(name="TEMPLATE_GUID")
	public String getTemplateGuid() {
		return templateGuid;
	}

	public void setTemplateGuid(String templateGuid) {
		this.templateGuid = templateGuid;
	}

	@Column(name="STATUSTF")
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="CREATEDVU360USER_ID", nullable=false)
	public VU360User getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(VU360User createdUser) {
		this.createdUser = createdUser;
	}

	@Column(name="TEMPLATE_NAME")
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

}
