package com.ciarankieran.core.domain.manager

import java.io.Serializable

data class ManagerDto(
    val id: Int,
    val name: String,
    var companyName: String,
    var department: String?
): Serializable

/***
 * This is the Manager Dto class that is used to transfer the details of Manager entities to and from
 * the database.
 * The class can be instantiated using JSON information that is provided by a client, and subsequently
 * used to create, update and delete Manager entities that exist in the database.
 ***/
