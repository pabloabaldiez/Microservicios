package com.user.service.user_service.controller;


import com.user.service.user_service.entity.User;
import com.user.service.user_service.models.BikeModel;
import com.user.service.user_service.models.CarModel;
import com.user.service.user_service.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @CircuitBreaker(name = "carCB", fallbackMethod = "fallBackGetCar")
    @GetMapping("/getcar/{userId}")
    public ResponseEntity<List<CarModel>> ListCar(@PathVariable("userId") int id) {

        User user = userService.getUserById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<CarModel> car = userService.getCar(id);

        return ResponseEntity.ok(car);
    }

    @CircuitBreaker(name = "bikeCB", fallbackMethod = "fallBackGetBike")
    @GetMapping("/getbike/{userId}")
    public ResponseEntity<List<BikeModel>> ListBike(@PathVariable("userId") int id) {

        User user = userService.getUserById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<BikeModel> bike = userService.getBike(id);

        return ResponseEntity.ok(bike);
    }


    @CircuitBreaker(name = "carCB", fallbackMethod = "fallBackSaveCar")
    @PostMapping("/car/{userId}")
    public ResponseEntity<CarModel> saveCar(@PathVariable("userId") int userId, @RequestBody CarModel car) {

        CarModel newCar = userService.saveCar(userId, car);

        return ResponseEntity.ok(newCar);
    }

    @CircuitBreaker(name = "bikeCB", fallbackMethod = "fallBackSaveBike")
    @PostMapping("/bike/{userId}")
    public ResponseEntity<BikeModel> saveBike(@PathVariable("userId") int userId, @RequestBody BikeModel bike) {

        BikeModel newBike = userService.saveBike(userId, bike);

        return ResponseEntity.ok(newBike);
    }

    @CircuitBreaker(name = "allCB", fallbackMethod = "fallBackGetAll")
    @GetMapping("/vehicles/{userId}")
    public ResponseEntity<Map<String,Object>> getAllVehicles(@PathVariable("userId") int userId) {

        Map<String,Object> vehicles = userService.getUserAndVehicle(userId);

        return ResponseEntity.ok(vehicles);
    }

    private ResponseEntity<List<CarModel>> fallBackGetCar(@PathVariable("usuarioId")int id, RuntimeException exception){

        return new ResponseEntity("The user: " + id + " has the car in the workshop", HttpStatus.OK);
    }

    private ResponseEntity<List<CarModel>> fallBackSaveCar(@PathVariable("usuarioId")int id,@RequestBody CarModel car, RuntimeException exception){

        return new ResponseEntity("The user: " + id + " does not have money", HttpStatus.OK);
    }


    private ResponseEntity<List<BikeModel>> fallBackGetBike(@PathVariable("usuarioId")int id, RuntimeException exception){

        return new ResponseEntity("The user: " + id + " has the bike in the workshop", HttpStatus.OK);
    }

    private ResponseEntity fallBackSaveBike(@PathVariable("usuarioId")int id, @RequestBody BikeModel car, RuntimeException exception){

        return new ResponseEntity("The user: " + id + " does not have money", HttpStatus.OK);
    }

    private ResponseEntity<Map<String,Object>> fallBackGetAll(@PathVariable("usuarioId")int id, RuntimeException exception){

        return new ResponseEntity("The user: " + id + " does not have money", HttpStatus.OK);
    }

}
