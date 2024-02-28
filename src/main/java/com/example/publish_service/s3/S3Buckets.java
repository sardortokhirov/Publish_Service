package com.example.publish_service.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Date-2/1/2024
 * By Sardor Tokhirov
 * Time-3:30 PM (GMT+5)
 */
@Configuration
@ConfigurationProperties(prefix = "aws.s3.buckets")
public class S3Buckets {
    private String user;
    private String postVideos;

    private String postImage;

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPostVideos() {
        return postVideos;
    }

    public void setPostVideos(String postVideos) {
        this.postVideos = postVideos;
    }
}
