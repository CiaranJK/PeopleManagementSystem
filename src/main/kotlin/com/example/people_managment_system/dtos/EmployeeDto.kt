package com.example.people_managment_system.dtos

import java.io.Serializable

data class EmployeeDto(
    var employeeID: Int? = null,
    var employeeName: String? = null,
    var email: String? = null,
    var salary: Int? = null,
    var companyName: String? = null,
    var managerID: Int? = null,
    var teamID: Int? = null
) : Serializable
