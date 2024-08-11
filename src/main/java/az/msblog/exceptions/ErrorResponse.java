package az.msblog.exceptions;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class ErrorResponse {
    private String message;
    private Integer status;
    private String path;
    private LocalDateTime timestamp;
    List<String> errors;
}
