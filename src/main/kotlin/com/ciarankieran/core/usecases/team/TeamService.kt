package com.ciarankieran.core.usecases.team

import com.ciarankieran.core.domain.company.CompanyRepository
import com.ciarankieran.core.domain.employee.Employee
import com.ciarankieran.core.domain.employee.EmployeeDto
import com.ciarankieran.core.domain.employee.EmployeeImpl
import com.ciarankieran.core.domain.employee.EmployeeRepository
import com.ciarankieran.core.domain.manager.ManagerRepository
import com.ciarankieran.core.domain.team.Team
import com.ciarankieran.core.domain.team.TeamDto
import com.ciarankieran.core.domain.team.TeamImpl
import com.ciarankieran.core.domain.team.TeamRepository
import com.ciarankieran.core.config.LoadResourceAsString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TeamService (
    @Autowired
    val employeeRepository: EmployeeRepository,
    val teamRepository: TeamRepository,
    val companyRepository: CompanyRepository,
    val managerRepository: ManagerRepository,
    val stringResources: LoadResourceAsString
    ): TeamImpl, EmployeeImpl {

    fun retrieveById(id: Int): TeamDto {
        var teamFound: Team? = null
        for (item in teamRepository.findAll()) {
            if (item.id == id) {
                teamFound = item
            }
        }
        return teamFound?.let { mapTeamEntityToDto(it) }
            ?: throw NoSuchElementException()
    }

    /***
     * Retrieves a Team's details by its ID or throws an exception if the ID doesn't exist.
     ***/

    fun addNew(teamDto: TeamDto): String? {
        val newTeam = Team()
        newTeam.id = teamDto.id
        newTeam.company = companyRepository.getCompanyByName(teamDto.companyName)
        newTeam.manager = teamDto.id.let { managerRepository.retrieveById(it) }
        teamRepository.save(newTeam)
        return stringResources.successfulInputResponse
    }

    /***
     * Saves a Team entity to the database.  If insufficient data is supplied, the exception is caught by the
     * ExceptionHandler class in the ControllerClasses package.
     ***/

    fun delete(id: Int) {
        var relevantTeam: Team? = null
        for (item in teamRepository.findAll()) {
            if (item.id == id) {
                relevantTeam = item
            }
        }
        relevantTeam?.let { teamRepository.delete(it) }
            ?: throw NoSuchElementException()
    }

    /***
     * Deletes a Team entity from the database or throws an exception if the ID doesn't exist.
     ***/

    fun updateTeamForEmployee(companyName: String, relevantEmployeeId: Int, relevantTeamId: Int): EmployeeDto {
        var relevantEmployee: Employee? = null
        for (item in employeeRepository.findAll()) {
            if (item.id == relevantEmployeeId &&
                item.company?.name == companyName) {
                relevantEmployee = item
                relevantEmployee.team = teamRepository.retrieveById(relevantTeamId)
                employeeRepository.save(relevantEmployee)
            }
        }
        return (relevantEmployee?.let { mapEmployeeEntityToDto(it) }
            ?: throw NoSuchElementException())
    }

    /***
     *  Changes an Employee's Team.
     *  Throws an exception if the request fails because incorrect data is supplied.
     ***/

    fun retrieveEmployeesInTeam(teamId: Int): MutableList<EmployeeDto> {
        val employeesFound = mutableListOf<EmployeeDto>()
        for (item in employeeRepository.findAll()) {
            if (item.team.toString() == teamId.toString())
                employeesFound.add(mapEmployeeEntityToDto(item))
        }
        return employeesFound
    }

    /***
     *  Retrieves all the employees in a Team by taking in the Team ID and returning a list of the employees.
     *  Returns an empty list if no employees are found in the Team, or if the Team ID is invalid.
     ***/
}

/***
 * The TeamService class contains the business logic pertaining to Team Entities.  Through this class,
 * the API communicates directly with the Postgres database in order to respond to client requests
 * transferred to it by the TeamController class.
 ***/
