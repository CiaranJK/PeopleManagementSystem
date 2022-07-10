package com.example.people_managment_system.repositories

import com.example.people_managment_system.model.Employee
import org.springframework.data.repository.CrudRepository

interface EmployeeRepository : CrudRepository<Employee, Int>