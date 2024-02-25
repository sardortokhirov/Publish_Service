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

    public ClassPostService(ClassPostRepository classPostRepository, LanguageRepository languageRepository, TeacherRepository teacherRepository, S3Service s3Service, S3Buckets s3Buckets, ElasticsearchService elasticsearchService) {
        this.classPostRepository = classPostRepository;
        this.languageRepository = languageRepository;
        this.teacherRepository = teacherRepository;
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
        this.elasticsearchService = elasticsearchService;
    }

    public void updatePost(UUID uuid, String username, ClassDto classDto, MultipartFile photoFile, MultipartFile videoFile) {
        ClassPost classPost = classPostRepository.findById(uuid).orElseThrow();
        setClassPostData(classDto, classPost);
        classPost.setTags(classDto.getTags());
        if (photoFile != null) {
            deleteFileData(username, classPost.getIntroVideoImgLink());
            UUID videoImageId = UUID.randomUUID();
            uploadClassPostData(username, photoFile, videoImageId.toString());
            classPost.setIntroVideoImgLink(videoImageId.toString());
        }
        if (videoFile != null) {
            deleteFileData(username, classPost.getIntroVideoLink());
            UUID videoId = UUID.randomUUID();
            uploadClassPostData(username, videoFile, videoId.toString());
            classPost.setIntroVideoLink(videoId.toString());
        }
        classPostRepository.save(classPost);
        elasticsearchService.updatePost(classPost.getPostId(), classDto,classPost.getIntroVideoImgLink());
    }

    public void createPost(String username, ClassDto classDto, MultipartFile photoFile, MultipartFile videoFile) {
        Teacher teacher = teacherRepository.findTeacherByUsername(username);
        if (teacher == null) {
            System.err.println("Teacher doesn't exist");
        }
        ClassPost classPost = new ClassPost();
        classPost.setPostId(UUID.randomUUID());
        classPost.setTeacherId(teacher.getTeacherId());
        setClassPostData(classDto, classPost);
        UUID videoId = UUID.randomUUID();
        UUID videoImageId = UUID.randomUUID();
        uploadClassPostData(username, videoFile, videoId.toString());
        uploadClassPostData(username, photoFile, videoImageId.toString());
        classPost.setIntroVideoImgLink(videoImageId.toString());
        classPost.setIntroVideoLink(videoId.toString());
        classPostRepository.save(classPost);
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
        classPost.setCategory(classDto.getCategory());
        classPost.setTags(classDto.getTags());
    }

    public void uploadClassPostData(String username, MultipartFile file, String randomId) {
        try {
            s3Service.putObject(
                    s3Buckets.getUser(),
                    "profile-images/%s/%s".formatted(username, randomId),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getUserFileData(String username, String uuid) {
        if (username == null || uuid == null) return null;

        return s3Service.getObject(
                s3Buckets.getUser(),
                "profile-images/%s/%s".formatted(username, uuid)
        );
    }

    public void deleteFileData(String username, String uuid) {
        s3Service.deleteObject(
                s3Buckets.getUser(),
                "profile-images/%s/%s".formatted(username, uuid)
        );
    }

    public PageablePayload getPublishedClasses(String username, int number, int offset) {
        UUID teacherId = teacherRepository.findTeacherByUsername(username).getTeacherId();
        List<ClassPost> classPostList = classPostRepository.findClassesByTeacherId(teacherId);
        List<PublishedClassPagePayload> classPagePayloads = classPostList.stream().skip(offset).limit(number).map(data -> {
            PublishedClassPagePayload classPagePayload = new PublishedClassPagePayload();
            classPagePayload.setPostId(data.getPostId());
            classPagePayload.setTitle(data.getTitle());
            classPagePayload.setPhotoFile(getUserFileData(username, data.getIntroVideoImgLink()));
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
}
