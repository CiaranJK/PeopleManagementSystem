package com.ciarankieran.core.domain.manager

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ManagerRepository : JpaRepository<Manager, Int> {

    @Query("select m from Manager m where m.id = ?1")
    fun retrieveById(id: Int): Manager?

    /***
     * Queries the 'managers' table in the database and retrieves a Manager entity by its ID.
     ***/
}

/***
 * This interface communicates with the 'managers' table in the Postgres database. Through it, the API can
 * make changes to the table and query its information.
 ***/