package com.example.demo.Controller;

import com.example.demo.DAO.UserRepository;
import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;



    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOptional = userService.login(email, password);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        // Vérification de l'existence de l'email
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            // Si l'email existe, envoyer la demande de réinitialisation
            boolean result = userService.forgotPassword(email);
            if (result) {
                return ResponseEntity.ok("Password reset email sent successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to send password reset email.");
            }
        } else {
            // Si l'email n'existe pas, renvoyer une réponse 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Email address not found.");
        }
    }


    @GetMapping("/user/store/{storeName}")
    public ResponseEntity<User> getUserByStoreName(@PathVariable String storeName) {
        Optional<User> userOptional = userService.getUserByStoreName(storeName);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}/website-url")
    public ResponseEntity<String> getWebsiteUrl(@PathVariable Long userId) {
        Optional<String> websiteUrl = userService.getWebsiteUrl(userId);
        if (websiteUrl.isPresent()) {
            return ResponseEntity.ok(websiteUrl.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/user/{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long userId,
            @RequestParam Map<String, String> params,
            @RequestParam(value = "logo", required = false) MultipartFile logo) {

        Optional<User> userOptional = userService.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Mettre à jour les propriétés de l'utilisateur en fonction des paramètres fournis
            if (params.containsKey("email")) user.setEmail(params.get("email"));
            if (params.containsKey("password")) user.setPassword(params.get("password"));
            if (params.containsKey("storeName")) user.setStoreName(params.get("storeName"));
            if (params.containsKey("phone")) user.setPhone(params.get("phone"));
            if (params.containsKey("city")) user.setCity(params.get("city"));
            if (params.containsKey("firstName")) user.setFirstName(params.get("firstName"));
            if (params.containsKey("lastName")) user.setLastName(params.get("lastName"));
            user.setWebsiteUrl("https://lmenu.netlify.app/"+params.get("storeName"));
            // Mettre à jour le logo si fourni
            if (logo != null && !logo.isEmpty()) {
                try {
                    user.setLogo(logo.getBytes());
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            }

            // Sauvegarder les modifications
            userService.addUser(user);

            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/user")
    public ResponseEntity<String> addUser(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String storeName,
            @RequestParam String phone,
            @RequestParam String city,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(value = "logo", required = false) MultipartFile logo) throws IOException {


        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setStoreName(storeName);
        user.setPhone(phone);
        user.setCity(city);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setWebsiteUrl("https://lmenu.netlify.app/"+storeName);
        user.setLogo(logo.getBytes());
        userService.addUser(user);
        return ResponseEntity.ok("new User ajoutées avec succès");
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        boolean deleted = userService.deleteUser(userId);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
