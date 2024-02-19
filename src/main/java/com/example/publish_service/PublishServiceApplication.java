package com.example.publish_service;

import com.example.publish_service.kafka.KafkaMessage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;


@SpringBootApplication
public class PublishServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublishServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(KafkaTemplate<String, KafkaMessage> kafkaTemplate) {
        return args -> {
                kafkaTemplate.send("video-processing",new KafkaMessage(UUID.randomUUID().toString(),"sardor_user",new byte[0]));
        };
    }

}
