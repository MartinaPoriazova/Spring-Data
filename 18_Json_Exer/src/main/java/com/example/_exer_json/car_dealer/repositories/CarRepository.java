package com.example._exer_json.car_dealer.repositories;

import com.example._exer_json.car_dealer.models.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

}
