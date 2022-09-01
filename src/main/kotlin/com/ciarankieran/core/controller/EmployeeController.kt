package com.ciarankieran.core.controller

import com.ciarankieran.core.domain.employee.EmployeeDto
import com.ciarankieran.core.usecases.employee.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController @RequestMapping("api/employees/")
class EmployeeController(val service: EmployeeService) {

    @GetMapping("{id}")
    fun retrieveById(@PathVariable id: Int): EmployeeDto {
        return service.retrieveById(id)
    }

    /***
     * Takes an Employee ID and provides the employee's details to the client submitting the request.
     * If no resource exists for the given ID, an exception is thrown that is caught by the ExceptionHandler class.
     ***/

    @PostMapping("add-new")
    @ResponseStatus(HttpStatus.CREATED)
    fun addNew(@RequestBody employeeDto: EmployeeDto): String? {
        return service.addNew(employeeDto)
    }

    /***
     * Saves a new Employee entity to the database by taking in an EmployeeDto as a JSON object.
     * If insufficient or incorrect data is supplied, an exception is thrown that is caught by the
     * ExceptionHandler class.
     ***/

    @DeleteMapping("delete/{id}")
    fun delete(@PathVariable id: Int) {
        return service.delete(id)
    }

    /***
     * Deletes an Employee entity from the database by taking in an ID as a path variable.
     * If no resource exists for the given ID, an exception is thrown that is handled by the ExceptionHandler class.
     ***/
}

/***
 * The EmployeeController class communicates with the EmployeeService class to retrieve and transfer information
 * to and from the database. It facilitates for, and responds to Employee related requests, that are submitted
 * by a client.
 ***/
