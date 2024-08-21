package com.example.demo.Controller;

import com.example.demo.Entity.Reservation;
import com.example.demo.Service.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/book")
    public ResponseEntity<Reservation> bookTable(@RequestBody Reservation reservation, @RequestParam Long userId) {
        Reservation savedReservation = reservationService.saveReservation(reservation, userId);
        long count = reservationService.countReservationsByUserId(userId);

        return ResponseEntity.ok(savedReservation);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Reservation>> getAllReservations(@PathVariable Long userId) {
        List<Reservation> reservations = reservationService.getAllReservations(userId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}/{userId}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id, @PathVariable Long userId) {
        Reservation reservation = reservationService.getReservationById(id, userId);
        return ResponseEntity.ok(reservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservationDetails, @RequestParam Long userId) {
        Reservation updatedReservation = reservationService.updateReservation(id, reservationDetails, userId);
        long count = reservationService.countReservationsByUserId(userId);

        return ResponseEntity.ok(updatedReservation);
    }

    @DeleteMapping("/{id}/{userId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id, @PathVariable Long userId) {
        reservationService.deleteReservation(id, userId);
        long count = reservationService.countReservationsByUserId(userId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<Long> countReservationsByUserId(@PathVariable Long userId) {
        long count = reservationService.countReservationsByUserId(userId);
        return ResponseEntity.ok(count);
    }
}