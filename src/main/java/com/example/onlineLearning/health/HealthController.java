package com.example.onlineLearning.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/health")
public class HealthController {
    @GetMapping(path = "health/app")
    public String healthCheck(){
        return "UP";
    }

}
