package com.ciarankieran.core.usecases

import com.ciarankieran.core.domain.company.CompanyDto
import com.ciarankieran.core.domain.company.CompanyRepository
import com.ciarankieran.core.usecases.company.CompanyService
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
class CompanyServiceTest @Autowired constructor (
    val service: CompanyService,
    val companyRepository: CompanyRepository
    ) {

    @Nested
    @DisplayName("retrieveById()")
    inner class RetrieveById {
        @Test
        fun `should retrieve a company with the given ID`() {
            //when
            val result = service.retrieveById(11)
            //then
            assert(result.name == "TikTok" && result.website == "www.tiktok.com")
        }
    }

    @Nested
    @DisplayName("addNew()")
    inner class AddNew {
        @Test
        fun `should save a company to the company repository`() {
            // given
            val testCompanyDto = CompanyDto(
                32,
                "Twitter",
                "www.twitter.com"
            )
            // when
            service.addNew(testCompanyDto)
            // then
            assert(companyRepository.retrieveById(32)?.name == "Twitter")
        }
    }

    @Nested
    @DisplayName("Delete()")
    inner class Delete {
        @Test
        fun `should delete an company's record from the company repository`() {
            // given
            val testCompanyDto = CompanyDto(
                33,
                "Twitter",
                "www.twitter.com"
            )
            service.addNew(testCompanyDto)
            // when
            service.delete(33)
            // then
            assert((companyRepository.retrieveById(33) == null))
        }
    }

    @Nested
    @DisplayName("RetrieveEmployeesInCompany()")
    inner class RetrieveEmployeesInCompany {
        @Test
        fun `should retrieve the employees in a company`() {
            // given
            val companyName = "LinkedIn"
            // when
            val linkedInEmployeesList = service.retrieveEmployeesInCompany(companyName)
            // then
            assert(linkedInEmployeesList[0].name == "Elliot Oberbrunner")
        }
    }

    @Nested
    @DisplayName("FindTheAverageSalaryInCompany()")
    inner class FindTheAverageSalaryInCompany {
        @Test
        fun `should find the average salary in a company`() {
            // given
            val companyName = "Amazon"
            val averageSalaryAtAmazon = 61294
            // when
            val result = service.findTheAverageSalaryInCompany(companyName)
            // then
            assert(result == "The average salary at Amazon is 61294")
        }
    }
}
