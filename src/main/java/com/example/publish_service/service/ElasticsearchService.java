package com.example.publish_service.service;


import com.example.publish_service.model.dto.ClassDto;
import com.example.publish_service.model.elastic.Post;
import com.example.publish_service.repository.elastic.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;


/**
 * Date-2/25/2024
 * By Sardor Tokhirov
 * Time-6:11 AM (GMT+5)
 */
@Service
public class ElasticsearchService {
    private final PostRepository postRepository;

    @Autowired
    public ElasticsearchService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void saveClassDto(ClassDto classDto, UUID uuid,String id) {
        Post post = new Post();
        mapClassDtoToPost(classDto, post,id);
        post.setId(String.valueOf(uuid));
        postRepository.save(post);
    }

    public void updatePost(UUID postId, ClassDto classDto,String id) {
        Post existingPost = postRepository.findById(String.valueOf(postId)).orElse(new Post(postId.toString()));
        mapClassDtoToPost(classDto, existingPost,id);
        postRepository.save(existingPost);
    }

    private void mapClassDtoToPost(ClassDto classDto, Post post, String id) {
        post.setTitle(classDto.getTitle());
        post.setCourseTarget(classDto.getCourseTarget());
        post.setLanguage(classDto.getLanguage());
        post.setPrice(classDto.getPrice());
        post.setMaxStudents(classDto.getMaxStudents());
        post.setImageId(id);
        post.setClassDays(classDto.getClassDays());
        post.setClassTime(classDto.getClassTime());
        post.setTags(classDto.getTags());
    }

    public void deletePost(UUID postId){
        postRepository.deleteById(String.valueOf(postId));
    }
}
