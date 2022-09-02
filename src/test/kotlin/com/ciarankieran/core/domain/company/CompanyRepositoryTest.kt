package com.ciarankieran.core.domain.company

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CompanyRepositoryTest @Autowired constructor (
    val repository: CompanyRepository
) {
    @Nested
    @DisplayName("retrieveById()")
    inner class RetrieveById {
        @Test
        fun `should retrieve a company with the given ID`() {
            //when
            val result = repository.retrieveById(2)
            //then
            assert(result?.name == "Google" && result.website == "www.google.com")
        }
    }

    @Nested
    @DisplayName("getCompanyByName()")
    inner class GetCompanyByName {
        @Test
        fun `should retrieve a company with the given ID`() {
            //when
            val result = repository.getCompanyByName("Facebook")
            //then
            assert(result?.id == 1 && result.website == "www.facebook.com")
        }
    }
}
