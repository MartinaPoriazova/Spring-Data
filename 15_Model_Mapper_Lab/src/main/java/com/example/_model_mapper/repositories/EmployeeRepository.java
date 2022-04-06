package com.example._model_mapper.repositories;

import com.example._model_mapper.entities.Employee;
import com.example._model_mapper.entities.dto.EmployeeSpringDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<EmployeeSpringDTO> findByBirthdayBeforeOrderBySalaryDesc(LocalDate beforeYear);
}