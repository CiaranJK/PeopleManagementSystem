package com.ciarankieran.core.domain.manager

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
    class ManagerImplTest @Autowired constructor (
        val repository: ManagerRepository
        ): ManagerImpl {

        @Nested
        @DisplayName("mapManagerEntityToDto()")
        inner class MapManagerEntityToDto {
            @Test
            fun `should create a ManagerDto object from a manager entity`() {
                //when
                val result = repository.retrieveById(14)?.let { mapManagerEntityToDto(it) }
                //then
                assert(result is ManagerDto)
            }
        }
}