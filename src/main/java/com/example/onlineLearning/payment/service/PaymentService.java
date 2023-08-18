package com.example.onlineLearning.payment.service;

import com.example.onlineLearning.payment.model.Payment;
import com.example.onlineLearning.payment.repository.PaymentRepository;
import com.example.onlineLearning.quiz.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    //Retrieve all Payments
    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }

    //Retrieve Only a Payment
    public Payment getOnlyPayment(Long id) {
        return paymentRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("The payment with id: " + id + " doesn't exist.")
        );
    }

}
