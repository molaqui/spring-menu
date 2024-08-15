package com.example.demo.Controller;


import com.example.demo.Entity.Subscriber;
import com.example.demo.Service.SubscriberService;
import com.example.demo.Service.UserService;
import com.example.demo.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/subscribers")
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Subscriber> saveSubscriber(@RequestParam String email, @RequestParam Long userId) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            Subscriber subscriber = new Subscriber();
            subscriber.setEmail(email);
            subscriber.setUser(user.get());
            Subscriber savedSubscriber = subscriberService.saveSubscriber(subscriber);
            return ResponseEntity.ok(savedSubscriber);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Subscriber>> getAllSubscribers() {
        List<Subscriber> subscribers = subscriberService.getAllSubscribers();
        return ResponseEntity.ok(subscribers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscriber(@PathVariable Long id) {
        subscriberService.deleteSubscriber(id);
        return ResponseEntity.noContent().build();
    }
}
