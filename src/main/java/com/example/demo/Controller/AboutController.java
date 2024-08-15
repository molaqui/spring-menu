package com.example.demo.Controller;

import com.example.demo.Entity.About;
import com.example.demo.Entity.User;
import com.example.demo.Service.AboutService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/about")
public class AboutController {

    @Autowired
    private AboutService aboutService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<About> getAbout(@RequestParam Long userId) {
        Optional<About> about = aboutService.getAboutByUserId(userId);
        return about.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<About> saveAbout(
            @RequestParam("title") String title,
            @RequestParam("subtitle") String subtitle,
            @RequestParam("description") String description,
            @RequestParam("yearsOfExperience") int yearsOfExperience,
            @RequestParam("numberOfChefs") int numberOfChefs,
            @RequestParam("image1") MultipartFile image1,
            @RequestParam("image2") MultipartFile image2,
            @RequestParam("image3") MultipartFile image3,
            @RequestParam("image4") MultipartFile image4,
            @RequestParam Long userId) {
        try {
            About about = new About();

            about.setDescription(description);
            about.setYearsOfExperience(yearsOfExperience);
            about.setNumberOfChefs(numberOfChefs);
            about.setImage1(image1.getBytes());
            about.setImage2(image2.getBytes());
            about.setImage3(image3.getBytes());
            about.setImage4(image4.getBytes());
            Optional<User> user = userService.findById(userId);
            if (user.isPresent()) {
                about.setUser(user.get());
            } else {
                return ResponseEntity.status(404).body(null);
            }

            About savedAbout = aboutService.saveAbout(about);
            return ResponseEntity.ok(savedAbout);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<About> updateAbout(@PathVariable Long userId,
                                             @RequestParam(value = "image1", required = false) MultipartFile image1,
                                             @RequestParam(value = "image2", required = false) MultipartFile image2,
                                             @RequestParam(value = "image3", required = false) MultipartFile image3,
                                             @RequestParam(value = "image4", required = false) MultipartFile image4,
                                             @RequestParam String title,
                                             @RequestParam String subtitle,
                                             @RequestParam String description,
                                             @RequestParam int yearsOfExperience,
                                             @RequestParam int numberOfChefs) {
        try {
            Optional<About> optionalAbout = aboutService.getAboutByUserId(userId);
            if (optionalAbout.isPresent()) {
                About about = optionalAbout.get();
                if (image1 != null) about.setImage1(image1.getBytes());
                if (image2 != null) about.setImage2(image2.getBytes());
                if (image3 != null) about.setImage3(image3.getBytes());
                if (image4 != null) about.setImage4(image4.getBytes());

                about.setDescription(description);
                about.setYearsOfExperience(yearsOfExperience);
                about.setNumberOfChefs(numberOfChefs);
                aboutService.saveAbout(about);
                return ResponseEntity.ok(about);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{userId}/image/{imageKey}")
    public ResponseEntity<Void> deleteAboutImage(@PathVariable Long userId, @PathVariable String imageKey) {
        Optional<About> optionalAbout = aboutService.getAboutByUserId(userId);
        if (optionalAbout.isPresent()) {
            About about = optionalAbout.get();
            aboutService.deleteImage(about.getId(), imageKey);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteAllAbout(@PathVariable Long userId) {
        Optional<About> optionalAbout = aboutService.getAboutByUserId(userId);
        if (optionalAbout.isPresent()) {
            aboutService.deleteAbout(optionalAbout.get().getId());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
