package com.example.onlineLearning.learningPath.controller;

import com.example.onlineLearning.learningPath.model.LearningPath;
import com.example.onlineLearning.learningPath.service.LearningPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/learning")
public class LearningPathController {

    @Autowired
    private LearningPathService learningPathService;

    @PostMapping("/create")
    private ResponseEntity<LearningPath> save(@RequestBody LearningPath learningPath){
        LearningPath temporal = learningPathService.create(learningPath);
        try{
            return ResponseEntity.created(new URI("/"+temporal.getId())).body(temporal);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/paths")
    private ResponseEntity<List<LearningPath>> AllLearningPath (){
        return ResponseEntity.ok(learningPathService.getAllLearningPath());
    }

    @GetMapping (path="path/{pathId}")
    public LearningPath getLearningPath(@PathVariable("pathId")Long id) {
        return learningPathService.getOnlyLearningPath(id);
    }

    //Metodo put para un usuario
    @PutMapping(path="path/{pathId}")
    public LearningPath updateLearning(@PathVariable("pathId") Long id, @RequestParam(required = false) String typePath) {
        return learningPathService.updateLearningPath(id,typePath);
    }

    @DeleteMapping (path="learning/{learningId}")
    public LearningPath deleteOnlyLearningPath(@PathVariable("learningId") Long id) {
        return learningPathService.deleteLearningPath(id);
    }





}
