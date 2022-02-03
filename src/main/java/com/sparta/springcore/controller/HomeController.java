package com.sparta.springcore.controller;

import com.sparta.springcore.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 홈페이지 이동 API
    @GetMapping ("/")
    public String moveIndex (Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails ==null) {
            return "index";
        }else{
            model.addAttribute("username", userDetails.getUsername());
            return "index";
        }
    }

    // 상세페이지 이동 API
    @GetMapping ("/move/details")
    public String moveUserDetails (Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails ==null) {
            return "index2";
        } else{
            model.addAttribute("username", userDetails.getUsername());
            return "index2";
        }
    }



}


