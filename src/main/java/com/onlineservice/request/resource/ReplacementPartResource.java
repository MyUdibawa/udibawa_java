package com.onlineservice.request.resource;

import java.util.Date;

import com.onlineservice.request.model.ReplacementPart;

public class ReplacementPartResource {
	private Long rpId;

	private String name;
	private Float price;

	private Date creationDate;
	private Date updatedDate;

	public ReplacementPartResource(ReplacementPart replacementPart) {
		this.rpId = replacementPart.getId();
		this.name = replacementPart.getName();
		this.price = replacementPart.getPrice();
		this.creationDate = replacementPart.getCreationDate();
		this.updatedDate = replacementPart.getUpdatedDate();
	}

	public Long getRpId() {
		return rpId;
	}

	public String getName() {
		return name;
	}

	public Float getPrice() {
		return price;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

}
