package com.example.people_managment_system.repositories

import com.example.people_managment_system.model.Manager
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface ManagerRepository : CrudRepository<Manager, Int> {

    @Query("select m from Manager m where m.managerID = ?1")
    fun getManagerById(managerId: Int?): Manager?

    @Query("select m from Manager m where m.managerName = ?1")
    fun getManagerByName(managerName: String?): Manager?
}