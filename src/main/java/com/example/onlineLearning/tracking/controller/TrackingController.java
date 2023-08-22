package com.example.onlineLearning.tracking.controller;

import com.example.onlineLearning.tracking.model.Tracking;
import com.example.onlineLearning.tracking.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/track")
public class TrackingController {
    @Autowired
    private TrackingService trackingService;

    @GetMapping (path="/track/{trackId}")
    public Tracking getTrack(@PathVariable("trackId")Long id) {
        return trackingService.getOnlyTrack(id);
    }
}
