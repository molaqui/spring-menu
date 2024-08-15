package com.example.demo.Service;



import com.example.demo.DAO.AboutRepository;
import com.example.demo.DAO.UserRepository;
import com.example.demo.Entity.About;
import com.example.demo.Entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AboutService {

    @Autowired
    private AboutRepository aboutRepository;

    public Optional<About> getAboutByUserId(Long userId) {
        return aboutRepository.findByUserId(userId);
    }

    public About saveAbout(About about) {
        return aboutRepository.save(about);
    }

    public void deleteImage(Long aboutId, String imageKey) {
        Optional<About> optionalAbout = aboutRepository.findById(aboutId);
        if (optionalAbout.isPresent()) {
            About about = optionalAbout.get();
            switch (imageKey) {
                case "image1":
                    about.setImage1(null);
                    break;
                case "image2":
                    about.setImage2(null);
                    break;
                case "image3":
                    about.setImage3(null);
                    break;
                case "image4":
                    about.setImage4(null);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid image key: " + imageKey);
            }
            aboutRepository.save(about);
        } else {
            throw new RuntimeException("About not found with id " + aboutId);
        }
    }


    public void deleteAbout(Long id) {
        aboutRepository.deleteById(id);
    }
}