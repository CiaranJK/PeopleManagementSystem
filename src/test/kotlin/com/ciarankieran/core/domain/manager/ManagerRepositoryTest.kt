package com.ciarankieran.core.domain.manager

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ManagerRepositoryTest @Autowired constructor (
    val repository: ManagerRepository
) {
    @Nested
    @DisplayName("retrieveById()")
    inner class RetrieveById {
        @Test
        fun `should retrieve a manager with the given ID`() {
            //when
            val result = repository.retrieveById(9)
            //then
            assert(result?.name == "Kailee Jacobson" && result.company?.name == "LinkedIn")
        }
    }
}
