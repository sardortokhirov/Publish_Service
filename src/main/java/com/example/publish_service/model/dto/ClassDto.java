package com.example.publish_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Date-2/13/2024
 * By Sardor Tokhirov
 * Time-12:48 AM (GMT+5)
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClassDto {

    private UUID postId;
    private Long teacherId;
    private String title;
    private String description;
    private String courseTarget;
    private List<String> requirements;
    private Integer languageId;
    private Double price;
    private Integer maxStudents;
    private Date demoTime;
    private List<String> classDays;
    private Date classTime;
    private MultipartFile photoFile;
    private MultipartFile videoFile;
    private String category;
    private List<String> tags;
}
