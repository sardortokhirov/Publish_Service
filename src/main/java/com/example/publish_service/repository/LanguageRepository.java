package com.example.publish_service.repository;

import com.example.publish_service.model.entity.Language;
import com.example.publish_service.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Date-2/13/2024
 * By Sardor Tokhirov
 * Time-2:36 AM (GMT+5)
 */
@Repository
public interface LanguageRepository  extends JpaRepository<Language, Integer> {
}
