package com.onlineservice.user.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.onlineservice.request.model.ServiceRequest;

@Entity
public class CustomerUser extends User {

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CustomerAddress> addresses = new LinkedList<>();

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ServiceRequest> serviceRequests = new LinkedList<>();

	public List<CustomerAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<CustomerAddress> addresses) {
		this.addresses = addresses;
	}

	public List<ServiceRequest> getServiceRequests() {
		return serviceRequests;
	}

	public void setServiceRequests(List<ServiceRequest> serviceRequests) {
		this.serviceRequests = serviceRequests;
	}

}
