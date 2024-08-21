package com.example.demo.Service;

import com.example.demo.Entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendCounts(Long userId, Long count) {
        messagingTemplate.convertAndSend("/topic/reservations/" + userId, count);
    }

    public void sendReservationUpdate(Long userId, Reservation reservation) {
        messagingTemplate.convertAndSend("/topic/reservations/" + userId, reservation);
    }
}
