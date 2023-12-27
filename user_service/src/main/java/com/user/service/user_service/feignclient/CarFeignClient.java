package com.user.service.user_service.feignclient;

import com.user.service.user_service.models.CarModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="car-service")
@RequestMapping("/car")
public interface CarFeignClient {

    @PostMapping("/save-car")
    public CarModel save(@RequestBody CarModel car);

    @GetMapping("/user/{userId}")
    public List<CarModel> getCar(@PathVariable("userId") int userId);

}
