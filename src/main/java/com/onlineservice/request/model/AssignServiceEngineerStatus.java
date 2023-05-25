package com.onlineservice.request.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.onlineservice.user.model.User;

@Entity
public class AssignServiceEngineerStatus {

	@Id
	@GeneratedValue
	private Long id;
	@Enumerated(EnumType.STRING)
	private AssignServiceEngineerStatusType status;
	@ManyToOne
	private AssignServiceEngineer assignServiceEngineer;
	@ManyToOne
	private User createdBy;

	@CreationTimestamp
	private Date creationDate;
	@UpdateTimestamp
	private Date updatedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AssignServiceEngineerStatusType getStatus() {
		return status;
	}

	public void setStatus(AssignServiceEngineerStatusType status) {
		this.status = status;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public AssignServiceEngineer getAssignServiceEngineer() {
		return assignServiceEngineer;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setAssignServiceEngineer(AssignServiceEngineer assignServiceEngineer) {
		this.assignServiceEngineer = assignServiceEngineer;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

}
