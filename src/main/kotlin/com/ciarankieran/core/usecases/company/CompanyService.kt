package com.ciarankieran.core.usecases.company

import com.ciarankieran.core.domain.company.Company
import com.ciarankieran.core.domain.company.CompanyDto
import com.ciarankieran.core.domain.company.CompanyImpl
import com.ciarankieran.core.domain.company.CompanyRepository
import com.ciarankieran.core.domain.employee.EmployeeDto
import com.ciarankieran.core.domain.employee.EmployeeImpl
import com.ciarankieran.core.domain.employee.EmployeeRepository
import com.ciarankieran.core.config.LoadResourceAsString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CompanyService (
    @Autowired
    val companyRepository: CompanyRepository,
    val employeeRepository: EmployeeRepository,
    val stringResources: LoadResourceAsString
    ): CompanyImpl, EmployeeImpl {

    fun retrieveById(id: Int): CompanyDto {
        var companyFound: Company? = null
        for (item in companyRepository.findAll()) {
            if (item.id == id) {
                companyFound = item
            }
        }
        return companyFound?.let { mapCompanyEntityToDto(it) }
            ?: throw NoSuchElementException()
    }

    /***
     * Retrieves a company's details by their ID or throws an exception if the ID doesn't exist.
     ***/

    fun addNew(companyDto: CompanyDto): String? {
        val newCompany = Company()
        newCompany.id = companyDto.id
        newCompany.name = companyDto.name
        newCompany.website = companyDto.website
        companyRepository.save(newCompany)
        return stringResources.successfulInputResponse
    }

    /***
     * Saves a company entity to the database. If insufficient data is supplied, the exception is caught by the
     * ExceptionHandler class in the ControllerClasses package.
     ***/

    fun delete(id: Int) {
        var relevantCompany: Company? = null
        for (item in companyRepository.findAll()) {
            if (item.id == id) {
               relevantCompany = item
            }
        }
        relevantCompany?.let { companyRepository.delete(it) }
        ?: throw NoSuchElementException()
    }

    /***
     * Deletes a company entity from the database or throws an exception if the ID doesn't exist.
     ***/

     fun findTheAverageSalaryInCompany(companyName: String): String {
        val salaryInts = mutableListOf<Int?>()
        for (item in employeeRepository.findAll()) {
            if (item.company.toString() == companyName) {
                item.let { salaryInts.add(item.salary) }
            }
        }
        var totalAllSalaries = 0
        salaryInts.forEach { if (it != null) { totalAllSalaries += it } }
        val response = if (totalAllSalaries > 0) {
            val averageSalaryInACompany = totalAllSalaries / salaryInts.size
            "The average salary at $companyName is $averageSalaryInACompany"
        } else {
            "Could not find any salary listing at the company name provided" }
        return response
    }

    /***
     *  Calculates the average value of employee's salaries in a company using the company name as a path variable.
     *  Returns a String response if the company has no employee data or the company name is invalid.
     ***/

    fun retrieveEmployeesInCompany(companyName: String): MutableList<EmployeeDto> {
        val employeesFound = mutableListOf<EmployeeDto>()
        for (item in employeeRepository.findAll()) {
            if (item.company.toString() == companyName)
                employeesFound.add(mapEmployeeEntityToDto(item))
        }
        return employeesFound
    }

    /***
     *  Retrieves all the employees in a company by taking in the company name and returning a list
     *  of the employees.
     *  Returns an empty list if no employees are found in the company, or if the company name is invalid.
     ***/
}

/***
 * The CompanyService class contains the business logic pertaining to Company Entities.
 * Through this class, the API communicates directly with the Postgres database in order to respond
 * to client requests transferred to it by the CompanyController class.
 ***/
