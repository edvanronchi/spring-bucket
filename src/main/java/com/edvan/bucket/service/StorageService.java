package com.edvan.bucket.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.edvan.bucket.http.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
public class StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public ResponseEntity<?> uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        try {
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));

            fileObj.delete();

            return new Response().success("Arquivo enviado!", HttpStatus.OK, null);
        } catch (Throwable e) {
            return new Response().error("Houve um erro!");
        }
    }

    public ResponseEntity<?> downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        try {
            byte[] data = IOUtils.toByteArray(inputStream);

            return ResponseEntity
                    .ok()
                    .contentLength(data.length)
                    .header("Content-type", "application/octet-stream")
                    .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                    .body(new ByteArrayResource(data));

        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    public ResponseEntity<?> listAll() {
        ObjectMapper mapper = new ObjectMapper();

        ArrayNode contentArray = mapper.createArrayNode();

        s3Client.listObjects(bucketName).getObjectSummaries().forEach( i -> {
            contentArray.add(mapper.createObjectNode().put("name", i.getKey()));
        });

        return new ResponseEntity<>(contentArray, HttpStatus.OK);
    }

    public ResponseEntity<?> viewFile(String fileName) {
        ObjectNode content = new ObjectMapper().createObjectNode();

        String url = s3Client.generatePresignedUrl(bucketName, fileName, Date.from(new Date().toInstant().plusSeconds(60))).toString();
        content.put("url", url);

        return new Response().success("URL Gerada!", HttpStatus.OK, content);
    }

    public ResponseEntity<?> deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);

        return new Response().success("Arquivo deletado!", HttpStatus.OK, null);
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));

        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }

        return convertedFile;
    }
}