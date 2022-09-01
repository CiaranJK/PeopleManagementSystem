package com.ciarankieran.core.domain.team

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TeamRepositoryTest @Autowired constructor (
    val repository: TeamRepository
) {
    @Nested
    @DisplayName("retrieveById()")
    inner class RetrieveById {
        @Test
        fun `should retrieve a team with the given ID`() {
            //when
            val result = repository.retrieveById(4005)
            //then
            assert(result?.manager?.id == 2 && result.company?.name == "Facebook")
        }
    }
}
