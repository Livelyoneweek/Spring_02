package com.sparta.springcore.controller;

import com.sparta.springcore.dto.MemoRequestDto;
import com.sparta.springcore.model.Memo;
import com.sparta.springcore.model.UserRoleEnum;
import com.sparta.springcore.repository.MemoRepository;
import com.sparta.springcore.security.UserDetailsImpl;
import com.sparta.springcore.service.MemoService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class MemoController {

    private final MemoRepository memoRepository;
    private final MemoService memoService;

    // 게시글 생성하는 API
    @PostMapping("/api/memosUp/post")
    public Memo createMemo(@RequestBody MemoRequestDto requestDto) {
        Memo memo = new Memo(requestDto);
        return memoRepository.save(memo);
    }

    // 게시글 불러오는 API
    @GetMapping("/api/memos")
    public List<Memo> getMemos() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        return memoRepository.findAllByModifiedAtBetweenOrderByModifiedAtDesc(yesterday, now);
    }

    // 상세 게시글 넘어갈때 불러오는 API
    @GetMapping("/api/memos/{id}")
    public Optional<Memo> getDetails(@PathVariable Long id) {
        return memoRepository.findById(id);
    }

    // 게시글 업데이트 하는 API
//    @PutMapping("/api/memosUp/{id}")
//    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
//        memoService.update(id,requestDto);
//        return id;
//    }

    //게시글 삭제하는 API
    @DeleteMapping("/api/memosUp/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        memoRepository.deleteById(id);
        return id;
    }


}
