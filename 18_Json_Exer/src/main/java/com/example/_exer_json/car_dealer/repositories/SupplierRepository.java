package com.example._exer_json.car_dealer.repositories;

import com.example._exer_json.car_dealer.models.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
