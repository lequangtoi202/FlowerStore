package com.quangtoi.flowerstore.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;


public interface ImageService {
    String uploadImage(MultipartFile imageFile);
    MultipartFile convertToMultipartFile(String imageUrl) throws IOException;
}
