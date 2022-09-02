package com.ciarankieran.core.domain.employee

interface EmployeeImpl {

    fun mapEmployeeEntityToDto(employee: Employee): EmployeeDto {
        return EmployeeDto(
            employee.id,
            employee.name,
            employee.email,
            employee.salary,
            employee.company?.name.toString(),
            employee.team?.id
        )
    }
}

/***
 * This interface contains a method that converts an Employee Entity object to an Employee Data Transfer Object
 * in order to pass Employee related information from the database, to a client which submits requests to the API.
 ***/
