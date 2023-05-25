package com.onlineservice.user.resource;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import com.onlineservice.user.model.CustomerAddress;

public class CustomerAddressResource extends ResourceSupport {
	private Long caId;
	private String title;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String country;
	private String pincode;
	private Double latitude;
	private Double longitude;
	private Date creationDate;
	private Date updatedDate;

	public CustomerAddressResource(CustomerAddress address) {
		this.caId = address.getId();
		this.title = address.getTitle();
		this.addressLine1 = address.getAddressLine1();
		this.addressLine2 = address.getAddressLine2();
		this.city = address.getCity();
		this.state = address.getState();
		this.country = address.getCountry();
		this.pincode = address.getPincode();
		this.latitude = address.getLatitude();
		this.longitude = address.getLongitude();
		this.creationDate = address.getCreationDate();
		this.updatedDate = address.getUpdatedDate();
	}

	public Long getCaId() {
		return caId;
	}

	public String getTitle() {
		return title;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getCountry() {
		return country;
	}

	public String getPincode() {
		return pincode;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

}
