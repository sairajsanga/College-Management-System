package com.project.uber.advices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ApiError {

    private HttpStatus status;
    private String message;
    private List<String> subErrors;
}
