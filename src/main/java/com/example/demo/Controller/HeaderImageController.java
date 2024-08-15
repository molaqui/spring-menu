package com.example.demo.Controller;


import com.example.demo.Entity.HeaderImage;
import com.example.demo.Entity.User;
import com.example.demo.Service.HeaderImageService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/header-images")
public class HeaderImageController {

    @Autowired
    private HeaderImageService headerImageService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<HeaderImage>> getHeaderImages(@RequestParam Long userId) {
        List<HeaderImage> headerImages = headerImageService.getHeaderImagesByUserId(userId);
        return ResponseEntity.ok(headerImages);
    }

    @PostMapping
    public ResponseEntity<HeaderImage> addHeaderImage(
            @RequestParam String title,
            @RequestParam String subtitle,
            @RequestParam MultipartFile bgImage,
            @RequestParam Long userId) {
        try {
            Optional<User> user = userService.findById(userId);
            if (user.isPresent()) {
                HeaderImage headerImage = new HeaderImage();
                headerImage.setTitle(title);
                headerImage.setSubtitle(subtitle);
                headerImage.setBgImage(bgImage.getBytes());
                headerImage.setUser(user.get());
                HeaderImage savedImage = headerImageService.saveHeaderImage(headerImage);
                return ResponseEntity.ok(savedImage);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
