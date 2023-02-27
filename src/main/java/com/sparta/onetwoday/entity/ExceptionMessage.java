package com.sparta.onetwoday.entity;

import lombok.Getter;

@Getter
public enum ExceptionMessage {
    BOARD_HAS_BEEN_DELETED("삭제된 게시물입니다."),
    BOARD_DOES_NOT_EXIEST("게시글이 존재하지 않습니다."),

    COMMENT_HAS_BEEN_DELETED("삭제된 댓글입니다."),
    COMMENT_DOES_NOT_EXIEST("댓글이 존재하지 않습니다."),

    COULD_NOT_FOUND_USER("등록된 사용자가 없습니다."),

    PASSWORDS_DO_NOT_MATCH("비밀번호가 일치하지 않습니다."),

    ALREADY_EXIST_DATA("이미 처리된 요청입니다."),

    ADMIN_PASSWORD_IS_INCORRECT("관리자 암호가 틀려 등록이 불가능합니다."),

    DUPLICATE_USER("중복된 사용자가 존재합니다."),

    DUPLICATE_NICKNAME("중복된 닉네임이 존재합니다."),

    NICKNAME_WITH_SPACES("공백이 포함된 닉네임입니다."),

    COULD_NOT_FOUND_LIKE("좋아요를 취소할 수 없습니다."),

    ILLEGAL_ACCESS_UPDATE_OR_DELETE("작성자만 수정/삭제할 수 있습니다."),

    BUDGET_NOT_WITHIN_VALID_RANGE("유효한 범위 내에 있는 예산이 아닙니다."),

    IMAGE_IS_INVALID("이미지가 잘못 되었습니다.");

    private final String message;
    ExceptionMessage(String message) {
        this.message = message;
    }
}
