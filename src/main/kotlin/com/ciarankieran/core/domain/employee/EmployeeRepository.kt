package com.ciarankieran.core.domain.employee

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface EmployeeRepository : JpaRepository<Employee, Int> {

    @Query("select e from Employee e where e.id = ?1")
    fun retrieveById(id: Int): Employee?

    /***
     * Queries the 'employees' table in the database and retrieves an Employee entity by its ID.
     ***/
}

/***
 * This interface communicates with the 'employees' table in the Postgres database. Through it, the API can
 * make changes to the table and query its information.
 ***/
