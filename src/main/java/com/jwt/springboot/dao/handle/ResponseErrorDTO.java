package com.jwt.springboot.dao.handle;

import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponseErrorDTO {
    private String error;
    private String message;
    private String details;
}
