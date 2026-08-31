package com.brucexu.springBootBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

/**
 * Service responsible for interacting with Amazon S3.
 *
 * <p>Provides utilities for generating pre-signed URLs that allow clients
 * to upload files directly to an S3 bucket without routing file contents
 * through the backend server.</p>
 */
@Service
public class S3Service {

    @Autowired
    private S3Client s3Client;

    @Autowired
    private S3Presigner s3Presigner;

    /**
     * Generates a pre-signed S3 PUT URL for uploading a PDF file.
     *
     * <p>The generated URL is valid for 10 minutes and grants temporary
     * permission to upload a single object to the specified bucket and key.
     * The uploaded object is expected to have the content type
     * {@code application/pdf}.</p>
     *
     * @param bucket the name of the target S3 bucket
     * @param key the object key (path and filename) where the file will be stored
     * @return a pre-signed URL that can be used to upload the object directly to S3
     */
    public String generateUploadUrl(String bucket, String key) {

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType("application/pdf")
                .build();

        PutObjectPresignRequest presignRequest =
                PutObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(10))
                        .putObjectRequest(putRequest)
                        .build();

        return s3Presigner
                .presignPutObject(presignRequest)
                .url()
                .toString();
    }

    public void deleteObject(String bucket, String key){
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build());
    }



}
