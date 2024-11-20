package com.example.devopsplayground;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String test() {
        return "hello";
    }

    @GetMapping("/test")
    public String test2(){
        return "돌아가는중입니다..";
    }
}
