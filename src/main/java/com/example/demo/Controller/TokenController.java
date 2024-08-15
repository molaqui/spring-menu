package com.example.demo.Controller;

import com.example.demo.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/tokens")
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateToken(@RequestBody Map<String, Integer> payload) {
        int tableNumber = payload.get("tableNumber");
        String token = tokenService.generateToken(String.valueOf(tableNumber));
        return ResponseEntity.ok(token);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyToken(@RequestParam String token) {
        String tableNumber = tokenService.verifyToken(token);
        if (tableNumber != null) {
            return ResponseEntity.ok(tableNumber);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token invalide");
        }
    }
}


