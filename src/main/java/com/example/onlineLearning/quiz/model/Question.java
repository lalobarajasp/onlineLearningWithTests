package com.example.onlineLearning.quiz.model;
import java.util.*;
public class Question {
    private String text;
    private List<String> options;
    private int answer;

    public Question() {
    }

    public Question(String text, List<String> options, int answer) {
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }




}
