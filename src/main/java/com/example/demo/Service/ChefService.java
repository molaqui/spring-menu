package com.example.demo.Service;

import com.example.demo.DAO.ChefRepository;
import com.example.demo.DAO.UserRepository;
import com.example.demo.Entity.Chef;
import com.example.demo.Entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Service
public class ChefService {
    @Autowired
    private ChefRepository chefRepository;

    public List<Chef> getChefsByUser(User user) {
        return chefRepository.findByUser(user);
    }
    public List<Chef> getChefsByUserId(Long userId) {
        return chefRepository.findByUserId(userId);
    }

    public Chef saveChef(Chef chef) {
        return chefRepository.save(chef);
    }

    public Chef updateChef(Long chefId, String name, String designation, MultipartFile image, String facebookUrl, String instagramUrl) throws IOException {
        return chefRepository.findById(chefId).map(chef -> {
            chef.setName(name);
            chef.setDesignation(designation);
            if (image != null && !image.isEmpty()) {
                try {
                    chef.setImage(image.getBytes()); // Update the image if a new one is provided
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            chef.setFacebookUrl(facebookUrl);
            chef.setInstagramUrl(instagramUrl);

            return chefRepository.save(chef);
        }).orElseThrow(() -> new RuntimeException("Chef not found"));
    }


    public boolean deleteChef(Long chefId) {
        if (chefRepository.existsById(chefId)) {
            chefRepository.deleteById(chefId);
            return true;
        } else {
            return false;
        }
    }
}
