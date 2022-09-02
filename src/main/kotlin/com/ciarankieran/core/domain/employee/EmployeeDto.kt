package com.ciarankieran.core.domain.employee

import java.io.Serializable

class EmployeeDto(
    val id: Int,
    val name: String,
    var email: String? = null,
    var salary: Int? = null,
    var companyName: String,
    var teamId: Int? = null
): Serializable

/***
 * This is the Employee Dto class that is used to transfer the details of Employee entities to and from
 * the database.
 * The class can be instantiated using JSON information that is provided by a client, and subsequently
 * used to create, update and delete Employee entities that exist in the database.
 ***/
