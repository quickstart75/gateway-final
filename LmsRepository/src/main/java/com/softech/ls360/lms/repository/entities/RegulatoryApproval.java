package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;

@Entity
@NamedEntityGraphs(
	@NamedEntityGraph(
	    name="RegulatoryApproval.WithRegulatorCategoryAndCreditReportingFields",
	    attributeNodes={
	        @NamedAttributeNode("approvalType"),
			@NamedAttributeNode("deleted"),
	        @NamedAttributeNode(value="regulatorCategory", subgraph="regulatorCategorySubGraph"),
	    },
		subgraphs={
			@NamedSubgraph(
			    name="regulatorCategorySubGraph",
			    attributeNodes={
			        @NamedAttributeNode("creditReportingFields"),
			    }
			)
	    }
	)
)
public class RegulatoryApproval extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private ContentOwner contentOwner;
	private String approvalType;
	private Boolean deleted;
	private RegulatorCategory regulatorCategory;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CONTENTOWNER_ID")
	public ContentOwner getContentOwner() {
		return contentOwner;
	}

	public void setContentOwner(ContentOwner contentOwner) {
		this.contentOwner = contentOwner;
	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	@Column(nullable=false)
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REGULATORCATEGORY_ID")
	public RegulatorCategory getRegulatorCategory() {
		return regulatorCategory;
	}

	public void setRegulatorCategory(RegulatorCategory regulatorCategory) {
		this.regulatorCategory = regulatorCategory;
	}

}
