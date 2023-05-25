package com.onlineservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.onlineservice.center.model.ServiceCenter;
import com.onlineservice.user.model.User;

@Service
public interface StorageService {

	public String uploadProfilePic(MultipartFile file, User user);

	public String uploadMedia(MultipartFile file);

	public String uploadServiceCenterImage(MultipartFile file, ServiceCenter serviceCenter);
}
