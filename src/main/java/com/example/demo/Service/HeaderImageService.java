package com.example.demo.Service;


import com.example.demo.DAO.HeaderImageRepository;
import com.example.demo.Entity.HeaderImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeaderImageService {

    @Autowired
    private HeaderImageRepository headerImageRepository;

    public List<HeaderImage> getHeaderImagesByUserId(Long userId) {
        return headerImageRepository.findByUserId(userId);
    }

    public HeaderImage saveHeaderImage(HeaderImage headerImage) {
        return headerImageRepository.save(headerImage);
    }
}
