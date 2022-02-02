package com.sparta.springcore.service;

import com.sparta.springcore.dto.CommentRequestDto;
import com.sparta.springcore.dto.CommentUpdateDto;
import com.sparta.springcore.model.Comment;
import com.sparta.springcore.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    // 댓글 업데이트 함수
    @Transactional
    public Long update(Long id, CommentUpdateDto requestDto) {
        System.out.println("test3");
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        System.out.println("test4");
        comment.update(requestDto);
        System.out.println("test5");
        return comment.getId();
    }
}