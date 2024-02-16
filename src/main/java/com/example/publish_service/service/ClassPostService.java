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

    public ClassPostService(ClassPostRepository classPostRepository, LanguageRepository languageRepository, TeacherRepository teacherRepository, S3Service s3Service, S3Buckets s3Buckets) {
        this.classPostRepository = classPostRepository;
        this.languageRepository = languageRepository;
        this.teacherRepository = teacherRepository;
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
    }

    public void uploadNewClass(String username, ClassDto classDto) {

        Teacher teacher = teacherRepository.findTeacherByUsername(username);
        if (teacher == null) {
            System.err.println("Teacher doesn't exist");
        }
        ClassPost classPost = new ClassPost();
        classPost.setPostId(UUID.randomUUID());
        classPost.setTeacherId(teacher.getTeacherId());
        classPost.setTitle(classDto.getTitle());
        classPost.setDescription(classDto.getDescription());
        classPost.setCourseTarget(classDto.getCourseTarget());
        classPost.setRequirements(classDto.getRequirements());
        classPost.setLanguageId(languageRepository.existsById(classDto.getLanguageId()) ? classDto.getLanguageId() : 1);
        classPost.setPrice(classDto.getPrice());
        classPost.setMaxStudents(classDto.getMaxStudents());
        classPost.setDemoTime(classDto.getDemoTime());
        classPost.setClassTime(classDto.getClassTime());
        classPost.setCategory(classDto.getCategory());
        classPost.setTags(classDto.getTags());
        UUID videoId = UUID.randomUUID();
        UUID videoImageId = UUID.randomUUID();
        uploadClassPostData(username, classDto.getPhotoFile(), videoId.toString());
        uploadClassPostData(username, classDto.getVideoFile(), videoImageId.toString());
        classPost.setIntroVideoImgLink(videoImageId.toString());
        classPost.setIntroVideoLink(videoId.toString());
        classPostRepository.save(classPost);
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

    public byte[] getUserProfileImage(String username, String uuid) {
        if (username == null || uuid == null) return null;

        return s3Service.getObject(
                s3Buckets.getUser(),
                "profile-images/%s/%s".formatted(username, uuid)
        );
    }

    public PageablePayload getPublishedClasses(String username, int number, int offset) {
        UUID teacherId = teacherRepository.findTeacherByUsername(username).getTeacherId();
        List<ClassPost> classPostList = classPostRepository.findClassesByTeacherId(teacherId);
        List<PublishedClassPagePayload> classPagePayloads= classPostList.stream().skip(offset).limit(number).map(data -> {
            PublishedClassPagePayload classPagePayload = new PublishedClassPagePayload();
            classPagePayload.setPostId(data.getPostId());
            classPagePayload.setTitle(data.getTitle());
            classPagePayload.setPhotoFile(getUserProfileImage(username, data.getIntroVideoImgLink()));
            return classPagePayload;
        }).collect(Collectors.toList());
        return new PageablePayload(
                classPostList.size(),
                classPagePayloads
        );
    }

    public List<Language> getLanguages(){
        return languageRepository.findAll();
    }
}
