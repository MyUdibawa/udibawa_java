package com.onlineservice.user.model;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.onlineservice.utils.GeneralUtils;

@Entity
public class ResetPasswordOTPTable {
	@Id
	@GeneratedValue
	private Long id;
	private int otp;
	@OneToOne
	private User user;
	private Date expiryDate;

	public ResetPasswordOTPTable() {
	}

	public ResetPasswordOTPTable(User user) {
		this.user = user;
		Calendar now = Calendar.getInstance();
		now.add(5, 1);
		this.expiryDate = now.getTime();
		this.otp = GeneralUtils.createToken();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getOtp() {
		return this.otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
}
