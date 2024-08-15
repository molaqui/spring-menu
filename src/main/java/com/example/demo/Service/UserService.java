package com.example.demo.Service;

import com.example.demo.Entity.User;
import com.example.demo.DAO.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    public Optional<User> login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            return userOptional;
        }
        return Optional.empty();
    }

    public boolean forgotPassword(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            sendEmail(user.getEmail(), user.getPassword());
            return true;
        } else {
            return false;
        }
    }

    private void sendEmail(String to, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your Password");
        message.setText("Your password is: " + password);
        mailSender.send(message);
    }

    public Optional<User> getUserByStoreName(String storeName) {
        return userRepository.findByStoreName(storeName);
    }

    public Optional<String> getWebsiteUrl(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(User::getWebsiteUrl);
    }

    public Optional<User> findById(Long userId) {

        return  userRepository.findById(userId);
    }


    // Ajouter un nouvel utilisateur
    public User addUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long userId, User updatedUser, MultipartFile newLogo) {
        return userRepository.findById(userId).map(user -> {
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setStoreName(updatedUser.getStoreName());
            user.setPhone(updatedUser.getPhone());
            user.setCity(updatedUser.getCity());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setWebsiteUrl(updatedUser.getWebsiteUrl());

            // Mettre à jour le logo seulement si un nouveau logo est fourni
            if (newLogo != null && !newLogo.isEmpty()) {
                try {
                    user.setLogo(newLogo.getBytes());
                } catch (Exception e) {
                    throw new RuntimeException("Failed to upload logo", e);
                }
            }

            return userRepository.save(user);
        });
    }

    // Supprimer un utilisateur par ID
    public boolean deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    // Obtenir le logo d'un utilisateur
    public Optional<byte[]> getLogo(Long userId) {
        return userRepository.findById(userId).map(User::getLogo);
    }
    public Optional<byte[]> getLogoByStoreName(String storeName) {
        return userRepository.findByStoreName(storeName)
                .map(User::getLogo);
    }



    public Optional<String> getLogoBase64ByStoreName(String storeName) {
        return userRepository.findByStoreName(storeName)
                .map(user -> Base64.getEncoder().encodeToString(user.getLogo()));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
