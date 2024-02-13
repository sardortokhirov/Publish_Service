package com.example.publish_service.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.util.UUID;

/**
 * Date-21/01/2024
 * By Sardor Tokhirov
 */
@Entity
@Table(name = "teachers")
@AllArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "teacherId", columnDefinition = "uuid")
    private UUID teacherId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    private String bio;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Teacher() {
    }

    public Teacher(User user) {
        this.user = user;
    }

    public UUID getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(UUID teacherId) {
        this.teacherId = teacherId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
