package com.example.demo.DAO;


import com.example.demo.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserId(Long userId);
    Optional<Reservation> findByIdAndUserId(Long id, Long userId);
    long countByUserId(Long userId);}