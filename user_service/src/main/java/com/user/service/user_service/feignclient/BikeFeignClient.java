package com.user.service.user_service.feignclient;

import com.user.service.user_service.models.BikeModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="bike-service", url="http://localhost:8003")
@RequestMapping("/bike")
public interface BikeFeignClient {

    @PostMapping("/save-bike")
    public BikeModel save(@RequestBody BikeModel bike);

    @GetMapping("/user/{userId}")
    public List<BikeModel> getBike(@PathVariable("userId") int userioId);

}