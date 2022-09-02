package com.ciarankieran.core.usecases

import com.ciarankieran.core.domain.employee.EmployeeRepository
import com.ciarankieran.core.domain.team.TeamDto
import com.ciarankieran.core.domain.team.TeamRepository
import com.ciarankieran.core.usecases.team.TeamService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TeamServiceTest @Autowired constructor (
    val service: TeamService,
    val teamRepository: TeamRepository,
    val employeeRepository: EmployeeRepository
    ) {

    @Nested
    @DisplayName("retrieveById()")
    inner class RetrieveById {
        @Test
        fun `should retrieve an employee with the given ID`() {
            //when
            val result = service.retrieveById(4014)
            //then
            assert(result.companyName == "Amazon" && result.managerId == 11)
        }
    }

    @Nested
    @DisplayName("addNew()")
    inner class AddNew {
        @Test
        fun `should save an employee to the employee repository`() {
            // given
            val testTeamDto = TeamDto(
                15,
                "Facebook",
                2
            )
            // when
            service.addNew(testTeamDto)
            // then
            assert(
                teamRepository.retrieveById(15)?.manager?.id == 2 &&
                        teamRepository.retrieveById(15)?.company?.name == "Facebook"
            )
        }
    }

    @Nested
    @DisplayName("Delete()")
    inner class Delete {
        @Test
        fun `should delete an team's record from the team repository`() {
            // given
            val testTeamDto = TeamDto(
                18,
                "Amazon",
                17
            )
            service.addNew(testTeamDto)
            // when
            service.delete(18)
            // then
            assert((teamRepository.retrieveById(18) == null))
        }
    }

    @Nested
    @DisplayName("UpdateTeamForEmployee()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class UpdateTeamForEmployee {
        @Test
        fun `should update an employee's team`() {
            // given
            val companyName = "Google"
            val employeeId = 3311
            val teamId = 4009
            // when
            service.updateTeamForEmployee(companyName, employeeId, teamId)
            // then
            assert(employeeRepository.retrieveById(employeeId)?.team?.id == teamId)
        }
    }

    @Nested
    @DisplayName("RetrieveEmployeesInATeam()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class RetrieveEmployeesInATeam {
        @Test
        fun `should retrieve the employees in a team`() {
            // given
            val teamId = 4011
            // then
            val teamList = service.retrieveEmployeesInTeam(teamId)
            // then
            assert(teamList[1].name == "Brendan Thompson")
        }
    }
}
