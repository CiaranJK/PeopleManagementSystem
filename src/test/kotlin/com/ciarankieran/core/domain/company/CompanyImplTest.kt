package com.ciarankieran.core.domain.company

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CompanyImplTest @Autowired constructor (
    val repository: CompanyRepository
): CompanyImpl {
    @Nested
    @DisplayName("mapCompanyEntityToDto()")
    inner class MapCompanyEntityToDto {
        @Test
        fun `should create a CompanyDto object from a company entity`() {
            //when
            val result = repository.retrieveById(1)?.let { mapCompanyEntityToDto(it) }
            //then
            assert(result is CompanyDto)
        }
    }
}