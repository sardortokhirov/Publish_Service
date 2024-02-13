package com.example.publish_service.model.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Date-2/13/2024
 * By Sardor Tokhirov
 * Time-4:20 AM (GMT+5)
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageablePayload {
    private long totalElements;
    private Object data;
}
