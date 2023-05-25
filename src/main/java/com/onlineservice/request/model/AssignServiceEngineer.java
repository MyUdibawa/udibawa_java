package com.onlineservice.request.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.onlineservice.user.model.ServiceEngineerUser;

@Entity
public class AssignServiceEngineer {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private ServiceEngineerUser serviceEngineer;

	@ManyToOne
	private ServiceRequest serviceRequest;

	@OneToMany(mappedBy = "assignServiceEngineer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AssignServiceEngineerStatus> statuses = new LinkedList<>();

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

	public ServiceEngineerUser getServiceEngineer() {
		return serviceEngineer;
	}

	public void setServiceEngineer(ServiceEngineerUser serviceEngineer) {
		this.serviceEngineer = serviceEngineer;
	}

	public ServiceRequest getServiceRequest() {
		return serviceRequest;
	}

	public void setServiceRequest(ServiceRequest serviceRequest) {
		this.serviceRequest = serviceRequest;
	}

	public List<AssignServiceEngineerStatus> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<AssignServiceEngineerStatus> statuses) {
		this.statuses = statuses;
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

}
