package com.car.service.car_service.controller;

import com.car.service.car_service.entity.Car;
import com.car.service.car_service.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService = new CarService();

    @GetMapping("/car-list")
    public ResponseEntity<List<Car>> CarList() {

        List<Car> car = carService.getAll();

        if (car.isEmpty()) {

            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(car);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Car> findCar(@PathVariable("id") int id) {

        Car car = carService.getCarById(id);

        if (car == null) {

            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(car);
    }

    @PostMapping("/save-car")
    public ResponseEntity<Car> saveCar(@RequestBody Car car) {

        Car newCar = carService.save(car);

        return ResponseEntity.ok(newCar);

    }

    @GetMapping("/user/{userId}")

    public ResponseEntity<List<Car>> listCarByUserId(@PathVariable("userId") int userId){

        List<Car> car = carService.byUserId(userId);

        if(car.isEmpty()){

            return ResponseEntity.noContent().build();
        }

            return  ResponseEntity.ok(car);
    }
}
