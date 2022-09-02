package com.ciarankieran.core.controller

import com.ciarankieran.core.domain.manager.ManagerDto
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
internal class ManagerControllerTest @Autowired constructor (
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
    val stringResources: LoadResourceAsString
) {
    val baseUrl = "/api/managers/"

    @Nested
    @DisplayName("retrieveById()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class RetrieveById {
        @Test
        fun `should retrieve a manager with the given ID`() {
            // given
            val id = 4
            // when/then
            mockMvc.get("$baseUrl/$id")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.name") { value("Jannie Boehm") }
                    jsonPath("$.companyName") { value("Facebook") }
                }
        }
        @Test
        fun `should return NOT FOUND if the Id does not exist`() {
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
        @Test
        fun `should save a manager to the manager repository`() {
            // given
            val newManagerDto = ManagerDto(
                11189,
                "Michelle Ryan",
                "Facebook",
                "Marketing"
            )
            // when
            val saveManager = mockMvc.post("$baseUrl/add-manager") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newManagerDto)
            }
            //then
            saveManager
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { stringResources.successfulInputResponse}
                }
        }
        @Test
        fun `should return BAD REQUEST if given incomplete data to create an Employee Entity`() {
            // given
            val badInput = "Bad Input"
            // when
            val saveManager = mockMvc.post("$baseUrl/add-manager") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(badInput)
            }
            // then
            saveManager
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                    content { stringResources.badInputResponse }
                }
        }
    }

    @Nested
    @DisplayName("Delete()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class Delete {
        @Test
        fun `should delete a manager's record from the manager repository`() {
            // given
            val id = 24
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
        fun `should return a String Not Found Response if the employee Id does not exist`() {
            // given
            val id = 99
            // when/then
            mockMvc.delete("$baseUrl/delete/$id")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                    content { stringResources.noResourceResponse }
                }
        }
    }

    @Nested
    @DisplayName("retrieveManagerForAnEmployee()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class RetrieveManagerForAnEmployee {
        @Test
        fun `should retrieve a manager for an Employee with the given employee ID`() {
            // given
            val employeeId = 3302
            // when/then
            mockMvc.get("$baseUrl/find-manager-for-employee/$employeeId")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.name") { value("Iva Lindgren") }
                    jsonPath("$.companyName") { value("Facebook") }
                }
        }
        @Test
        fun `should return NOT FOUND if the Id does not exist`() {
            // given
            val employeeId = 88888
            // when/then
            mockMvc.get("$baseUrl/$employeeId")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                    content { stringResources.noResourceResponse }
                }
        }
    }

    @Nested
    @DisplayName("UpdateManagerForTeam()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class UpdateManagerForTeam {
        @Test
        fun `should update a team's manager`() {
            // given
            val companyName = "Facebook"
            val teamId = 4007
            // when
            val updateEmployeeTeam = mockMvc.patch("$baseUrl/update-manager-for-team/$companyName/$teamId") {
                contentType = MediaType.APPLICATION_JSON
                content = 3
            }
            //then
            updateEmployeeTeam
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.managerId") { value(3) }
                }
        }
        @Test
        fun `should return NOT FOUND if the employee Id does not exist`() {
            // given
            val companyName = "Facebook"
            val teamId = 4007
            // when
            val updateEmployeeTeam = mockMvc.patch("$baseUrl/$companyName/update-team/$teamId") {
                contentType = MediaType.APPLICATION_JSON
                content = 22222
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
}
