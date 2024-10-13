package com.hpt.iuh.controller;

import com.hpt.iuh.model.Department;
import com.hpt.iuh.repository.DepartmentRepository;
import com.hpt.iuh.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // This means that this class is a Controller
@RequestMapping("/api") // This means URL's start with /api (after Application path)
public class DepartmentController {

    public static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data

    private DepartmentRepository departmentRepository; //Service which will do all data retrieval/manipulation work

    // Retrieve All Departments
    @GetMapping("/department")
    public ResponseEntity<List<Department>> listAllDepartments() {
        logger.info("Fetching All Department");
        List<Department> departments = departmentRepository.findAll();
        if (departments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    // Retrieve Single User
    @GetMapping("/department/{id}")
    public ResponseEntity<?> getDepartment(@PathVariable("id") String id) {
        logger.info("Fetching Department with id {}", id);
        Department department = departmentRepository.findById(id).orElse(null);
        if (department == null) {
            logger.error("Department with id {} not found.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    // Restful API to Create an Department
    @PostMapping("/department")
    public ResponseEntity<?> createDepartment(@RequestBody Department department) {
        logger.info("Creating Department : {}", department);
        // Check department is existed
        if (departmentRepository.existsById(department.getDepartment_id())) {
            logger.error("Unable to create. An department with id {} - name {} already exist", department.getDepartment_id(), department.getDepartment_name());
            return new ResponseEntity<>(new CustomErrorType("Unable to create. A User with id "
                    + department.getDepartment_id() + " and name "
                    + department.getDepartment_name() + " already exist."), HttpStatus.CONFLICT);
        }

        departmentRepository.save(department);
        return new ResponseEntity<>(department, HttpStatus.CREATED);
    }

    // Update a User
    @PutMapping("/department/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody Department department) {
        logger.info("Updating User with id {}", id);

        Department currentDepartment = departmentRepository.findById(id).orElse(null);

        if (currentDepartment == null) {
            logger.error("Unable to update. User with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to update. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentDepartment.setDepartment_name(department.getDepartment_name());

        departmentRepository.save(currentDepartment);

        return new ResponseEntity<>(currentDepartment, HttpStatus.OK);
    }

    // Delete a User
    @DeleteMapping("/department/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting User with id {}", id);

        Department department = departmentRepository.findById(id).orElse(null);
        if (department == null) {
            logger.error("Unable to delete. User with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        departmentRepository.deleteById(id);
        return new ResponseEntity<Department>(HttpStatus.NO_CONTENT);
    }
}
