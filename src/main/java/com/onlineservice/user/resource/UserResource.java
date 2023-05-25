package com.onlineservice.user.resource;

import java.util.Date;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.onlineservice.user.model.AdminUser;
import com.onlineservice.user.model.CustomerUser;
import com.onlineservice.user.model.ServiceCenterAdminUser;
import com.onlineservice.user.model.ServiceEngineerUser;
import com.onlineservice.user.model.User;

public class UserResource extends ResourceSupport {
	private Long uId;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String userType;
	private Date creationDate;
	private Date updatedDate;
	private Boolean isAccountNonLocked = false;
	private Boolean isEnabled = false;

	public UserResource(User user) {
		this.uId = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.phoneNumber = user.getPhoneNumber();

		if (user instanceof AdminUser) {
			userType = "ADMIN";
		} else if (user instanceof ServiceCenterAdminUser) {
			userType = "SERVICE_CENTER_ADMIN";
		} else if (user instanceof ServiceEngineerUser) {
			userType = "SERVICE_ENGINEER";
		} else if (user instanceof CustomerUser) {
			userType = "CUSTOMER";
		}

		this.creationDate = user.getCreationDate();
		this.updatedDate = user.getUpdatedDate();
		this.isAccountNonLocked = user.getIsAccountNonLocked();
		this.isEnabled = user.getIsEnabled();

		if (user.getProfilePic() != null && !user.getProfilePic().isEmpty())
			add(new Link(user.getProfilePic()).withRel("profilePic"));
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getUserType() {
		return userType;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public Boolean getIsAccountNonLocked() {
		return isAccountNonLocked;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public Long getuId() {
		return uId;
	}
	
}
