package com.example.publish_service.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Date-2/1/2024
 * By Sardor Tokhirov
 * Time-7:48 AM (GMT+5)
 */
@Configuration
public class S3Config {
    @Value("${aws.region}")
    String region;

    @Bean
    public S3Client s3Client() {
        S3Client client = S3Client.builder()
                .region(Region.of(region))
                .build();
        return client;
    }
}
