package com.ciarankieran.core.domain.team

import java.io.Serializable

data class TeamDto (
    val id: Int,
    var companyName: String,
    var managerId: Int?
): Serializable

/***
 * This is the Team Dto class that is used to transfer the details of Team entities to and from
 * the database.
 * The class can be instantiated using JSON information that is provided by a client, and subsequently
 * used to create, update and delete Team entities that exist in the database.
 ***/