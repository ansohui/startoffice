package com.example.startoffice.service;

import com.example.startoffice.apiPayload.exception.NoticeException;
import com.example.startoffice.app.dto.NoticeDto;
import com.example.startoffice.app.dto.NoticeDto.NoticeRequestDto;
import com.example.startoffice.domain.Notice;
import com.example.startoffice.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {
    private final NoticeRepository noticeRepository;
    public void addNotice(NoticeRequestDto requestDto){

        if (requestDto.getContent() == null || requestDto.getContent().trim().isEmpty()) {
            throw new NoticeException.NoticeContentNonExistException("내용은 비어 있을 수 없습니다.");
        }
        if (requestDto.getTitle() == null || requestDto.getTitle().trim().isEmpty()) {
            throw new NoticeException.NoticeTitleNonExistException("제목은 비어 있을 수 없습니다.");
        }


        // 새로운 댓글 생성
        Notice notice = new Notice();
        notice.setTitle(requestDto.getTitle());
        notice.setContent(requestDto.getContent());
        notice.setCreatedAt(LocalDateTime.now()); // 현재 시간 설정

        // 댓글 저장
        noticeRepository.save(notice);
    }
    public List<NoticeDto.NoticeGetDto> getAllNotice(){
        List<Notice> notices = noticeRepository.findAllByOrderByCreatedAtDesc();
        if (notices.isEmpty()) {
            throw new NoticeException.NoticeNonExistException("공지사항이 없습니다.");
        }
        return notices.stream()
                .map(notice -> new NoticeDto.NoticeGetDto(
                        notice.getCreatedAt(),
                        notice.getTitle(),
                        notice.getContent()

                ))
                .collect(Collectors.toList());
    }

}
