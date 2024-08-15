package com.example.demo.Controller;

import com.example.demo.DTO.LocationDto;
import com.example.demo.Entity.Location;
import com.example.demo.Entity.User;
import com.example.demo.Service.LocationService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
@CrossOrigin("*")
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/{userId}")
    public ResponseEntity<Location> getLocationByUserId(@PathVariable Long userId) {
        Optional<Location> location = locationService.getLocationByUserId(userId);
        return location.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Location> saveLocation(@RequestBody LocationDto locationDto) {
        if (locationDto.getUserId() == null) {
            return ResponseEntity.badRequest().build(); // Check for user ID
        }
        Location location = locationService.saveLocation(locationDto);
        return ResponseEntity.ok(location);
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long locationId) {
        try {
            locationService.deleteLocation(locationId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
