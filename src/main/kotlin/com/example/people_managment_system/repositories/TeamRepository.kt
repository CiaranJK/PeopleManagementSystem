package com.example.people_managment_system.repositories

import com.example.people_managment_system.model.Team
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface TeamRepository : CrudRepository<Team, Int> {

    @Query("select t from Team t where t.teamID = ?1")
    fun getTeamById(teamId: Int?): Team?
}