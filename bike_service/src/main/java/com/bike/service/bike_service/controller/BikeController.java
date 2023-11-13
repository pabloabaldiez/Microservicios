package com.bike.service.bike_service.controller;


import com.bike.service.bike_service.entity.Bike;
import com.bike.service.bike_service.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bike")
public class BikeController {

    @Autowired
    private BikeService bikeService = new BikeService();

    @GetMapping("/bike-list")
    public ResponseEntity<List<Bike>> BikeList() {

        List<Bike> bike = bikeService.getAll();

        if (bike.isEmpty()) {

            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bike);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Bike> findCar(@PathVariable("id") int id) {

        Bike bike = bikeService.getBikeById(id);

        if (bike == null) {

            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bike);
    }

    @PostMapping("/save-bike")
    public ResponseEntity<Bike> saveBike(@RequestBody Bike bike) {

        Bike newBike = bikeService.save(bike);

        return ResponseEntity.ok(newBike);

    }

    @GetMapping("/user/{userId}")

    public ResponseEntity<List<Bike>> listBikeByUserId(@PathVariable("userId") int userId){

        List<Bike> bike = bikeService.byUserId(userId);

        if(bike.isEmpty()){

            return ResponseEntity.noContent().build();
        }

            return  ResponseEntity.ok(bike);
    }
}
