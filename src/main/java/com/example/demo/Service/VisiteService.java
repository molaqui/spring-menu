package com.example.demo.Service;

import com.example.demo.DAO.UserRepository;
import com.example.demo.DAO.VisiteRepository;
import com.example.demo.Entity.User;
import com.example.demo.Entity.Visite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VisiteService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VisiteRepository visiteRepository;

    @Transactional
    public void incrementVisite(String storeName) {
        User user = userRepository.findByStoreName(storeName).get();

        if (user != null && user.getVisite() != null) {
            Visite visite = user.getVisite();
            visite.setCompteur(visite.getCompteur() + 1);
            visiteRepository.save(visite);
        } else if (user != null) {
            Visite newVisite = new Visite();
            newVisite.setCompteur(1);
            user.setVisite(newVisite);
            visiteRepository.save(newVisite);
            userRepository.save(user);
        }
    }

    public int getVisitCount(String storeName) {
        User user = userRepository.findByStoreName(storeName).get();
        if (user != null && user.getVisite() != null) {
            return user.getVisite().getCompteur();
        }
        return 0;
    }

    public int getVisitCountByUserId(Long userId) {
        // Logique pour obtenir le nombre de visites par userId
        return visiteRepository.findByUserId(userId)
                .map(visite -> visite.getCompteur())
                .orElse(0);
    }
}
