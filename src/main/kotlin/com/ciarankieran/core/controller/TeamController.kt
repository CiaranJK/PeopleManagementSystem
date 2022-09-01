package com.ciarankieran.core.controller

import com.ciarankieran.core.domain.employee.EmployeeDto
import com.ciarankieran.core.domain.team.TeamDto
import com.ciarankieran.core.usecases.team.TeamService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController @RequestMapping("api/teams/")
class TeamController(val service: TeamService) {

    @GetMapping("{id}")
    fun retrieveById(@PathVariable id: Int): TeamDto {
        return service.retrieveById(id)
    }

    /***
     * Takes a Team ID and provides the team's details to the client submitting the request.
     * If no resource exists for the given ID, an exception is thrown that is caught by the ExceptionHandler class.
     ***/

    @PostMapping("add-team")
    @ResponseStatus(HttpStatus.CREATED)
    fun addNew(@RequestBody teamDto: TeamDto): String? {
        return service.addNew(teamDto)
    }

    /***
     * Saves a new Team entity to the database by taking in a TeamDto as a JSON object.
     * If insufficient or incorrect data is supplied, an exception is thrown that is caught by the
     * ExceptionHandler class.
     ***/

    @DeleteMapping("delete/{id}")
    fun delete(@PathVariable id: Int) {
        return service.delete(id)
    }

    /***
     * Deletes a Team entity from the database by taking in an ID as a path variable.
     * If no resource exists at the given ID, an exception is thrown that is handled by the ExceptionHandler class.
     ***/

    @PatchMapping("update-team-for-employee/{companyName}/{employeeId}")
    fun updateTeam(@PathVariable companyName: String,
                   @PathVariable employeeId: Int,
                   @RequestBody teamId: Int): EmployeeDto {
        return service.updateTeamForEmployee(companyName, employeeId, teamId)
    }

    /***
     * Updates the Manager for a Team entity.
     * If no resource exists for any of the given IDs, an exception is thrown that is handled by the
     * ExceptionHandler class.
     ***/

    @GetMapping("{teamId}/employees")
    fun retrieveEmployeesInCompany(@PathVariable teamId: Int): MutableList<EmployeeDto> {
        return service.retrieveEmployeesInTeam(teamId)
    }

    /***
     * Provides the details of all the employees in a Team.
     * If no resource exists at the given ID, an exception is thrown that is caught by the ExceptionHandler class.
     ***/
}

/***
 * The TeamController class communicates with the ManagerService class to retrieve and transfer information
 * to and from the database. It facilitates for, and responds to Team related requests, that are submitted
 * by a client.
 ***/
