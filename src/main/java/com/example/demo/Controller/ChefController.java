package com.example.demo.Controller;

import com.example.demo.Entity.Chef;
import com.example.demo.Entity.User;
import com.example.demo.Service.ChefService;
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
@RequestMapping("/api/chefs")
@CrossOrigin("*")
public class ChefController {

    @Autowired
    private ChefService chefService;

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Chef>> getChefsByUserId(@PathVariable Long userId) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            List<Chef> chefs = chefService.getChefsByUser(user.get());
            return ResponseEntity.ok(chefs);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping
    public ResponseEntity<List<Chef>> getAllChefs(@RequestParam Long userId) {
        List<Chef> chefs = chefService.getChefsByUserId(userId);
        return ResponseEntity.ok(chefs);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Chef> addChef(
            @PathVariable Long userId,
            @RequestParam("name") String name,
            @RequestParam("designation") String designation,
            @RequestParam("image") MultipartFile image,
            @RequestParam("facebookUrl") String facebookUrl,
            @RequestParam("instagramUrl") String instagramUrl) {
        try {
            Optional<User> user = userService.findById(userId);
            if (user.isPresent()) {
                Chef chef = new Chef();
                chef.setName(name);
                chef.setDesignation(designation);
                chef.setImage(image.getBytes());
                chef.setFacebookUrl(facebookUrl);
                chef.setInstagramUrl(instagramUrl);
                chef.setUser(user.get());
                Chef newChef = chefService.saveChef(chef);
                return ResponseEntity.ok(newChef);
            }
            return ResponseEntity.status(404).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }


    // Update chef information
    @PutMapping("/{id}")
    public ResponseEntity<Chef> updateChef(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("designation") String designation,
            @RequestParam("image") MultipartFile image,
            @RequestParam("facebookUrl") String facebookUrl,
            @RequestParam("instagramUrl") String instagramUrl) {

        try {
            Chef updatedChef = chefService.updateChef(id, name, designation, image, facebookUrl, instagramUrl);
            return ResponseEntity.ok(updatedChef);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Delete chef
    @DeleteMapping("/{chefId}")
    public ResponseEntity<Void> deleteChef(@PathVariable Long chefId) {
        if (chefService.deleteChef(chefId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
