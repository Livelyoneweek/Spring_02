package com.sparta.springcore.controller;

import com.sparta.springcore.dto.CommentRequestDto;
import com.sparta.springcore.dto.CommentUpdateDto;
import com.sparta.springcore.model.Comment;
import com.sparta.springcore.repository.CommentRepository;
import com.sparta.springcore.service.CommentService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;

    // 댓글 작성 하는 API
    @PostMapping("/api/commentsUp")
    public Comment createComment(@RequestBody CommentRequestDto requestDto) {
        Comment comment = new Comment(requestDto);
        return commentRepository.save(comment);
    }

    // 댓글 불러오는 API
    @GetMapping("/api/comments/{post}")
    public List<Comment> getCommnets(@PathVariable Long post) {
        return commentRepository.findAllByPostOrderByCreatedAtDesc(post);
    }

    //  댓글 한개 조회하는 API
//    @GetMapping("/api/comments/commentOne/{id}")
//    public Optional<Comment> getDetails(@PathVariable Long id) {
//        return commentRepository.findById(id);
//    }

    // 댓글 수정하는 API
    @PutMapping("/api/commentsUp/{id}")
    public Long updateComment(@PathVariable Long id, @RequestBody CommentUpdateDto requestDto) {
        commentService.update(id,requestDto);
        return id;
    }

    //댓글 삭제하는  API
    @DeleteMapping("/api/commentsUp/{id}")
    public Long deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
        return id;
    }


}
