package com.onlineservice.center.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class OfferedService {
	@Id
	@GeneratedValue
	private Long id;
	@Enumerated(EnumType.STRING)
	private ServiceType serviceType;
	private Float serviceCharge;
	private String pincodes;

	private Boolean deleted = false;

	@ManyToOne
	private ServiceCenter serviceCenter;

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

	public ServiceCenter getServiceCenter() {
		return serviceCenter;
	}

	public void setServiceCenter(ServiceCenter serviceCenter) {
		this.serviceCenter = serviceCenter;
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

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getPincodes() {
		return pincodes;
	}

	public void setPincodes(String pincodes) {
		this.pincodes = pincodes;
	}

}
