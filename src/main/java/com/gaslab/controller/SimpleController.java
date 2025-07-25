package com.gaslab.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {
    
    // @GetMapping("/")
    // public String home() {
    //     return "forward:/index.html";
    // }
    
    @GetMapping("/test")
    public String test() {
        return "앱이 정상 동작중입니다! ✅";
    }
    
    @GetMapping("/debug")
    public String debug() {
        return """
            <h2>디버그 정보</h2>
            <ul>
                <li><a href="/api/situations">상황 목록 (현재 결과)</a></li>
                <li>DB 연결: 성공 (빈 배열이 나오니까 연결됨)</li>
                <li>문제: JPA가 데이터를 못 찾음</li>
            </ul>
            <p>Oracle에서 테이블명과 데이터를 확인해주세요!</p>
            """;
    }
}