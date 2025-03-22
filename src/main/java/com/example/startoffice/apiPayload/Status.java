package com.example.startoffice.apiPayload;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public enum Status {

    NOTICE_ADD_SUCCESS("200", "SUCCESS", "공지사항 추가를 성공했습니다."),
    NOTICE_GET_SUCCESS("200", "SUCCESS", "공지사항 가져오기를 성공했습니다."),
    NOTICE_NON_EXISTS("404", "FAILURE","공지사항이 없습니다."),
    NOTICE_NON_TITLE("404", "FAILURE","제목이 없습니다."),
    NOTICE_NON_CONTENT("404", "FAILURE","내용이 없습니다.");
    private final String code;
    private final String result;
    private final String message;
}
