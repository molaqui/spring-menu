package com.example.demo.Service;

import com.example.demo.DAO.ContactRepository;
import com.example.demo.DAO.UserRepository;
import com.example.demo.Entity.Contact;
import com.example.demo.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    public Contact saveContact(Contact contact, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        contact.setUser(user);
        return contactRepository.save(contact);
    }

    public Optional<Contact> getContactByUserId(Long userId) {
        return contactRepository.findByUserId(userId);
    }
}
