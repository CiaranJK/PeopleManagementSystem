package com.example.people_managment_system.controller

import com.example.people_managment_system.dtos.EmployeeDto
import com.example.people_managment_system.service.SystemService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/system")

class SystemController(val service: SystemService)
    {
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleBadDataInput(e: DataIntegrityViolationException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping("/employees/{employeeId}")
    fun retrieveEmployeeById(@PathVariable employeeId: Int): EmployeeDto {
        return service.retrieveEmployeeById(employeeId)
    }
    @GetMapping("/{companyName}/employees")
    fun retrieveEmployeesByCompanyName(@PathVariable companyName: String): MutableList<EmployeeDto> {
        return service.retrieveEmployeesInACompany(companyName)
    }
    @GetMapping("/average-salary/{companyName}")
    fun findTheAverageSalaryInACompany(@PathVariable companyName: String): String {
        return service.findTheAverageSalaryInACompany(companyName)
    }
    @PostMapping("{companyName}/add-employee")
    @ResponseStatus(HttpStatus.CREATED)
    fun addEmployee(@PathVariable companyName: String, @RequestBody newEmployeeDto: EmployeeDto): String {
        return service.saveNewEmployee(companyName, newEmployeeDto)
    }
    @PatchMapping("/{companyName}/update-team/{employeeId}")
    fun updateTeam(@PathVariable companyName: String, @PathVariable employeeId: Int, @RequestBody teamId: Int): EmployeeDto? {
        return service.updateTeam(companyName, employeeId, teamId)
    }
    @PatchMapping("/{companyName}/update-manager/{employeeId}")
    fun updateManager(@PathVariable companyName: String, @PathVariable employeeId: Int, @RequestBody managerId: Int): EmployeeDto? {
        return service.updateManager(companyName, employeeId, managerId)
    }
    @DeleteMapping("/delete-employee/{employeeId}")
    fun deleteEmployee(@PathVariable employeeId: Int): String? {
        return service.deleteEmployee(employeeId)
    }
}