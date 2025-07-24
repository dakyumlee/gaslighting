package com.gaslab.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DialogueController {

    @GetMapping("/situations")
    public String[] getSituations() {
        return new String[]{"연애", "가족", "알바", "직장"};
    }

    @GetMapping("/statements")
    public String[] getStatements(@RequestParam String situation) {
        return new String[]{
            "너는 왜 그렇게 예민해?",
            "그냥 내가 하자는 대로 하면 안 돼?",
            "다른 사람들도 다 이렇게 사는데?"
        };
    }

    @PostMapping("/response")
    public String getResponse(@RequestBody String responseText) {
        return "{\"score\": 80, \"feedback\": \"이성적이고 좋은 반박입니다!\", \"type\": \"공감형\"}";
    }
}
