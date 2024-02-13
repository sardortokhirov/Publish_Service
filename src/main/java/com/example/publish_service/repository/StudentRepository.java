package com.example.publish_service.repository;


import com.example.publish_service.model.entity.Student;
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

public interface StudentRepository extends JpaRepository<Student, UUID> {

    @Query("SELECT s FROM Student s WHERE s.user.userName = :username")
   Optional<Student>  findStudentByUsername(@Param("username") String username);

    Student findByUser(User user);


}
