package com.example.publish_service.repository.elastic;

import com.example.publish_service.model.elastic.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Date-2/25/2024
 * By Sardor Tokhirov
 * Time-8:01 AM (GMT+5)
 */
@Repository
public interface PostRepository extends ElasticsearchRepository<Post, String> {


}