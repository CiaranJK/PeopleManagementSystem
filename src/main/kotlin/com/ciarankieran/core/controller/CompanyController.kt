package com.ciarankieran.core.controller

import com.ciarankieran.core.domain.company.CompanyDto
import com.ciarankieran.core.domain.employee.EmployeeDto
import com.ciarankieran.core.usecases.company.CompanyService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController @RequestMapping("api/companies/")
class CompanyController(val service: CompanyService) {

    @GetMapping("{id}")
    fun retrieveById(@PathVariable id: Int): CompanyDto {
        return service.retrieveById(id)
    }

    /***
     * Takes a Company ID and provides the company's details to the client submitting the request.
     * If no resource exists for the given ID, an exception is thrown that is handled by the ExceptionHandler class.
     ***/

    @PostMapping("add-company")
    @ResponseStatus(HttpStatus.CREATED)
    fun addNew(@RequestBody companyDto: CompanyDto): String? {
        return service.addNew(companyDto)
    }

    /***
     * Saves a new Company entity to the database by taking in a CompanyDto as a JSON object.
     * If insufficient or incorrect data is supplied, an exception is thrown that is handled by the
     * ExceptionHandler class.
     ***/

    @DeleteMapping("delete/{id}")
    fun delete(@PathVariable id: Int) {
        return service.delete(id)
    }

    /***
     * Deletes an Employee entity from the database by taking in an ID as a path variable.
     * If no resource exists at the given ID, an exception is thrown that is handled by the ExceptionHandler class.
     ***/

    @GetMapping("{companyName}/employees")
    fun retrieveEmployeesInCompany(@PathVariable companyName: String): MutableList<EmployeeDto> {
        return service.retrieveEmployeesInCompany(companyName)
    }

    /***
     * Provides the details of all employees in a Company.
     * If no resource exists at the given ID, an exception is thrown that is handled by the ExceptionHandler class.
     ***/

    @GetMapping("average-salary/{companyName}")
    fun findTheAverageSalaryInCompany(@PathVariable companyName: String): String {
        return service.findTheAverageSalaryInCompany(companyName)
    }

    /***
     * Provides the average salary of employees in a Company.
     * If no resource exists at the given ID, an exception is thrown that is handled by the ExceptionHandler class.
     ***/
}

/***
 * The CompanyController class communicates with the CompanyService class to retrieve and transfer information
 * to and from the database. It facilitates for, and responds to Company related requests, that are submitted
 * by a client.
 ***/
