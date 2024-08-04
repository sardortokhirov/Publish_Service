package com.example.publish_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Date-2/13/2024
 * By Sardor Tokhirov
 * Time-12:48 AM (GMT+5)
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassDto {

    private String title;
    private String description;
    private List<String> courseTarget;
    private List<String> requirements;

    private String language;
    private Double price;

    private Integer maxStudents;

    private Date demoTime;

    private List<String> classDays;

    private Date classStartDate;
    private Date classEndDate;
    private Date classTime;
    private int duration;

    private List<String> tags;
    private List<String> roadmap;

    private Boolean isPrivate;

    private Boolean isRoadmapPresent;
}
