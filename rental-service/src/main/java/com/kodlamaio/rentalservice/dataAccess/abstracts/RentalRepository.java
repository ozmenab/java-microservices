package com.kodlamaio.rentalservice.dataAccess.abstracts;

import com.kodlamaio.rentalservice.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental,String> {
}
