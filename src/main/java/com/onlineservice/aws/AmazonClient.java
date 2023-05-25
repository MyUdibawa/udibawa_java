package com.onlineservice.aws;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AmazonClient {

	private Logger logger = LoggerFactory.getLogger(AmazonClient.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${aws.s3.bucket}")
	private String bucketName;

	@Value("${aws.endpointurl}")
	private String endpointUrl;

	@Value("${aws.foldername}")
	private String awsFolder;

	public static final String FILE_SEPARETOR = "/";

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		try (FileOutputStream fos = new FileOutputStream(convFile)) {
			fos.write(file.getBytes());
		} catch (IOException io) {
			logger.error(io.getMessage());
			throw new IOException();
		}
		return convFile;
	}

	public void uploadFileTos3bucket(String fileName, File file) {
		s3client.putObject(
				new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
	}

	public String uploadMedia(MultipartFile multipartFile, String fileName) {
		String fileUrl = "";
		fileName = awsFolder + FILE_SEPARETOR + fileName;
		try {
			File file = convertMultiPartToFile(multipartFile);
			fileUrl = endpointUrl + FILE_SEPARETOR + bucketName + FILE_SEPARETOR + fileName;
			uploadFileTos3bucket(fileName, file);
			if (Files.deleteIfExists(Paths.get(file.getAbsolutePath())))
				logger.info("Temp File Deleted");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return fileUrl;
	}

	public String uploadMedia(File file, String fileName) {
		String fileUrl = "";
		fileName = awsFolder + FILE_SEPARETOR + fileName;
		try {
			fileUrl = endpointUrl + FILE_SEPARETOR + bucketName + FILE_SEPARETOR + fileName;
			uploadFileTos3bucket(fileName, file);
			if (Files.deleteIfExists(Paths.get(file.getAbsolutePath())))
				logger.info("Temp File Deleted");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return fileUrl;
	}
}
