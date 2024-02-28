package com.example.publish_service.model.elastic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Date-2/25/2024
 * By Sardor Tokhirov
 * Time-7:57 AM (GMT+5)
 */

@Document(indexName = "post")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Keyword)
    private List<String> courseTarget;

    @Field(type = FieldType.Keyword)
    private String language;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Integer)
    private Integer maxStudents;

    @Field(type = FieldType.Keyword)
    private List<String> classDays;

    @Field(type = FieldType.Date)
    private Date classTime;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Keyword)
    private String imageId;

    @Field(type = FieldType.Keyword)
    private List<String> tags;

    public Post(String id) {
        this.id = id;
    }
}
