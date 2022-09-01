package com.ciarankieran.core.controller

import com.ciarankieran.core.domain.employee.EmployeeDto
import com.ciarankieran.core.domain.team.TeamDto
import com.ciarankieran.core.config.LoadResourceAsString
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
internal class TeamControllerTest @Autowired constructor (
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
    val stringResources: LoadResourceAsString
    ) {
    val baseUrl = "/api/teams/"

    @Nested
    @DisplayName("retrieveById()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class RetrieveById {
        @Test
        fun `should retrieve a team with the given ID`() {
            // given
            val id = 4005
            // when/then
            mockMvc.get("$baseUrl/$id")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.companyName") { value("Facebook") }
                    jsonPath("$.managerId") { value(2) }
                }
        }
        @Test
        fun `should String response if the Id does not exist`() {
            // given
            val id = 88888
            // when/then
            mockMvc.get("$baseUrl/$id")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                    content { stringResources.noResourceResponse }
                }
        }
    }

    @Nested
    @DisplayName("AddNew()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class AddNew {
        @Nested
        @DisplayName("AddNew()")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)

        inner class AddNew {
            @Test
            fun `should save a team to the team repository`() {
                // given
                val newTeamDto = TeamDto(
                    14,
                    "Facebook",
                    14
                )
                // when
                val saveTeam = mockMvc.post("$baseUrl/add-team") {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(newTeamDto)
                }
                //then
                saveTeam
                    .andDo { print() }
                    .andExpect {
                        status { isCreated() }
                        content { stringResources.successfulInputResponse}
                    }
            }

            @Test
            fun `should return BAD REQUEST if given incomplete data to create an Team Entity`() {
                // given
                val newTeamDto = TeamDto(
                    11114,
                    "Non-Existent-Company",
                    99999
                )
                // when
                val saveCompany = mockMvc.post("$baseUrl/add-team") {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(newTeamDto)
                }
                // then
                saveCompany
                    .andDo { print() }
                    .andExpect {
                        status { isBadRequest() }
                        content { stringResources.badInputResponse }
                    }
            }
        }
    }

    @Nested
    @DisplayName("Delete()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class Delete {
        @Test
        fun `should delete an team's record from the team repository`() {
            // given
            val id = 21
            // when/then
            mockMvc.delete("$baseUrl/delete/$id")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                }
            mockMvc.get("$baseUrl/$id")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                    content { stringResources.noResourceResponse }
                }
        }
        @Test
        fun `should return a String Not Found Response if the team Id does not exist`() {
            // given
            val id = 76223
            // when/then
            mockMvc.delete("$baseUrl/delete-team/$id")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                    content { stringResources.noResourceResponse }
                }
        }
    }

    @Nested
    @DisplayName("UpdateTeamForEmployee()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)

    inner class UpdateTeamForEmployee {
        @Test
        fun `should update an employee's team`() {
            // given
            val companyName = "Facebook"
            val employeeId = 3302
            // when
            val updateEmployeeTeam = mockMvc.patch("$baseUrl/update-team-for-employee/$companyName/$employeeId") {
                contentType = MediaType.APPLICATION_JSON
                content = 4006
            }
            //then
            updateEmployeeTeam
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.teamId") { value(4006) }
                }
        }

        @Test
        fun `should return NOT FOUND if the employee Id does not exist`() {
            // given
            val companyName = "Facebook"
            val employeeID = 0
            // when
            val updateEmployeeTeam = mockMvc.patch("$baseUrl/$companyName/update-team/$employeeID") {
                contentType = MediaType.APPLICATION_JSON
                content = 3
            }
            // then
            updateEmployeeTeam
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                    content { stringResources.noResourceResponse }
                }
        }
    }

    @Nested
    @DisplayName("RetrieveEmployeesInATeam()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class RetrieveEmployeesInATeam {
        @Test
        fun `should retrieve the employees in a team`() {
            // given
            val teamId = "4007"
            // when/then
            mockMvc.get("$baseUrl/$teamId/employees")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.[0].email") { value("bstreich@facebook.com") }
                    jsonPath("$.[-1].name") { value("Orlando Kerluke") }
                }
        }
        @Test
        fun `should return an Empty List if the team ID does not exist`() {
            // given
            val teamId = 999
            // when/then
            mockMvc.get("$baseUrl/$teamId/employees")
                .andDo { print() }
                .andExpect {
                    content { emptyList<EmployeeDto>() }
                }
        }
    }
}
