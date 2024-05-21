package com.ferremas.ferremas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class ErrorListResponse {
    @NonNull
    HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    LocalDateTime timestamp = LocalDateTime.now();
    @NonNull
    String message;
    @NonNull
    HashMap<String, String> errors;
}
