package com.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LambdaS3Handler implements RequestHandler<S3Event, String> {

    private final AmazonS3 s3Client;

    public LambdaS3Handler() {
        this.s3Client = AmazonS3ClientBuilder.defaultClient();
    }

    // Constructor for injecting mock S3 client
    public LambdaS3Handler(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String handleRequest(S3Event s3event, Context context) {
        String bucketName = "albertawsbucket";
        String key = "example.txt";
        String content = "Hello, World!";  // The content you want to upload to S3

        // Convert String to InputStream
        InputStream inputStream = new ByteArrayInputStream(content.getBytes());

        // Object Metadata
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("text/plain");  // Change to the content type of your data
        metadata.setContentLength(content.length());  // Set the content length of your data

        // Create PutObjectRequest
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, metadata);

        s3Client.putObject(putObjectRequest);

        // Retrieve the file from S3
        S3Object s3Object = s3Client.getObject(bucketName, key);
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }

        return fileContent.toString();
    }
}
