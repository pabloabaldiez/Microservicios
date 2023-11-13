package com.bike.service.bike_service.service;



import com.bike.service.bike_service.repository.BikeRepository;
import com.bike.service.bike_service.entity.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeService {

    @Autowired
    private BikeRepository bikeRepository;

    public List<Bike> getAll() {
        return bikeRepository.findAll();
    }

    public Bike getBikeById(int id) {
        return bikeRepository.findById(id).orElse(null);
    }

    public Bike save(Bike bike) {
        Bike newBike = bikeRepository.save(bike);
        return newBike;
    }

    public List<Bike> byUserId(int userId) {return bikeRepository.findByUserId(userId);
    }
}