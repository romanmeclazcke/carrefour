package org.example.carrefour.Utils.Dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
public class ErrorDto {
    private String message;
    private String errorCode;
    private LocalDateTime timestamp;
}
