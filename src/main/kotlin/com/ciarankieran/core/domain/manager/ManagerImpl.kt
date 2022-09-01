package com.ciarankieran.core.domain.manager

interface ManagerImpl {

    fun mapManagerEntityToDto(manager: Manager): ManagerDto {
        return ManagerDto(
            manager.id,
            manager.name,
            manager.company?.name.toString(),
            manager.department
        )
    }
}

/***
 * This interface contains a method that converts a Manager Entity object to a Manager Data Transfer Object
 * in order to pass Manager related information from the database, to a client which submits requests to the API.
 ***/