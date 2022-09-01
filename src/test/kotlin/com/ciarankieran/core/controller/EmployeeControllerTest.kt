package com.ciarankieran.core.controller

import com.ciarankieran.core.domain.employee.EmployeeDto
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
internal class EmployeeControllerTest @Autowired constructor (

    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
    val stringResources: LoadResourceAsString
) {
    val baseUrl = "/api/employees/"

    @Nested
    @DisplayName("retrieveById()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class RetrieveById {
        @Test
        fun `should retrieve an employee with the given ID`() {
            // given
            val id = 3301
            // when/then
            mockMvc.get("$baseUrl/$id")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.name") { value("Violette Heathcote") }
                        jsonPath("$.salary") { value(45000) }
                    }
                }
        @Test
        fun `should return NOT FOUND if the employee Id does not exist`() {
            // given
            val id = 0
            // when/then
            mockMvc.get("$baseUrl/$id")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                    content { stringResources.badInputResponse }
                }
        }
    }

    @Nested
    @DisplayName("AddNew()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class AddNew {
        @Test
        fun `should save an employee to the employee repository`() {

            // given
            val newEmployeeDto = EmployeeDto(
                11114,
                "Nick Muleavey",
                "nmuleavey@google.com",
                55000,
                "Facebook"
            )
            // when
            val saveEmployee = mockMvc.post("$baseUrl/add-employee") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newEmployeeDto)
            }
            //then
            saveEmployee
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { stringResources.successfulInputResponse}
                }
        }
        @Test
        fun `should return BAD REQUEST if given incomplete data to create an Employee Entity`() {
            // given
            val companyName = "Facebook"
            val badInput = "Bad Input"
            // when
            val saveEmployee = mockMvc.post("$baseUrl/add-employee") {
                contentType = MediaType.APPLICATION_JSON
                content = badInput
            }
            // then
            saveEmployee
                .andDo { print() }
                .andExpect {
                    status { isBadRequest()
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
        fun `should delete an employee's record from the employee repository`() {
            // given
            val id = 3307
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
        fun `should return a String Response if the employee Id does not exist`() {
            // given
            val id = 0
            // when/then
            mockMvc.delete("$baseUrl/delete/$id")
                .andDo { print() }
                .andExpect {
                    content { stringResources.noResourceResponse }
                }
        }
    }
}
