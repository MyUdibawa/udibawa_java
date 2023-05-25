package com.onlineservice.service.implementation;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.onlineservice.aws.AmazonClient;
import com.onlineservice.center.model.ServiceCenter;
import com.onlineservice.service.StorageService;
import com.onlineservice.user.model.User;

@Service
public class StorageServiceImplementation implements StorageService {
	private AmazonClient amazonClient;
	private static final String USER_FOLDER = "User";
	private static final String PROFILE_PIC_FOLDER = "ProfilePic";
	private static final String MEDIA_FOLDER = "Media";
	private static final String SERVICE_CENTER_FOLDER = "ServiceCenter";
	private static final String FILE_SEPARATOR = "/";

	@Autowired
	public StorageServiceImplementation(AmazonClient amazonClient) {
		this.amazonClient = amazonClient;
	}

	@Override
	public String uploadProfilePic(MultipartFile file, User user) {
		return amazonClient.uploadMedia(file, getUserProfilePicFolder(user) + new Date().getTime() + "."
				+ getFileExtension(file.getOriginalFilename()));
	}

	private String getUserProfilePicFolder(User user) {
		return USER_FOLDER + FILE_SEPARATOR + user.getId() + FILE_SEPARATOR + PROFILE_PIC_FOLDER + FILE_SEPARATOR;
	}

	private String getFileExtension(String fileName) {
		String extension = "";
		try {
			if (fileName != null) {
				extension = fileName.substring(fileName.lastIndexOf('.') + 1);
			}
		} catch (Exception e) {
			extension = "";
		}
		return extension;
	}

	@Override
	public String uploadMedia(MultipartFile file) {
		return amazonClient.uploadMedia(file, SERVICE_CENTER_FOLDER + FILE_SEPARATOR + MEDIA_FOLDER + FILE_SEPARATOR
				+ new Date().getTime() + "." + getFileExtension(file.getOriginalFilename()));
	}

	@Override
	public String uploadServiceCenterImage(MultipartFile file, ServiceCenter serviceCenter) {
		return amazonClient.uploadMedia(file,
				SERVICE_CENTER_FOLDER + FILE_SEPARATOR + serviceCenter.getId() + FILE_SEPARATOR + MEDIA_FOLDER
						+ FILE_SEPARATOR + new Date().getTime() + "." + getFileExtension(file.getOriginalFilename()));
	}
}
