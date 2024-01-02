package com.user.service.user_service.service;

import com.user.service.user_service.entity.User;
import com.user.service.user_service.feignclient.BikeFeignClient;
import com.user.service.user_service.feignclient.CarFeignClient;
import com.user.service.user_service.models.BikeModel;
import com.user.service.user_service.models.CarModel;
import com.user.service.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {

        User newUser = userRepository.save(user);

        return newUser;

    }

    //CONNECT TO MICROSERVICES WITH REST TEMPLATE

    @Autowired
    private RestTemplate restTemplate;

    public List<CarModel> getCar(int userId) {
        List<CarModel> car = restTemplate.getForObject("http://car-service/car/user/" + userId, List.class);

        return car;
    }

    public List<BikeModel> getBike(int userId) {
        List<BikeModel> bike = restTemplate.getForObject("http://bike-service/bike/user/" + userId, List.class);

        return bike;
    }

    //CONNECT TO MICROSERVICES WITH FEIGN CLIENT

    @Autowired
    private CarFeignClient carFeignClient;

    public CarModel saveCar(int userId, CarModel car) {

        car.setUserId(userId);
        CarModel newCar = carFeignClient.save(car);

        return newCar;
    }

    @Autowired
    private BikeFeignClient bikeFeignClient;

    public BikeModel saveBike(int userId, BikeModel bike) {

        bike.setUserId(userId);
        BikeModel newBike = bikeFeignClient.save(bike);

        return newBike;
    }

    public Map<String, Object> getUserAndVehicle(int userId){

        Map<String, Object> result= new HashMap<>();
        User user=userRepository.findById(userId).orElse(null);

        if(user == null){
            result.put("Message", "Username does not exist");
            return  result;
        }
        result.put("User", user);

        //Comprueba Cars
        List<CarModel> car = carFeignClient.getCar(userId);

        if(car.isEmpty()){

            result.put("Message", "The user does not have a car");
        }

        result.put("Car", car);

        //Comprueba Bikes
        List<BikeModel> bike = bikeFeignClient.getBike(userId);

        if(bike.isEmpty()){

            result.put("Message", "The user does not have a bike");
        }

        result.put("Bike", bike);

        return result;

    }


}
