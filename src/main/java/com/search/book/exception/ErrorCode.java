package com.search.book.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    INVALID_INPUT_VALUE(400, "C001", "유효하지 않는 값 입니다."),
    EMAIL_DUPLICATION(400, "M001", "이미 가입된 이메일 입니다."),
    USER_NOT_EXIST(400, "M002", "존재하지 않는 사용자 정보 입니다.");

    private int status;
    private final String code;
    private final String message;

}
