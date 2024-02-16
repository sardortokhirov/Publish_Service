package com.example.publish_service.model.cassandra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Date-2/7/2024
 * By Sardor Tokhirov
 * Time-5:51 PM (GMT+5)
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("class_post")
public class ClassPost {

    @PrimaryKey
    @Column("post_id")
    private UUID postId;

    @Column("teacher_id")
    @Indexed
    private UUID teacherId;

    private String title;

    private String description;

    @Column("course_target")
    private String courseTarget;

    private List<String> requirements;
    @Column("language_id")
    private Integer languageId;

    private Double price;

    @Column("max_students")
    private int maxStudents;

    @Column("demo_time")
    private Date demoTime;

    @Column("class_days")
    private List<String> classDays;

    @Column("class_time")
    private Date classTime;

    @Column("intro_video_link")
    private String introVideoLink;

    @Column("intro_video_img_link")
    private String introVideoImgLink;

    private String category;

    private List<String> tags;


}
