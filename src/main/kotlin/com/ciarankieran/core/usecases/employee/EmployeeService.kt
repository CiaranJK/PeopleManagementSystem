package com.ciarankieran.core.usecases.employee

import com.ciarankieran.core.domain.company.CompanyRepository
import com.ciarankieran.core.domain.employee.Employee
import com.ciarankieran.core.domain.employee.EmployeeDto
import com.ciarankieran.core.domain.employee.EmployeeImpl
import com.ciarankieran.core.domain.employee.EmployeeRepository
import com.ciarankieran.core.domain.team.Team
import com.ciarankieran.core.domain.team.TeamRepository
import com.ciarankieran.core.config.LoadResourceAsString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmployeeService(
    @Autowired
    val employeeRepository: EmployeeRepository,
    val companyRepository: CompanyRepository,
    val teamRepository: TeamRepository,
    val stringResources: LoadResourceAsString
    ) : EmployeeImpl {

    fun retrieveById(id: Int): EmployeeDto {
        var employeeFound: Employee? = null
        for (item in employeeRepository.findAll()) {
            if (item.id == id) {
                employeeFound = item
            }
        }
        return employeeFound?.let { mapEmployeeEntityToDto(it) }
            ?: throw NoSuchElementException()
    }

    /***
     * Retrieves an employee's details by their ID or throws an exception if the ID doesn't exist.
     ***/

    fun addNew(employeeDto: EmployeeDto): String? {
        val newEmployee = Employee()
        newEmployee.id = employeeDto.id
        newEmployee.name = employeeDto.name
        newEmployee.email = employeeDto.email
        newEmployee.salary = employeeDto.salary
        newEmployee.company = companyRepository.getCompanyByName(employeeDto.companyName)
        if (employeeDto.teamId != null) {
        newEmployee.team = employeeDto.teamId?.let { teamRepository.retrieveById(it) }
                as Team }
        employeeRepository.save(newEmployee)
        return stringResources.successfulInputResponse
    }

    /***
     * Saves an employee entity to the database.  If insufficient data is supplied, the exception is caught by the
     * ExceptionHandler class in the ControllerClasses package.
     ***/

    fun delete(id: Int) {
        var relevantEmployee: Employee? = null
        for (item in employeeRepository.findAll()) {
            if (item.id == id) {
                relevantEmployee = item
            }
        }
        relevantEmployee?.let { employeeRepository.delete(it) }
            ?: throw NoSuchElementException()
    }

    /***
     * Deletes an employee entity from the database or throws an exception if the ID doesn't exist.
     ***/
}

/***
 * The EmployeeService class contains the business logic pertaining to Employee Entities.
 * Through this class, the API communicates directly with the Postgres database in order to respond
 * to client requests transferred to it by the EmployeeController class.
 ***/