package com.kodlamaio.invoiceservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="invoices")
public class Invoice {
    @Id
    @Column(name="id")
    private String id;
    private String fullName;
    private String brandName;
    private String modelName;
    private double dailyPrice;
    private int rentedForDays;
    private double totalPrice;
}
