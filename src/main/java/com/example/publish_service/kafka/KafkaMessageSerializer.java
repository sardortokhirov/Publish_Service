package com.example.publish_service.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

/**
 * Date-2/19/2024
 * By Sardor Tokhirov
 * Time-4:03 AM (GMT+5)
 */
public class KafkaMessageSerializer implements Serializer<KafkaMessage> {

    @Override
    public byte[] serialize(String topic, KafkaMessage data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error serializing KafkaMessage");
        }
    }
}