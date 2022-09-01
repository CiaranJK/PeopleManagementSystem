package com.ciarankieran.core.domain.team

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
    class TeamImplTest @Autowired constructor (
        val repository: TeamRepository
        ): TeamImpl {

        @Nested
        @DisplayName("mapTeamEntityToDto()")
        inner class MapTeamEntityToDto {
            @Test
            fun `should create a TeamDto object from a team entity`() {
                //when
                val result = repository.retrieveById(4011)?.let { mapTeamEntityToDto(it) }
                //then
                assert(result is TeamDto)
            }
        }
}