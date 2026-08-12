package com.brucexu.springBootBackend.controllers;

import com.brucexu.springBootBackend.entity.Upload;
import com.brucexu.springBootBackend.entity.UploadStatus;
import com.brucexu.springBootBackend.repository.UploadRepository;
import com.brucexu.springBootBackend.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class uploadController {

    @Autowired
    private UploadRepository uploadRepo;

    @Autowired
    private S3Service s3Service;

    @Value("${aws.bucket.name}")
    private String bucketName;

    /** pings the server to check if it's up */
    @GetMapping("/uploads/test")
    public String testGet (){
        LocalDateTime today = LocalDateTime.now();
        return today.toString();
    }

    /**
     * Generates a pre-signed URL that allows a client to upload
     * a file directly to S3.
     *
     * Stores the Upload into the database --> reference the Upload entity
     *
     * @param body Request body containing the original filename.
     * @return A map containing the pre-signed upload URL, object key and uploadId
     */
    @PostMapping ("/uploads/presign")
    public Map<String, String> getUploadUrl(@RequestBody Map<String, Object> body) {

        String key = "uploads/" + UUID.randomUUID() + "-" + body.get("filename");

        String url = s3Service.generateUploadUrl(
                bucketName,
                key
        );

        Upload upload = new Upload(null, key, UploadStatus.UPLOADING);
        uploadRepo.save(upload);
        Long uploadId = upload.getUploadId();

        return Map.of(
                "uploadUrl", url,
                "objectKey", key,
                "uploadId", uploadId.toString()
        );
    }

    /**
     * Takes the uploaded file and calls the lambda parsing pipeline to parse
     * @param key
     * @param uploadId
     * @return
     */
    @PostMapping("/uploads/validate/{uploadId}")
    public String validateUpload(@RequestBody String key, @PathVariable Long uploadId){

        Upload upload = uploadRepo.findById(uploadId)
                .orElseThrow(() ->
                        new RuntimeException("Upload not found")
                );
        upload.setStatus(UploadStatus.UPLOADED);

        return "";
    }
}
