package com.example.publish_service.model.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Date-2/13/2024
 * By Sardor Tokhirov
 * Time-4:59 AM (GMT+5)
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublishedClassPagePayload {
    private UUID postId;
    private String title;
    private byte[] photoFile;
}
