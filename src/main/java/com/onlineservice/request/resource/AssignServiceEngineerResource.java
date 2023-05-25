package com.onlineservice.request.resource;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.onlineservice.request.model.AssignServiceEngineer;
import com.onlineservice.request.model.AssignServiceEngineerStatus;
import com.onlineservice.user.resource.UserResource;

public class AssignServiceEngineerResource {
	private Long aseId;
	private UserResource serviceEngineer;
	private List<AssignServiceEngineerStatusResource> statuses = new LinkedList<>();
	private Date creationDate;
	private Date updatedDate;

	public AssignServiceEngineerResource(AssignServiceEngineer assignServiceEngineer) {
		this.aseId = assignServiceEngineer.getId();
		this.serviceEngineer = new UserResource(assignServiceEngineer.getServiceEngineer());
		for (AssignServiceEngineerStatus s : assignServiceEngineer.getStatuses()) {
			statuses.add(new AssignServiceEngineerStatusResource(s));
		}
		this.creationDate = assignServiceEngineer.getCreationDate();
		this.updatedDate = assignServiceEngineer.getUpdatedDate();
	}

	public Long getAseId() {
		return aseId;
	}

	public UserResource getServiceEngineer() {
		return serviceEngineer;
	}

	public List<AssignServiceEngineerStatusResource> getStatuses() {
		return statuses;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

}
