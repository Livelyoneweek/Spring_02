package com.sparta.springcore.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String userid;
    private Long post;
    private String contents;

}
