package com.onlineservice.center.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.onlineservice.user.model.ServiceCenterAdminUser;
import com.onlineservice.user.model.ServiceEngineerUser;

@Entity
public class ServiceCenter {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@Column(columnDefinition = "TEXT")
	private String description;
	private String gstin;
	private String pan;
	private String image;

	@OneToOne
	private ServiceCenterAddress address;

	@OneToOne
	private ServiceCenterAdminUser admin;

	@OneToMany(mappedBy = "serviceCenter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<OfferedService> services = new LinkedList<>();

	@OneToMany(mappedBy = "serviceCenter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ServiceEngineerUser> serviceEngineers = new LinkedList<>();

	private Boolean isVerified = false;
	private Boolean isDeleted = false;

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

	public ServiceCenterAddress getAddress() {
		return address;
	}

	public void setAddress(ServiceCenterAddress address) {
		this.address = address;
	}

	public ServiceCenterAdminUser getAdmin() {
		return admin;
	}

	public void setAdmin(ServiceCenterAdminUser admin) {
		this.admin = admin;
	}

	public List<OfferedService> getServices() {
		return services;
	}

	public void setServices(List<OfferedService> services) {
		this.services = services;
	}

	public List<ServiceEngineerUser> getServiceEngineers() {
		return serviceEngineers;
	}

	public void setServiceEngineers(List<ServiceEngineerUser> serviceEngineers) {
		this.serviceEngineers = serviceEngineers;
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

	public Boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
