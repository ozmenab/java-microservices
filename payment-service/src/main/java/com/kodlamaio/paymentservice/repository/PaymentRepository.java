package com.kodlamaio.paymentservice.repository;

import com.kodlamaio.paymentservice.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,String> {
    boolean existsByCardNumberAndFullNameAndCardCvv(
            String cardNumber, String fullName, String cardCvv);
    Payment findByCardNumber(String cardNumber);
}
