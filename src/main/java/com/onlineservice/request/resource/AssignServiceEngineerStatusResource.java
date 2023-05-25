package com.onlineservice.request.resource;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import com.onlineservice.request.model.AssignServiceEngineerStatus;
import com.onlineservice.request.model.AssignServiceEngineerStatusType;

public class AssignServiceEngineerStatusResource extends ResourceSupport {
	private Long asesId;
	private AssignServiceEngineerStatusType status;
	private Date creationDate;
	private Date updatedDate;

	public AssignServiceEngineerStatusResource(AssignServiceEngineerStatus assignServiceEngineerStatus) {
		this.asesId = assignServiceEngineerStatus.getId();
		this.status = assignServiceEngineerStatus.getStatus();
		this.creationDate = assignServiceEngineerStatus.getCreationDate();
		this.updatedDate = assignServiceEngineerStatus.getUpdatedDate();
	}

	public Long getAsesId() {
		return asesId;
	}

	public AssignServiceEngineerStatusType getStatus() {
		return status;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

}
