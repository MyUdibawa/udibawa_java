package com.onlineservice.center.model.create;

import com.onlineservice.user.model.create.UserCreateModel;

public class ServiceCenterCreateModel {
	private String name;
	private String description;
	private String gstin;
	private String pan;
	private String image;

	private ServiceCenterAddressCreateModel address;

	private UserCreateModel admin;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public ServiceCenterAddressCreateModel getAddress() {
		return address;
	}

	public void setAddress(ServiceCenterAddressCreateModel address) {
		this.address = address;
	}

	public UserCreateModel getAdmin() {
		return admin;
	}

	public void setAdmin(UserCreateModel admin) {
		this.admin = admin;
	}

}
