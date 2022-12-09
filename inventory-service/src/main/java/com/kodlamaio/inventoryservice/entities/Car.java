package com.kodlamaio.inventoryservice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
public class Car {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "daily_price")
    private double dailyPrice;
    @Column(name = "model_year")
    private int modelYear;
    @Column(name = "plate")
    private String plate;
    @Column(name = "state")
    private int state=1;
    @ManyToOne()
    @JoinColumn(name = "model_id")
    private Model model;
}
