package com.example.startoffice.apiPayload.exception;

import com.example.startoffice.apiPayload.ApiResponse;
import com.example.startoffice.apiPayload.Status;
import com.example.startoffice.app.controller.NoticeController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = {RestController.class}, basePackages = {"com.example.HyThon.web.controller"})
public class NoticeExceptionHandler {
    @ExceptionHandler(NoticeException.NoticeNonExistException.class)
    public ResponseEntity<ApiResponse<?>> handleNoticeExcption(NoticeException.NoticeNonExistException ex) {
        return new ResponseEntity<>(ApiResponse.onFailure(Status.NOTICE_NON_EXISTS), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NoticeException.NoticeTitleNonExistException.class)
    public ResponseEntity<ApiResponse<?>> handleNoticeExcption(NoticeException.NoticeTitleNonExistException ex) {
        return new ResponseEntity<>(ApiResponse.onFailure(Status.NOTICE_NON_TITLE), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NoticeException.NoticeContentNonExistException.class)
    public ResponseEntity<ApiResponse<?>> handleNoticeExcption(NoticeException.NoticeContentNonExistException ex) {
        return new ResponseEntity<>(ApiResponse.onFailure(Status.NOTICE_NON_CONTENT), HttpStatus.NOT_FOUND);
    }
}
