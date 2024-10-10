package com.jwt.springboot.dto.handle;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ResponseDTO <T> {

    private T data;
    private String message;
    private Integer statusCode;

}
