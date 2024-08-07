package com.example.publish_service.service;

import com.example.publish_service.model.cassandra.ClassPost;
import com.example.publish_service.model.dto.ClassDto;
import com.example.publish_service.model.entity.Language;
import com.example.publish_service.model.entity.Teacher;
import com.example.publish_service.model.payload.PageablePayload;
import com.example.publish_service.model.payload.PublishedClassPagePayload;
import com.example.publish_service.repository.LanguageRepository;
import com.example.publish_service.repository.TeacherRepository;
import com.example.publish_service.repository.cassandra.ClassPostRepository;
import com.example.publish_service.s3.S3Buckets;
import com.example.publish_service.s3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Date-2/8/2024
 * By Sardor Tokhirov
 * Time-5:40 AM (GMT+5)
 */
@Service
public class ClassPostService {

    private final ClassPostRepository classPostRepository;
    private final LanguageRepository languageRepository;

    private final TeacherRepository teacherRepository;

    private final S3Service s3Service;
    private final S3Buckets s3Buckets;

    private final ElasticsearchService elasticsearchService;

    @Autowired
    public ClassPostService(ClassPostRepository classPostRepository, LanguageRepository languageRepository, TeacherRepository teacherRepository, S3Service s3Service, S3Buckets s3Buckets, ElasticsearchService elasticsearchService) {
        this.classPostRepository = classPostRepository;
        this.languageRepository = languageRepository;
        this.teacherRepository = teacherRepository;
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
        this.elasticsearchService = elasticsearchService;
    }

    public void updatePost(UUID uuid, ClassDto classDto, MultipartFile photoFile, MultipartFile videoFile) {
        ClassPost classPost = classPostRepository.findById(uuid).orElseThrow();
        boolean isPrivate= classPost.isPrivate();
        setClassPostData(classDto, classPost);
        if (photoFile != null) {
            deleteFileData(s3Buckets.getPostImage(), classPost.getIntroVideoImgLink());
            UUID videoImageId = UUID.randomUUID();
            uploadPostImage(photoFile, videoImageId.toString());
            classPost.setIntroVideoImgLink(videoImageId.toString());
        }
        if (videoFile != null) {
            deleteFileData(s3Buckets.getPostVideos(), classPost.getIntroVideoLink());
            UUID videoId = UUID.randomUUID();
            uploadPostVideo(videoFile, videoId.toString());
            classPost.setIntroVideoLink(videoId.toString());
        }
        classPostRepository.save(classPost);
        if (classDto.getIsPrivate()) {
            if (isPrivate){
                elasticsearchService.deletePost(classPost.getPostId());
            }
        } else {
            elasticsearchService.updatePost(classPost.getPostId(), classDto, classPost.getIntroVideoImgLink());
        }
    }

    public void createPost(String username, ClassDto classDto, MultipartFile photoFile, MultipartFile videoFile) {
        Teacher teacher = teacherRepository.findTeacherByUsername(username).orElseThrow();
        ClassPost classPost = new ClassPost();
        classPost.setPostId(UUID.randomUUID());
        classPost.setTeacherId(teacher.getTeacherId());
        setClassPostData(classDto, classPost);
        UUID videoId = UUID.randomUUID();
        UUID videoImageId = UUID.randomUUID();
        uploadPostVideo(videoFile, videoId.toString());
        uploadPostImage(photoFile, videoImageId.toString());
        classPost.setIntroVideoImgLink(videoImageId.toString());
        classPost.setIntroVideoLink(videoId.toString());
        classPostRepository.save(classPost);
        if (!classDto.getIsPrivate())
            elasticsearchService.saveClassDto(classDto, classPost.getPostId(), String.valueOf(videoImageId));
    }

    private void setClassPostData(ClassDto classDto, ClassPost classPost) {
        classPost.setTitle(classDto.getTitle());
        classPost.setDescription(classDto.getDescription());
        classPost.setCourseTarget(classDto.getCourseTarget());
        classPost.setRequirements(classDto.getRequirements());
        classPost.setLanguage(languageRepository.existsByLanguage(classDto.getLanguage()) ? classDto.getLanguage() : "English");
        classPost.setPrice(BigDecimal.valueOf(classDto.getPrice()));
        classPost.setMaxStudents(classDto.getMaxStudents());
        classPost.setDemoTime(classDto.getDemoTime());
        classPost.setClassDays(classDto.getClassDays());
        classPost.setClassTime(classDto.getClassTime());
        classPost.setTags(classDto.getTags());
        classPost.setRoadmap(classDto.getRoadmap());
        classPost.setPrivate(classDto.getIsPrivate());
        classPost.setRoadmapPresent(classDto.getIsRoadmapPresent());
        classPost.setClassEndDate(classDto.getClassEndDate());
        classPost.setClassStartDate(classDto.getClassStartDate());
        classPost.setDuration(classDto.getDuration());
    }

    public void uploadPostImage(MultipartFile file, String randomId) {
        try {
            s3Service.putObject(
                    s3Buckets.getPostImage(),
                    "%s".formatted(randomId),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void uploadPostVideo(MultipartFile file, String randomId) {
        try {
            s3Service.putObject(
                    s3Buckets.getPostVideos(),
                    "%s".formatted(randomId),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getPostImage(String uuid) {
        if (uuid == null) return null;

        return s3Service.getObject(
                s3Buckets.getPostImage(),
                "%s".formatted(uuid)
        );
    }

    public void deleteFileData(String bucketName, String uuid) {
        s3Service.deleteObject(
                bucketName,
                "%s".formatted(uuid)
        );
    }

    public PageablePayload getPosts(String username, int number, int offset) {
        UUID teacherId = teacherRepository.findTeacherByUsername(username).orElseThrow().getTeacherId();
        List<ClassPost> classPostList = classPostRepository.findClassesByTeacherId(teacherId);
        List<PublishedClassPagePayload> classPagePayloads = classPostList.stream().skip(offset).limit(number).map(data -> {
            PublishedClassPagePayload classPagePayload = new PublishedClassPagePayload();
            classPagePayload.setPostId(data.getPostId());
            classPagePayload.setTitle(data.getTitle());
            classPagePayload.setImageId(data.getIntroVideoImgLink());
            return classPagePayload;
        }).collect(Collectors.toList());
        return new PageablePayload(
                classPostList.size(),
                classPagePayloads
        );
    }

    public ClassPost getClassPost(UUID uuid) {
        ClassPost classPost = classPostRepository.findById(uuid).orElseThrow();
        return classPost;
    }

    public List<Language> getLanguages() {
        return languageRepository.findAll();
    }

    public void deletePost(UUID uuid) {
        ClassPost classPost = classPostRepository.findById(uuid).orElseThrow();
        deleteFileData(s3Buckets.getPostVideos(), classPost.getIntroVideoLink());
        deleteFileData(s3Buckets.getPostImage(), classPost.getIntroVideoImgLink());
        classPostRepository.deleteById(uuid);
        elasticsearchService.deletePost(uuid);
    }
}
