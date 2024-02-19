package com.example.publish_service.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Date-2/18/2024
 * By Sardor Tokhirov
 * Time-6:10 AM (GMT+5)
 */
@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic videoProcessingTopic(){
        return TopicBuilder.name("video-processing").build();
    }
}
