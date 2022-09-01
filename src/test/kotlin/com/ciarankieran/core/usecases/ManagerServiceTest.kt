package com.ciarankieran.core.usecases

import com.ciarankieran.core.domain.manager.ManagerDto
import com.ciarankieran.core.domain.manager.ManagerRepository
import com.ciarankieran.core.domain.team.TeamRepository
import com.ciarankieran.core.usecases.manager.ManagerService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ManagerServiceTest @Autowired constructor (
    val service: ManagerService,
    val managerRepository: ManagerRepository,
    val teamRepository: TeamRepository
    ) {

    @Nested
    @DisplayName("retrieveById()")
    inner class RetrieveById {
        @Test
        fun `should retrieve an employee with the given ID`() {
            //when
            val result = service.retrieveById(10)
            //then
            assert(result.name == "Marlen Hickle" && result.department == "Public Relations")
        }
    }

    @Nested
    @DisplayName("addNew()")
    inner class AddNew {
        @Test
        fun `should save an employee to the employee repository`() {
            // given
            val testManagerDto = ManagerDto(
                27,
                "Michelle Ryan",
                "Facebook",
                "Marketing"
            )
            // when
            service.addNew(testManagerDto)
            // then
            assert(managerRepository.retrieveById(27)?.name == "Michelle Ryan")
        }
    }

    @Nested
    @DisplayName("Delete()")
    inner class Delete {
        @Test
        fun `should delete an manager's record from the manager repository`() {
            // given
            val testManagerDto = ManagerDto(
                29,
                "Mock Manager",
                "Amazon",
                "Finance"
            )
            service.addNew(testManagerDto)
            // when
            service.delete(36)
            // then
            assert((managerRepository.retrieveById(36) == null))
        }
    }

    @Nested
    @DisplayName("retrieveManagerForAnEmployee()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class RetrieveManagerForAnEmployee {
        @Test
        fun `should retrieve a manager for an Employee with the given employee ID`() {
            // given
            val employeeId = 3314
            // when
            val result = service.retrieveManagerForAnEmployee(employeeId)
            // then
            assert(result.name == "Neal Wehner")
        }
    }

    @Nested
    @DisplayName("UpdateManagerForTeam()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class UpdateManagerForTeam {
        @Test
        fun `should update a team's manager`() {
            // given
            val companyName = "LinkedIn"
            val teamId = 4012
            val managerId = 10
            // when
            service.updateManagerForTeam(companyName, teamId, managerId)
            // then
            assert(teamRepository.retrieveById(4012)?.manager?.id == managerId)
        }
    }
}
