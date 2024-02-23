package com.example.publish_service.controller;

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

import java.util.List;

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

    @PostMapping(value = "/{username}/create-edit")
    public void uploadNewClass(
                               @PathVariable String username,
                               @RequestParam("photoFile") MultipartFile photoFile,
                               @RequestParam("videoFile") MultipartFile videoFile,
                               @RequestParam("classDto") String classDtoJson
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ClassDto classDto = objectMapper.readValue(classDtoJson, ClassDto.class);
            classPostService.uploadNewClass(username, classDto, photoFile, videoFile);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
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
}
