package com.muller.racha_api.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class R2StorageService {
    private final S3Template s3Template;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${spring.storage.public-url}")
    private String publicUrl;

    public String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalArgumentException("Arquivo sem nome");
        }

        String extension = StringUtils.getFilenameExtension(originalFilename);
        String key = UUID.randomUUID().toString() + "." + extension;

        try {
            s3Template.upload(bucketName, key, file.getInputStream());

            return publicUrl + "/" + key;
        } catch (IOException e) {
            throw new RuntimeException("Falha ao fazer upload da imagem", e);
        }
    }

    public void delete(String fileUrl) {
        if (fileUrl == null || fileUrl.isBlank())
            return;

        try {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

            s3Template.deleteObject(bucketName, fileName);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao deletar a imagem" + e.getMessage(), e);
        }
    }

}
