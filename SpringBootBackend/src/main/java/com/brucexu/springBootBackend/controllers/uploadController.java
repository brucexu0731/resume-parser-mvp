package com.brucexu.springBootBackend.controllers;

import com.brucexu.springBootBackend.dto.response.SaveResponseDTO;
import com.brucexu.springBootBackend.dto.resume.ParsedResume;
import com.brucexu.springBootBackend.entity.Personal;
import com.brucexu.springBootBackend.entity.Upload;
import com.brucexu.springBootBackend.entity.UploadStatus;
import com.brucexu.springBootBackend.repository.*;
import com.brucexu.springBootBackend.services.EmbeddingService;
import com.brucexu.springBootBackend.services.LambdaParserService;
import com.brucexu.springBootBackend.services.ResumeSaveService;
import com.brucexu.springBootBackend.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class uploadController {

    @Autowired
    private UploadRepository uploadRepo;
    @Autowired
    private EducationRepository educationRepo;
    @Autowired
    private WorkExperienceRepository workExperienceRepo;
    @Autowired
    private PersonalRepository personalRepo;
    @Autowired
    private WorkExperienceContentRespository workExperienceContentRespository;

    @Autowired
    private EmbeddingService embeddingService;
    @Autowired
    private S3Service s3Service;
    @Autowired
    private ResumeSaveService resumeSaveService;

    @Autowired
    private LambdaParserService lambdaParserService;

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
     * Takes the uploaded file and calls the lambda parsing pipeline to parse,
     * return the parsed fields for user to validate
     * @param uploadId
     * @return
     */
    @PostMapping("/uploads/parse/{uploadId}")
    public ParsedResume parseUpload(@PathVariable Long uploadId){

        Upload upload = uploadRepo.findById(uploadId)
                .orElseThrow(() ->
                        new RuntimeException("Upload not found")
                );
        upload.setStatus(UploadStatus.UPLOADED);
        String key = upload.getS3Key();
        ParsedResume parsedResume = lambdaParserService.parseResume(bucketName, key);
        return parsedResume;
    }

    /**
     * Takes the validated inputs, check if the profile is already in database,
     * embeds the work contents fields, and saves into database.
     * @Body Validated fields casted into ParsedResume
     * @return the personal_id of the profile create or the duplicated profile
     */
    @PostMapping("/uploads/save")
    public SaveResponseDTO validateAndSaveUpload(@RequestBody ParsedResume validatedResume){
        //TO DO:
        // check if the person's profile is already in the database, if so, return user_id

        Personal savedResume = resumeSaveService.saveParsedResume(validatedResume);
        Long personalId = savedResume.getPersonalId();
        SaveResponseDTO res = new SaveResponseDTO(personalId, "SUCCESS");

        return res;
    }


    @PostMapping("/uploads/testParsing")
    public ParsedResume testParsing(@RequestBody Map<String, String> body) {
        ParsedResume parsedResume = lambdaParserService.parseResume(body.get("bucket"), body.get("key"));
        resumeSaveService.saveParsedResume(parsedResume);
        return parsedResume;
    }

    @PostMapping("/uploads/testRAG")
    public List<String> testRAG(@RequestBody Map<String, String> body) {

        String query = body.get("query");
        float[] queryEmbedding = embeddingService.embed(query);
        String vector = embeddingService.toVectorString(queryEmbedding);

        return workExperienceContentRespository.findSimilar(vector, 1);
    }

}
