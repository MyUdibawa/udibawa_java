package com.onlineservice.user.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.onlineservice.center.model.ServiceCenter;

@Entity
public class ServiceEngineerUser extends User {

	@ManyToOne
	private ServiceCenter serviceCenter;

	public ServiceCenter getServiceCenter() {
		return serviceCenter;
	}

	public void setServiceCenter(ServiceCenter serviceCenter) {
		this.serviceCenter = serviceCenter;
	}

}
