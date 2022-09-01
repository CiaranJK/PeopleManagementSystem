package com.ciarankieran.core.usecases.manager

import com.ciarankieran.core.domain.company.CompanyRepository
import com.ciarankieran.core.domain.employee.EmployeeImpl
import com.ciarankieran.core.domain.employee.EmployeeRepository
import com.ciarankieran.core.domain.manager.Manager
import com.ciarankieran.core.domain.manager.ManagerDto
import com.ciarankieran.core.domain.manager.ManagerImpl
import com.ciarankieran.core.domain.manager.ManagerRepository
import com.ciarankieran.core.domain.team.Team
import com.ciarankieran.core.domain.team.TeamDto
import com.ciarankieran.core.domain.team.TeamImpl
import com.ciarankieran.core.domain.team.TeamRepository
import com.ciarankieran.core.config.LoadResourceAsString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ManagerService (
    @Autowired
    val employeeRepository: EmployeeRepository,
    val managerRepository: ManagerRepository,
    val companyRepository: CompanyRepository,
    val teamRepository: TeamRepository,
    val stringResources: LoadResourceAsString
    ) : EmployeeImpl, ManagerImpl, TeamImpl {

    fun retrieveById(id: Int): ManagerDto {
        var managerFound: Manager? = null
        for (item in managerRepository.findAll()) {
            if (item.id == id) {
                managerFound = item
            }
        }
        return managerFound?.let { mapManagerEntityToDto(it) }
            ?: throw NoSuchElementException()
    }

    /***
     * Retrieves a manager's details by their ID or throws an exception if the ID doesn't exist.
     ***/


    fun addNew(managerDto: ManagerDto): String? {
        val newManager = Manager()
        newManager.id = managerDto.id
        newManager.name = managerDto.name
        newManager.company = companyRepository.getCompanyByName(managerDto.companyName)
        newManager.department = managerDto.department
        managerRepository.save(newManager)
        return stringResources.successfulInputResponse
    }

    /***
     * Saves a manager entity to the database. If insufficient data is supplied, the exception is caught by the
     * ExceptionHandler class in the ControllerClasses package.
     ***/

    fun delete(id: Int) {
        var relevantManager: Manager? = null
        for (item in managerRepository.findAll()) {
            if (item.id == id) {
                relevantManager = item
            }
        }
        relevantManager?.let { managerRepository.delete(it) }
            ?: throw NoSuchElementException()
    }

    /***
     * Deletes a Manager entity from the database or throws an exception if the ID doesn't exist.
     ***/

    fun retrieveManagerForAnEmployee(employeeId: Int): ManagerDto {
        var teamId: String? = null
        var managerFound: Manager? = null
        for (item in employeeRepository.findAll()) {
            if (item.id == employeeId) {
                teamId = item.team.toString()
            }
            for (team in teamRepository.findAll()) {
                if (team.id == teamId?.toInt()) {
                    val managerId = team.manager?.id
                    managerFound = managerId?.let { managerRepository.retrieveById(it) }
                }
            }
        }
        return managerFound?.let { mapManagerEntityToDto(it) }
            ?: throw NoSuchElementException()
    }

    /***
     *  Takes in an Employee ID and returns the details for the employee's manager by querying
     *  the team repository and the manager repository.
     *  Throws an exception if the request fails because incorrect data is supplied.
     ***/

    fun updateManagerForTeam(companyName: String, teamId: Int, managerId: Int): TeamDto {
        var relevantTeam: Team? = null
        for (item in teamRepository.findAll()) {
            if (item.id == teamId &&
                item.company?.name == companyName) {
                relevantTeam = item
                relevantTeam.manager = managerRepository.retrieveById(managerId)
                teamRepository.save(relevantTeam)
            }
        }
        return relevantTeam?.let { mapTeamEntityToDto(it) }
            ?: throw NoSuchElementException()
    }

    /***
     *  Changes a manager's team.
     *  Throws an exception if the request fails because incorrect data is supplied.
     ***/
}

/***
 * The EmployeeService class contains the business logic pertaining to Manager Entities.  Through this class,
 * the API communicates directly with the Postgres database in order to respond to client requests
 * transferred to it by the ManagerController class.
 ***/
