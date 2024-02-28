package com.example.publish_service.controller;

import com.example.publish_service.model.cassandra.ClassPost;
import com.example.publish_service.model.dto.ClassDto;
import com.example.publish_service.model.entity.Language;
import com.example.publish_service.model.payload.PageablePayload;
import com.example.publish_service.service.ClassPostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * Date-2/13/2024
 * By Sardor Tokhirov
 * Time-2:17 AM (GMT+5)
 */

@RestController
@RequestMapping("/api/v1/publish-class")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PublishClassController {

    private final ClassPostService classPostService;

    @PostMapping(value = "/{username}/create")
    public boolean createPost(
                               @PathVariable String username,
                               @RequestParam("photoFile") MultipartFile photoFile,
                               @RequestParam("videoFile") MultipartFile videoFile,
                               @RequestParam("classDto") String classDtoJson
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ClassDto classDto = objectMapper.readValue(classDtoJson, ClassDto.class);
            classPostService.createPost(username, classDto, photoFile, videoFile);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }

    @PostMapping(value = "/{username}/edit/{uuid}")
    public boolean updatePost(
            @PathVariable UUID uuid,
            @RequestParam(value = "photoFile",required = false) MultipartFile photoFile,
            @RequestParam(value = "videoFile",required = false) MultipartFile videoFile,
            @RequestParam(value = "classDto") String classDtoJson
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ClassDto classDto = objectMapper.readValue(classDtoJson, ClassDto.class);
            classPostService.updatePost(uuid, classDto, photoFile, videoFile);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<PageablePayload> getPublishedClasses(@PathVariable String username,
                                                               @RequestParam(required = false, name = "number", defaultValue = "8") int number,
                                                               @RequestParam(required = false, name = "offset", defaultValue = "0") int offset) {
        return ResponseEntity.ok(classPostService.getPublishedClasses(username, number, offset));
    }

    @GetMapping("/languages")
    public ResponseEntity<List<Language>> getLanguages() {
        return ResponseEntity.ok(classPostService.getLanguages());
    }

    @GetMapping("post/{id}")
    public ResponseEntity<ClassPost> getClassPost(@PathVariable UUID id) {
        return ResponseEntity.ok(classPostService.getClassPost(id));
    }
    @GetMapping(value = "/post/image/{uuid}")
    public ResponseEntity<String> getFile(@PathVariable String uuid) {
        byte[] imageBytes = classPostService.getPostImage(uuid);
        return ResponseEntity.ok(Base64.getEncoder().encodeToString(imageBytes));
    }
}
