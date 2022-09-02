package com.ciarankieran.core.domain.employee

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
    class EmployeeImplTest @Autowired constructor (
        val repository: EmployeeRepository
        ): EmployeeImpl {

        @Nested
        @DisplayName("mapEmployeeEntityToDto()")
        inner class MapEmployeeEntityToDto {
            @Test
            fun `should create a EmployeeDto object from a employee entity`() {
                //when
                val result = repository.retrieveById(3301)?.let { mapEmployeeEntityToDto(it) }
                //then
                assert(result is EmployeeDto)
            }
        }
}