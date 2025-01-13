package com.example.userservice.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class ProtectEndpoint {
    @GetMapping
    public ResponseEntity<String> getProtectedMessage() {
        return ResponseEntity.ok("YOU ARE IN");
    }
}
