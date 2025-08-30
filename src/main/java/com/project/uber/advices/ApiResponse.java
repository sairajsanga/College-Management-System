package com.project.uber.advices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse<T> {

    private LocalDateTime timestamp;
    private ApiError error;
    private T data;

    public ApiResponse(ApiError apiError){
        this.error=apiError;
    }
    public ApiResponse(T data){
        this.data=data;
    }
}
