package com.example.startoffice.app.controller;

import com.example.startoffice.apiPayload.ApiResponse;
import com.example.startoffice.apiPayload.Status;
import com.example.startoffice.app.dto.NoticeDto;
import com.example.startoffice.app.dto.NoticeDto.NoticeRequestDto;
import com.example.startoffice.app.dto.NoticeDto.NoticeGetDto;
import com.example.startoffice.domain.Notice;
import com.example.startoffice.repository.NoticeRepository;
import com.example.startoffice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;


    @PostMapping
    public ApiResponse<?> createNotice(@RequestBody NoticeRequestDto requestDto) {
        noticeService.addNotice(requestDto);
        return ApiResponse.onSuccess(Status.NOTICE_ADD_SUCCESS, null);
    }


    @GetMapping
    public ApiResponse<?> getNotices() {
        List<NoticeGetDto> notice  = noticeService.getAllNotice();
        return ApiResponse.onSuccess(Status.NOTICE_GET_SUCCESS, notice);
    }
}
