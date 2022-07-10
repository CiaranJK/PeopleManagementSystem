package com.example.people_managment_system.repositories

import com.example.people_managment_system.model.Company
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CompanyRepository : CrudRepository<Company, Int> {

    @Query("select c from Company c where c.companyName = ?1")
    fun getCompanyByName(companyName: String?): Company?

}