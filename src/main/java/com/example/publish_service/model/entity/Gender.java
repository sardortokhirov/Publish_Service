package com.example.publish_service.model.entity;

import jakarta.persistence.*;

/**
 * Date-21/01/2024
 * By Sardor Tokhirov
 */
@Entity
@Table(name = "genders")
public class Gender {
    @Id
    @Column(name = "gender_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gender_sequence")
    @SequenceGenerator(name = "gender_sequence", sequenceName = "gender_sequence_name", allocationSize = 1)
    private Integer genderId;

    @Column(name = "gender_name", unique = true)
    private String genderName;

    public Gender() {
    }

    public Gender(String genderName) {
        this.genderName = genderName;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }
}
