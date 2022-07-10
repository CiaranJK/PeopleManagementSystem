package com.example.people_managment_system.service

import com.example.people_managment_system.dtos.EmployeeDto
import com.example.people_managment_system.model.Employee
import com.example.people_managment_system.repositories.CompanyRepository
import com.example.people_managment_system.repositories.EmployeeRepository
import com.example.people_managment_system.repositories.ManagerRepository
import com.example.people_managment_system.repositories.TeamRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service

class SystemService(
    @Autowired  val employeeRepository: EmployeeRepository,
                val companyRepository: CompanyRepository,
                val teamRepository: TeamRepository,
                val managerRepository: ManagerRepository
    )
    {

//  Retrieves an employee by their ID or throws an exception if the employeeID doesn't exist. //

    fun retrieveEmployeeById(employeeID: Int): EmployeeDto {
        var employeeFound: Employee? = null
        for (item in employeeRepository.findAll()) {
            if (item.employeeID == employeeID) {
                employeeFound = item
            }
        }
        return employeeFound?.let { mapEmployeeEntityToEmployeeDto(it) }
            ?: throw NoSuchElementException("Could not find an employee with that ID")
    }

/*  Retrieves all the employees in a company by taking in the company name as a path variable and
    returning a list of the employees.  Returns an empty list if no employees are found in the company,
    or if the company name is invalid. */

    fun retrieveEmployeesInACompany(companyName: String): MutableList<EmployeeDto> {
    val employeesFound = mutableListOf<EmployeeDto>()
    for (item in employeeRepository.findAll()) {
        if (item.company.toString() == companyName)
            employeesFound.add(mapEmployeeEntityToEmployeeDto(item))
    }
    return employeesFound
}

/*  Calculates the average value in a company using the company name as a path variable.
    Returns a String response if the company has no employee data or the company name is invalid. */

   fun findTheAverageSalaryInACompany(companyName: String): String {
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

/*  Saves an employee to the company database using an employee data transfer object.
    Takes in the company name as a path variable.
    Returns a String response if insufficient or erroneous data is entered. */

    fun saveNewEmployee(companyName: String ,newEmployeeDto: EmployeeDto): String {
       val newEmployee = Employee()
       newEmployee.employeeID = newEmployeeDto.employeeID
       newEmployee.employeeName = newEmployeeDto.employeeName
       newEmployee.email = newEmployeeDto.email
       newEmployee.salary = newEmployeeDto.salary
       newEmployee.company = companyRepository.getCompanyByName(companyName)
       newEmployee.manager = managerRepository.getManagerById(newEmployeeDto.managerID)
       newEmployee.team = teamRepository.getTeamById(newEmployeeDto.teamID)
       return try {
           employeeRepository.save(newEmployee)
           "The Employee has been successfully saved to PeopleMs"
       } catch (e: DataIntegrityViolationException) {
           throw DataIntegrityViolationException("Could not add the employee to your company. " +
                   "Incorrect or insufficient data supplied.")
       }
    }

/* Updates the Team of an employee.
   Takes in the company name as a path variable to ensure the Team ID relates to the right company.
   Throws an exception if the employee ID does not exist. */

    fun updateTeam(companyName: String, relevantEmployeeId: Int, relevantTeamId: Int): EmployeeDto {
       var relevantEmployee: Employee? = null
       for (item in employeeRepository.findAll()) {
           if (item.employeeID == relevantEmployeeId &&
                   item.company?.companyName == companyName) {
               relevantEmployee = item
               relevantEmployee.team = teamRepository.getTeamById(relevantTeamId)
               employeeRepository.save(relevantEmployee)
           }
       }
       return relevantEmployee?.let { mapEmployeeEntityToEmployeeDto(it) }
           ?: throw NoSuchElementException("Could not find an employee with that ID")
    }

/* Updates the Manager of an employee.
   Takes in the company name as a path variable to ensure the Manager ID relates to the right company.
   Throws an exception if the employee ID does not exist. */

fun updateManager(companyName: String, relevantEmployeeId: Int, relevantManagerId: Int): EmployeeDto {
   var relevantEmployee: Employee? = null
   for (item in employeeRepository.findAll()) {
       if (item.employeeID == relevantEmployeeId &&
           item.company?.companyName == companyName) {
           relevantEmployee = item
           relevantEmployee.manager = managerRepository.getManagerById(relevantManagerId)
           employeeRepository.save(relevantEmployee)
       }
   }
   return relevantEmployee?.let { mapEmployeeEntityToEmployeeDto(it) }
       ?: throw NoSuchElementException("Could not find an employee with that ID")
}

/* Deletes an employee's data.
   Returns a String response if the employee ID does not exist. */

    fun deleteEmployee(relevantEmployeeId: Int): String {
       var response = "Could not find an employee with that ID"
       var relevantEmployee: Employee?
       for (item in employeeRepository.findAll()) {
           if (item.employeeID == relevantEmployeeId) {
               relevantEmployee = item
               employeeRepository.delete(relevantEmployee)
               response = "The employee's record has been deleted from PeopleMS."}
       }
       return response
    }

/* Maps an employee entity to a data transfer object.
    Called by the functions:
           retrieveEmployeeById()
           retrieveEmployeesInACompany
           updateTeam()
           updateManager()
*/
   fun mapEmployeeEntityToEmployeeDto(employeeEntity: Employee): EmployeeDto {
       val employeeDto = EmployeeDto()
       employeeDto.employeeID = employeeEntity.employeeID
       employeeDto.employeeName = employeeEntity.employeeName
       employeeDto.email = employeeEntity.email
       employeeDto.salary = employeeEntity.salary
       employeeDto.companyName = employeeEntity.company.toString()
       employeeDto.managerID = employeeEntity.manager?.managerID
       employeeDto.teamID = employeeEntity.team?.teamID
       return employeeDto
   }

}
















