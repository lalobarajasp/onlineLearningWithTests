package com.example.onlineLearning.payment.controller;

import com.example.onlineLearning.payment.model.Payment;
import com.example.onlineLearning.payment.service.PaymentService;
import com.example.onlineLearning.quiz.model.Quiz;
import com.example.onlineLearning.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payments")
    private ResponseEntity<List<Payment>> AllPayments (){
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping (path="payment/{paymentId}")
    public Payment getPayment(@PathVariable("paymentId")Long id) {
        return paymentService.getOnlyPayment(id);
    }


}
