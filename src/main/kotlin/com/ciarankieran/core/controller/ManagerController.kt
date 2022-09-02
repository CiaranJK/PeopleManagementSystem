package com.ciarankieran.core.controller

import com.ciarankieran.core.domain.manager.ManagerDto
import com.ciarankieran.core.domain.team.TeamDto
import com.ciarankieran.core.usecases.manager.ManagerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController @RequestMapping("api/managers/")
class ManagerController(val service: ManagerService) {

    @GetMapping("{id}")
    fun retrieveById(@PathVariable id: Int): ManagerDto {
        return service.retrieveById(id)
    }

    /***
     * Takes a Manager ID and provides the manager's details to the client submitting the request.
     * If no resource exists for the given ID, an exception is thrown that is handled by the ExceptionHandler class.
     ***/

    @PostMapping("add-manager")
    @ResponseStatus(HttpStatus.CREATED)
    fun addNew(@RequestBody managerDto: ManagerDto): String? {
        return service.addNew(managerDto)
    }

    /***
     * Saves a new Manager entity to the database by taking in a ManagerDto as a JSON object and
     * calling the addNew method in the ManagerService class.
     * If insufficient or incorrect data is supplied, an exception is thrown that is handled by the
     * ExceptionHandler class.
     ***/

    @DeleteMapping("delete/{id}")
    fun delete(@PathVariable id: Int) {
        return service.delete(id)
    }

    /***
     * Deletes a Manager entity from the database by taking in an ID as a path variable and calling the delete
     * method in the ManagerService class.
     * If ino resource exists at the given ID, an exception is thrown that is handled by the ExceptionHandler class.
     ***/

    @GetMapping("find-manager-for-employee/{employeeId}")
    fun retrieveManagerForAnEmployee(@PathVariable employeeId: Int): ManagerDto {
        return service.retrieveManagerForAnEmployee(employeeId)
    }

    /***
     * Provides the details for an employee's manager to the client submitting the request.
     * If no resource exists for either of the given IDs, an exception is thrown that is handled by the
     * ExceptionHandler class.
     ***/

    @PatchMapping("update-manager-for-team/{companyName}/{teamId}")
    fun updateTeam(@PathVariable companyName: String,
                   @PathVariable teamId: Int,
                   @RequestBody managerId: Int): TeamDto {
        return service.updateManagerForTeam(companyName, teamId, managerId)
    }

    /***
     * Updates the Manager for a Team entity.
     * If no resource exists for any of the given IDs, an exception is thrown that is handled by the
     * ExceptionHandler class.
     ***/
}

/***
 * The ManagerController class communicates with the ManagerService class to retrieve and transfer information
 * to and from the database. It facilitates for, and responds to Manager related requests, that are submitted
 * by a client.
 ***/
