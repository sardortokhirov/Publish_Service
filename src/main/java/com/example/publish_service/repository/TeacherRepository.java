package com.example.publish_service.repository;

import com.example.publish_service.model.entity.Teacher;
import com.example.publish_service.model.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

/**
 * Date-21/01/2024
 * By Sardor Tokhirov
 */

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    @Query("SELECT s FROM Teacher s WHERE s.user.userName = :username")
    Optional<Teacher> findTeacherByUsername(@Param("username") String username);

    Teacher findByUser(User user);
}
