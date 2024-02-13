package com.example.publish_service.controller;

import com.example.publish_service.model.dto.ClassDto;
import com.example.publish_service.model.payload.PageablePayload;
import com.example.publish_service.service.ClassPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value = "/{username}/new-class", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadNewClass(@PathVariable String username, @RequestBody ClassDto classDto) {
        classPostService.uploadNewClass(username, classDto);
    }

    @GetMapping("/{username}")
    public ResponseEntity<PageablePayload> getPublishedClasses(@PathVariable String username,
                                                               @RequestParam(required = false, name = "number", defaultValue = "8") int number,
                                                               @RequestParam(required = false, name = "offset", defaultValue = "0") int offset){
        return ResponseEntity.ok(classPostService.getPublishedClasses(username,number,offset));
    }
}
