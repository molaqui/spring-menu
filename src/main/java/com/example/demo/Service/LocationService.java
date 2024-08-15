package com.example.demo.Service;

import com.example.demo.DAO.LocationRepository;
import com.example.demo.DAO.UserRepository;
import com.example.demo.DTO.LocationDto;
import com.example.demo.Entity.Location;
import com.example.demo.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<Location> getLocationByUserId(Long userId) {
        return locationRepository.findByUserId(userId);
    }

    public Location saveLocation(LocationDto locationDto) {
        User user = userRepository.findById(locationDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Delete existing location if present
        locationRepository.findByUserId(user.getId()).ifPresent(existingLocation -> {
            locationRepository.delete(existingLocation);
        });

        Location location = new Location();
        location.setUser(user);
        location.setMapLink(locationDto.getMapLink());
        return locationRepository.save(location);
    }

    public void deleteLocation(Long locationId) {
        locationRepository.deleteById(locationId);
    }
}
