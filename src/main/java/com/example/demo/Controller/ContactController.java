package com.example.demo.Controller;

import com.example.demo.Entity.Contact;
import com.example.demo.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<Contact> saveContact(@RequestBody Contact contact, @RequestParam Long userId) {
        Contact savedContact = contactService.saveContact(contact, userId);
        return ResponseEntity.ok(savedContact);
    }
}
