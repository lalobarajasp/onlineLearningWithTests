package com.example.onlineLearning.learningPath.service;

import com.example.onlineLearning.learningPath.model.LearningPath;
import com.example.onlineLearning.learningPath.repository.LearningPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearningPathService {
    @Autowired
    private LearningPathRepository learningPathRepository;

    //Create an Learning Path
    public LearningPath create(LearningPath learningPath){
        //if user choose Custom there is an option to choose more than one a sequence
        //if user choose just a course, it will be the sequence of the course
        return learningPathRepository.save(learningPath);
    }

    //Retrieve all Learning Paths
    public List<LearningPath> getAllLearningPath(){
        return learningPathRepository.findAll();
    }

    //Retrieve Only an Learning Path
    public LearningPath getOnlyLearningPath(Long id) {
        return learningPathRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("The Learning Path with id: " + id +" doesn't exist.")
        );
    }

    //Update Learning Path
    public LearningPath updateLearningPath(Long id, String typePath) {
        LearningPath tmpUser = null;
        //Show an option to change either custom or just a course

        return tmpUser;
    }

    //Delete a Learning Path
    public LearningPath deleteLearningPath(Long id) {
        LearningPath tmpUser = null;
        if (learningPathRepository.existsById(id)) {
            tmpUser = learningPathRepository.findById(id).get();
            learningPathRepository.deleteById(id);
        }
        return tmpUser;
    }

}
