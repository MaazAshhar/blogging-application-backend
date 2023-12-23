package com.ashhar.blogappapis.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ashhar.blogappapis.exceptions.InvalidFileFormatException;
import com.ashhar.blogappapis.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {

		// File name
		String name = file.getOriginalFilename();

		// checking for valid file type
		HashSet<String> fileType = new HashSet<>();
		fileType.add(".png");
		fileType.add(".jpeg");
		fileType.add(".jpg");

		if (!fileType.contains(name.substring(name.lastIndexOf(".")))) {
			throw new InvalidFileFormatException("only .jpg, .jpeg or .png extension type is supported");
		}

		// random name generation for file

		String randomId = UUID.randomUUID().toString();

		String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));

		// Full path
		String filePath = path + File.separator + fileName1;

		// create folder if not created
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

		// file copy
		Files.copy(file.getInputStream(), Paths.get(filePath));

		return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath=path+File.separator+fileName;
		InputStream inputStream=new FileInputStream(fullPath);
		return inputStream;
	}

}
