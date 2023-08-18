package com.example.onlineLearning.payment.repository;

import com.example.onlineLearning.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

}
