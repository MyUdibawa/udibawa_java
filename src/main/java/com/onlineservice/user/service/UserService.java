package com.onlineservice.user.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.onlineservice.user.model.User;
import com.onlineservice.user.resource.UserResource;

@Service
public interface UserService {
	public User findByEmail(String username);

	public UserResource getLoggedIn(String username);

	public String updateUser(String firstName, String lastName, String phoneNumber, String username);

	public String updateProfilePic(MultipartFile file, String username);

	public String changePassword(String password, String username);
}
