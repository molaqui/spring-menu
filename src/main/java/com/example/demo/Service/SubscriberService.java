package com.example.demo.Service;


import com.example.demo.DAO.SubscriberRepository;
import com.example.demo.Entity.Subscriber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriberService {
    @Autowired
    private SubscriberRepository subscriberRepository;

    public Subscriber saveSubscriber(Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }

    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll();
    }

    public void deleteSubscriber(Long id) {
        subscriberRepository.deleteById(id);
    }
}
