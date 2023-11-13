package com.user.service.user_service.controller;


import com.user.service.user_service.entity.User;
import com.user.service.user_service.models.BikeModel;
import com.user.service.user_service.models.CarModel;
import com.user.service.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService = new UserService();

    @GetMapping("/user-list")
    public ResponseEntity<List<User>> userList() {

        List<User> user = userService.getAll();

        if (user.isEmpty()) {

            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(user);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> findUser(@PathVariable("id") int id) {

        User user = userService.getUserById(id);

        if (user == null) {

            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/save-user")
    public ResponseEntity<User> saveUser(@RequestBody User user) {

        User newUser = userService.save(user);

        return ResponseEntity.ok(newUser);

    }

    @GetMapping("/getcar/{userId}")
    public ResponseEntity<List<CarModel>> ListCar(@PathVariable("userId") int id) {

        User user = userService.getUserById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<CarModel> car = userService.getCar(id);

        return ResponseEntity.ok(car);
    }

    @GetMapping("/getbike/{userId}")
    public ResponseEntity<List<BikeModel>> ListBike(@PathVariable("userId") int id) {

        User user = userService.getUserById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<BikeModel> bike = userService.getBike(id);

        return ResponseEntity.ok(bike);
    }


    @PostMapping("/car/{userId}")
    public ResponseEntity<CarModel> saveCar(@PathVariable("userId") int userId, @RequestBody CarModel car) {

        CarModel newCar = userService.saveCar(userId, car);

        return ResponseEntity.ok(newCar);
    }

    @PostMapping("/bike/{userId}")
    public ResponseEntity<BikeModel> saveBike(@PathVariable("userId") int userId, @RequestBody BikeModel bike) {

        BikeModel newBike = userService.saveBike(userId, bike);

        return ResponseEntity.ok(newBike);
    }

    @GetMapping("/vehicles/{userId}")
    public ResponseEntity<Map<String,Object>> getAllVehicles(@PathVariable("userId") int userId) {

        Map<String,Object> vehicles = userService.getUserAndVehicle(userId);

        return ResponseEntity.ok(vehicles);
    }


}
