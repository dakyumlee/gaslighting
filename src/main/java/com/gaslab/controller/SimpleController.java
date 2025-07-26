package com.gaslab.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {
    
    @GetMapping("/test")
    public String test() {
        return "App is running! ✅";
    }
    
    @GetMapping("/debug")
    public String debug() {
        return """
            <h2>Debug Info</h2>
            <ul>
                <li><a href="/api/situations">Situations</a></li>
                <li>DB: Connected</li>
                <li>Status: OK</li>
            </ul>
            """;
    }
}