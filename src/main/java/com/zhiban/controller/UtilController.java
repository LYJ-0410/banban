package com.zhiban.controller;

import com.zhiban.common.Result;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/util")
@RequiredArgsConstructor
public class UtilController {
    
    private final PasswordEncoder passwordEncoder;
    
    @PostMapping("/encode-password")
    public Result<String> encodePassword(@RequestParam String password) {
        String encoded = passwordEncoder.encode(password);
        return Result.success(encoded);
    }
    
    @PostMapping("/check-password")
    public Result<Boolean> checkPassword(@RequestParam String rawPassword, @RequestParam String encodedPassword) {
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        return Result.success(matches);
    }
}
