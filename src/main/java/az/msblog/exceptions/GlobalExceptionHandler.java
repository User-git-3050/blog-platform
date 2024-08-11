package az.msblog.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.springframework.http.HttpStatus.*;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleNotFoundException(HttpServletRequest request, NotFoundException e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .status(NOT_FOUND.value())
                .path(request.getServletPath())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(FORBIDDEN)
    public ErrorResponse handleForbiddenException(HttpServletRequest request) {
        return ErrorResponse.builder()
                .message("This action is not permitted")
                .status(FORBIDDEN.value())
                .path(request.getServletPath())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ActionAlreadyDoneException.class)
    @ResponseStatus(METHOD_NOT_ALLOWED)
    public ErrorResponse handleActionAlreadyDoneException(HttpServletRequest request, ActionAlreadyDoneException e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .status(METHOD_NOT_ALLOWED.value())
                .timestamp(LocalDateTime.now())
                .path(request.getServletPath())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        ArrayList<String> errors = new ArrayList<>();
        e.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));

        return ErrorResponse.builder()
                .message("Validation error happened")
                .timestamp(LocalDateTime.now())
                .status(BAD_REQUEST.value())
                .errors(errors)
                .path(request.getServletPath())
                .build();
    }

    @ExceptionHandler(SelfFollowException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleSelfFollowException(HttpServletRequest request, SelfFollowException e) {
        return ErrorResponse.builder()
                .message(e.getMessage()).
                timestamp(LocalDateTime.now())
                .status(BAD_REQUEST.value())
                .path(request.getServletPath())
                .build();
    }

    @ExceptionHandler(SelfUnfollowException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleSelfUnfollowException(HttpServletRequest request, SelfUnfollowException e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getServletPath())
                .status(BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(HttpServletRequest request) {
        return ErrorResponse.builder()
                .message("Something went wrong")
                .timestamp(LocalDateTime.now())
                .path(request.getServletPath())
                .status(INTERNAL_SERVER_ERROR.value())
                .build();
    }


}
