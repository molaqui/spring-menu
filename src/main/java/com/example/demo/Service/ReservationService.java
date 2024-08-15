package com.example.demo.Service;

import com.example.demo.DAO.ReservationRepository;
import com.example.demo.Entity.Reservation;
import com.example.demo.Entity.User;
import com.example.demo.DAO.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    public Reservation saveReservation(Reservation reservation, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            reservation.setUser(userOptional.get());
            return reservationRepository.save(reservation);
        }
        throw new RuntimeException("User not found");
    }

    public List<Reservation> getAllReservations(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    public Reservation getReservationById(Long id, Long userId) {
        Optional<Reservation> optionalReservation = reservationRepository.findByIdAndUserId(id, userId);
        return optionalReservation.orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    public Reservation updateReservation(Long id, Reservation reservationDetails, Long userId) {
        Optional<Reservation> optionalReservation = reservationRepository.findByIdAndUserId(id, userId);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setName(reservationDetails.getName());
            reservation.setPhone(reservationDetails.getPhone());
            reservation.setDatetime(reservationDetails.getDatetime());
            reservation.setNumberOfPeople(reservationDetails.getNumberOfPeople());
            reservation.setMessage(reservationDetails.getMessage());
            return reservationRepository.save(reservation);
        } else {
            throw new RuntimeException("Reservation not found");
        }
    }

    public void deleteReservation(Long id, Long userId) {
        Optional<Reservation> optionalReservation = reservationRepository.findByIdAndUserId(id, userId);
        if (optionalReservation.isPresent()) {
            reservationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reservation not found");
        }
    }

    // Nouvelle méthode pour compter les réservations par utilisateur
    public long countReservationsByUserId(Long userId) {
        return reservationRepository.countByUserId(userId);
    }
}
