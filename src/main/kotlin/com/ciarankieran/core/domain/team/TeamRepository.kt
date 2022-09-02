package com.ciarankieran.core.domain.team

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TeamRepository : JpaRepository<Team, Int> {

    @Query("select t from Team t where t.id = ?1")
    fun retrieveById(id: Int): Team?

    /***
     * Queries the 'teams' table in the database and retrieves a Team entity by its ID.
     ***/
}

/***
 * This interface communicates with the 'teams' table in the Postgres database. Through it, the API can
 * make changes to the table and query its information.
 ***/