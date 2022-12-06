package com.kodlamaio.paymentservice.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "card_cvv")
    private String cardCvv;
    @Column(name = "balance")
    private double balance;
}
