package com.example.people_managment_system.controller

import com.example.people_managment_system.dtos.EmployeeDto
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
internal class SystemControllerTest @Autowired constructor (
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
    ) {

    val baseUrl = "/api/system"

    @Nested
    @DisplayName("getEmployee()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)

    inner class GetEmployee {

        @Test
        fun `should retrieve an employee with the given ID`() {
            // given
            val employeeID = 3301
            // when/then
            mockMvc.get("$baseUrl/employees/$employeeID")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.employeeName") { value("Violette Heathcote") }
                        jsonPath("$.salary") { value(45000) }
                    }
                }

        @Test
        fun `should return NOT FOUND if the employee Id does not exist`() {
            // given
            val employeeID = 0
            // when/then
            mockMvc.get("$baseUrl/employees/$employeeID")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }

    @Nested
    @DisplayName("GetEmployeesInACompany()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)

    inner class GetEmployeesInACompany {

        @Test
        fun `should retrieve the employees in a company`() {
            // given
            val companyName = "Amazon"
            // when/then
            mockMvc.get("$baseUrl/$companyName/employees")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.[0].companyName") { value("Amazon") }
                    jsonPath("$.[-1].employeeName") { value("Ursela Prendergast") }
                }
        }

        @Test
        fun `should return an Empty List if the company name does not exist`() {
            // given
            val companyName = "does_not_exist"
            // when/then
            mockMvc.get("$baseUrl/$companyName/employees")
                .andDo { print() }
                .andExpect {
                    content { emptyList<EmployeeDto>() }
                }
        }
    }

    @Nested
    @DisplayName("GetAverageSalaryInACompany()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)

    inner class GetAverageSalaryInACompany {

        @Test
        fun `should find the average salary in a company`() {
            // given
            val companyName = "LinkedIn"
            val averageSalaryAtLinkedIn = 55200
            // when/then
            mockMvc.get("$baseUrl/average-salary/$companyName")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { string("The average salary at $companyName is $averageSalaryAtLinkedIn") }
                }
        }

        @Test
        fun `should return a String Not Found Response if the company name does not exist`() {
            // given
            val companyName = "does_not_exist"
            // when/then
            mockMvc.get("$baseUrl/average-salary/$companyName")
                .andDo { print() }
                .andExpect {
                    content { string("Could not find any salary listing at the company name provided") }
                }
        }
    }

    @Nested
    @DisplayName("SaveEmployee()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)

    inner class SaveEmployee {

        @Test
        fun `should save an employee to the employee repository`() {

            // given
            val companyName = "Facebook"
            val newEmployeeDto = EmployeeDto(
                11114,
                "Nick Muleavey",
                "nmuleavey@google.com",
                55000
            )
            // when
            val saveEmployee = mockMvc.post("$baseUrl/$companyName/add-employee") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newEmployeeDto)
            }
            //then
            saveEmployee
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { string("The Employee has been successfully saved to PeopleMs") }
                }
        }

        @Test
        fun `should return BAD REQUEST if given incomplete data to create an Employee Entity`() {
            // given
            val companyName = "Facebook"
            val newEmployeeDto = EmployeeDto(
                0
            )
            // when
            val saveEmployee = mockMvc.post("$baseUrl/$companyName/add-employee") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newEmployeeDto)
            }
            // then
            saveEmployee
                .andDo { print() }
                .andExpect {
                    status { isBadRequest()
                        content { string("Could not add the employee to your company. " +
                                "Incorrect or insufficient data supplied.") }
                    }
                }
        }
    }

    @Nested
    @DisplayName("updateTeamOfEmployee()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)

    inner class UpdateTeamOfEmployee {

        @Test

        fun `should update an employee's team`() {
            // given
            val companyName = "Facebook"
            val employeeID = 3302
            // when
            val updateEmployeeTeam = mockMvc.patch("$baseUrl/$companyName/update-team/$employeeID") {
                contentType = MediaType.APPLICATION_JSON
                content = 4005
            }
            //then
            updateEmployeeTeam
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.teamID") { value(4005) }
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
                    content { string("Could not find an employee with that ID") }
                }
        }
    }

    @Nested
    @DisplayName("updateManagerOfEmployee()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)

    inner class UpdateManager {
        @Test
        fun `should update an employee's manager`() {
            // given
            val companyName = "Facebook"
            val employeeID = 3302
            // when
            val updateEmployeeManager = mockMvc.patch("$baseUrl/$companyName/update-manager/$employeeID") {
                contentType = MediaType.APPLICATION_JSON
                content = 3
            }
            //then
            updateEmployeeManager
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.managerID") { value(3) }
                }
        }

        @Test
        fun `should return NOT FOUND if the employee Id does not exist`() {
            // given
            val companyName = "Facebook"
            val employeeID = 0
            // when
            val updateEmployeeManager = mockMvc.patch("$baseUrl/$companyName/update-manager/$employeeID") {
                contentType = MediaType.APPLICATION_JSON
                content = 3
            }
            // then
            updateEmployeeManager
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                    content { string("Could not find an employee with that ID") }
                }
        }
    }

    @Nested
    @DisplayName("deleteEmployeeRecord()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)

    inner class DeleteExistingEmployee {
        @Test
        fun `should delete an employee's record from the employee repository`() {
            // given
            val employeeID = 3309
            // when/then
            mockMvc.delete("$baseUrl/delete-employee/$employeeID")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { string("The employee's record has been deleted from PeopleMS.") }
                }
            mockMvc.get("$baseUrl/$employeeID")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
        @Test
        fun `should return a String Not Found Response if the employee Id does not exist`() {
            // given
            val employeeID = 0
            // when/then
            mockMvc.delete("$baseUrl/delete-employee/$employeeID")
                .andDo { print() }
                .andExpect {
                    content { string("Could not find an employee with that ID") }
                }
        }
    }
}