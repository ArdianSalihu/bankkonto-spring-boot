package com.ardian.bankkonto_spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/hallo")
    public String sagHallo() {
        return "Hallo, Steve";
    }
}
