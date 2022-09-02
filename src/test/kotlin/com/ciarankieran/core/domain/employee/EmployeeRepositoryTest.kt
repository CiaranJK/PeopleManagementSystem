package com.ciarankieran.core.domain.employee

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EmployeeRepositoryTest @Autowired constructor (
    val repository: EmployeeRepository
) {
    @Nested
    @DisplayName("retrieveById()")
    inner class RetrieveById {
        @Test
        fun `should retrieve an employee with the given ID`() {
            //when
            val result = repository.retrieveById(3302)
            //then
            assert(result?.name == "Stan Rath" && result.salary == 33000)
        }
    }
}
