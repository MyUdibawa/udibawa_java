package com.onlineservice.request.resource;

public class DashboardResource {
	private Long totalEngineers;
	private Long totalServices;
	private Long totalOrders;

	public Long getTotalEngineers() {
		return totalEngineers;
	}

	public void setTotalEngineers(Long totalEngineers) {
		this.totalEngineers = totalEngineers;
	}

	public Long getTotalServices() {
		return totalServices;
	}

	public void setTotalServices(Long totalServices) {
		this.totalServices = totalServices;
	}

	public Long getTotalOrders() {
		return totalOrders;
	}

	public void setTotalOrders(Long totalOrders) {
		this.totalOrders = totalOrders;
	}

}
