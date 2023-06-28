package com.quangtoi.flowerstore.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Url;
import com.cloudinary.utils.ObjectUtils;
import com.quangtoi.flowerstore.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final Cloudinary cloudinary;


    @Override
    public String uploadImage(MultipartFile imageFile) {
        try {
            Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("url");
        } catch (IOException e) {
           throw new RuntimeException("Upload image has error, try it later");
        }
    }

    @Override
    public MultipartFile convertToMultipartFile(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        byte[] imageBytes = IOUtils.toByteArray(url);

        // Tạo đối tượng MultipartFile từ byte array
        MultipartFile multipartFile = new CustomMultipartFile(imageBytes);

        return multipartFile;
    }

    private static class CustomMultipartFile implements MultipartFile {
        private final byte[] fileContent;

        public CustomMultipartFile(byte[] fileContent) {
            this.fileContent = fileContent;
        }

        @Override
        public String getName() {
            return "image.jpg";
        }

        @Override
        public String getOriginalFilename() {
            return "image.jpg";
        }

        @Override
        public String getContentType() {
            return MimeTypeUtils.IMAGE_JPEG_VALUE;
        }

        @Override
        public boolean isEmpty() {
            return fileContent == null || fileContent.length == 0;
        }

        @Override
        public long getSize() {
            return fileContent != null ? fileContent.length : 0;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return fileContent;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(fileContent);
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            new FileOutputStream(dest).write(fileContent);
        }
    }
}
