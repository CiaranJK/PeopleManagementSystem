package com.ciarankieran.core.domain.company

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CompanyRepository : JpaRepository<Company, Int> {

    @Query("select c from Company c where c.id = ?1")
    fun retrieveById(id: Int): Company?

    /***
     * Queries the 'companies' table in the database and retrieves a Company entity by its ID.
     ***/

    @Query("select c from Company c where c.name = ?1")
    fun getCompanyByName(companyName: String?): Company?

    /***
     * Queries the 'companies' table in the database and retrieves a Company entity by its name.
     ***/
}

/***
 * This interface communicates with the 'companies' table in the Postgres database. Through it, the API can
 * make changes to the table and query its information.
 ***/
