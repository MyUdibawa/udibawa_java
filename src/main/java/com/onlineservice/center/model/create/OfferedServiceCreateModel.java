package com.onlineservice.center.model.create;

import com.onlineservice.center.model.ServiceType;

public class OfferedServiceCreateModel {
	private ServiceType serviceType;
	private Float serviceCharge;
	private String pincodes;

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public Float getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(Float serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public String getPincodes() {
		return pincodes;
	}

	public void setPincodes(String pincodes) {
		this.pincodes = pincodes;
	}

}
