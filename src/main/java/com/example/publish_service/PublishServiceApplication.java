package com.example.publish_service;

import com.example.publish_service.kafka.KafkaMessage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;


@SpringBootApplication
public class PublishServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublishServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(KafkaTemplate<String, KafkaMessage> kafkaTemplate) {
        return args -> {
//            while (true){
//                System.out.println("helloo--------------------------------------------------");
                kafkaTemplate.send("video-processing",new KafkaMessage("name","sardor_user"));
//                    Thread.sleep(1000);
//            }
        };
    }

}
