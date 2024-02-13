package com.example.publish_service.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Date-2/13/2024
 * By Sardor Tokhirov
 * Time-2:34 AM (GMT+5)
 */
@Entity
@Table(name = "languages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "language_id")
    private Integer languageId;

    private String language;
}
