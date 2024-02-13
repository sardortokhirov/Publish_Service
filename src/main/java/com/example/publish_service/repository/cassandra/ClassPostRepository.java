package com.example.publish_service.repository.cassandra;

import com.example.publish_service.model.cassandra.ClassPost;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Date-2/8/2024
 * By Sardor Tokhirov
 * Time-5:38 AM (GMT+5)
 */
@Repository
public interface ClassPostRepository extends CassandraRepository<ClassPost, UUID> {
    @Query("SELECT * FROM class_post WHERE teacher_id = ?0")
    List<ClassPost> findClassesByTeacherId(UUID teacherId);
}
