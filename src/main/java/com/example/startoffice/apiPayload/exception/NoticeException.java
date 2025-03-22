package com.example.startoffice.apiPayload.exception;

public class NoticeException extends RuntimeException{
    public NoticeException(String message){super(message);}
    public static class NoticeNonExistException extends NoticeException{
        public NoticeNonExistException(String message){super(message);}

    }

    public static class NoticeTitleNonExistException extends NoticeException{
        public NoticeTitleNonExistException(String message){super(message);}

    }
    public static class NoticeContentNonExistException extends NoticeException{
        public NoticeContentNonExistException(String message){super(message);}

    }
}
