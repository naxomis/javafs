package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {
    @GetMapping("/hello")   // 주소창에 쓸 주소 (같지 않아도 딤)
    public String nicToMeetYou(){
        return "hello";     // 불러올 주소?
    }

    @GetMapping("/hi")
    // 전달한 내용이 있으면 Model model 를 써준다
    public String greetings(Model model){
        model.addAttribute("username", "junsuk"); // 전달할 내용
        return "greetings";
    }

    @GetMapping("/bye")
    // 전달한 내용이 있으면 Model model 를 써준다
    public String seeYouNext(Model model){
        model.addAttribute("nickname", "홍길동"); // 전달할 내용
        return "goodbye";
    }

}
