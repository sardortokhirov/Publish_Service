package com.example.publish_service.kafka;

import lombok.*;

import java.io.Serializable;


/**
 * Date-2/18/2024
 * By Sardor Tokhirov
 * Time-6:07 AM (GMT+5)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KafkaMessage  implements Serializable {
    private String id;
    private String username;
}
