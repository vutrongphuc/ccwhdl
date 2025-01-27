package com.hpt.iuh.repository;

import com.hpt.iuh.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface DepartmentRepository extends JpaRepository<Department, String> {

}
