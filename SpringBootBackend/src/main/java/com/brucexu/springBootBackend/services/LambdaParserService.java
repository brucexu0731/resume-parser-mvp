package com.brucexu.springBootBackend.services;

import com.brucexu.springBootBackend.dto.resume.ParsedResume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Service
public class LambdaParserService {

    @Autowired
    private LambdaClient lambdaClient;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * Service responsible for invoking the AWS Lambda resume parser.
     *
     * <p>Sends the S3 bucket name and object key to the Lambda function,
     * waits for the parsed resume response, and deserializes the returned
     * JSON into a {@link ParsedResume} object.</p>
     */
    public ParsedResume parseResume(String bucket, String key) {
        Map<String, String> payload = Map.of(
                "bucket", bucket,
                "key", key
        );

        String json = objectMapper.writeValueAsString(payload);

        InvokeRequest request = InvokeRequest.builder()
                .functionName("lambda-parser1")
                .payload(SdkBytes.fromUtf8String(json))
                .build();

        InvokeResponse response = lambdaClient.invoke(request);

        ParsedResume res = objectMapper.readValue(
                response.payload().asUtf8String(),
                ParsedResume.class
        );

        return res;
    }
}
