package com.ciarankieran.core.controller

import com.ciarankieran.core.config.LoadResourceAsString
import com.ciarankieran.core.domain.company.CompanyDto
import com.ciarankieran.core.domain.employee.EmployeeDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
internal class CompanyControllerTest @Autowired constructor (
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
    val stringResources: LoadResourceAsString
) {
    val baseUrl = "/api/companies/"

    @Nested
    @DisplayName("retrieveById()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class RetrieveById {
        @Test
        fun `should retrieve a company with the given ID`() {
            // given
            val id = 1
            // when/then
            mockMvc.get("$baseUrl/$id")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.name") { value("Facebook") }
                    jsonPath("$.website") { value("www.facebook.com") }
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
                    content { stringResources.badInputResponse }
                }
        }
    }

    @Nested
    @DisplayName("AddNew()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class AddNew {
        @Test
        fun `should save a company to the company repository`() {
            // given
            val newCompanyDto = CompanyDto(
                6,
                "TikTok",
                "www.tiktok.com"
            )
            // when
            val saveCompany = mockMvc.post("$baseUrl/add-company") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newCompanyDto)
            }
            //then
            saveCompany
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
            val saveCompany = mockMvc.post("$baseUrl/add-company") {
                contentType = MediaType.APPLICATION_JSON
                content = badInput
            }
            // then
            saveCompany
                .andDo { print() }
                .andExpect {
                    status { isBadRequest()
                        content { stringResources.noResourceResponse }
                    }
                }
        }
    }

    @Nested
    @DisplayName("Delete()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class Delete {
        @Test
        fun `should delete an company's record from the company repository`() {
            // given
            val id = 10
            // when/then
            mockMvc.delete("$baseUrl/delete/$id")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                }
        }
        @Test fun `should return a String Response if the Company Id does not exist`() {
            // given
            val id = 76333
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
    @DisplayName("RetrieveEmployeesInCompany()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class RetrieveEmployeesInCompany {
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
                    jsonPath("$.[-1].name") { value("Ursela Prendergast") }
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
    @DisplayName("FindTheAverageSalaryInCompany()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class FindTheAverageSalaryInCompany {
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
                    content { stringResources.noResourceResponse }
                }
        }
    }
}
