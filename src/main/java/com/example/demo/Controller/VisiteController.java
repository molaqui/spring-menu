package com.example.demo.Controller;

import com.example.demo.Service.VisiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visite")
public class VisiteController {

    @Autowired
    private VisiteService visiteService;

    @GetMapping("/{storeName}/increment")
    public void incrementVisite(@PathVariable String storeName) {
        visiteService.incrementVisite(storeName);
    }

    @GetMapping("/{storeName}/count")
    public int getVisitCount(@PathVariable String storeName) {
        return visiteService.getVisitCount(storeName);
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Integer> getVisitCountByUserId(@PathVariable Long userId) {
        try {
            int count = visiteService.getVisitCountByUserId(userId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
