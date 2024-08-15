package com.example.demo.Controller;

import com.example.demo.Entity.Message;
import com.example.demo.Entity.User;
import com.example.demo.Service.MessageService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin("*")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message, @RequestParam Long userId) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isPresent()) {
            message.setUser(userOptional.get());
            Message savedMessage = messageService.saveMessage(message);
            return ResponseEntity.ok(savedMessage);
        } else {
            return ResponseEntity.badRequest().build(); // Return 400 if user not found
        }
    }

    @GetMapping
    public ResponseEntity<List<Message>> getMessagesByUserId(@RequestParam Long userId) {
        List<Message> messages = messageService.getMessagesByUserId(userId);
        return ResponseEntity.ok(messages);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        boolean isDeleted = messageService.deleteMessageById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Méthode pour compter le nombre de messages pour un utilisateur
    @GetMapping("/count")
    public ResponseEntity<Long> countMessagesByUserId(@RequestParam Long userId) {
        long count = messageService.countMessagesByUserId(userId);
        return ResponseEntity.ok(count);
    }
}
