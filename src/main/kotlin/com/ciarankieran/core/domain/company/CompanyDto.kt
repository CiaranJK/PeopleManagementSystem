package com.ciarankieran.core.domain.company

import java.io.Serializable

data class CompanyDto(
    val id: Int,
    val name: String,
    val website: String?
): Serializable

/***
 * This is the Company Dto class that is used to transfer the details of Company entities to and from
 * the database.
 * The class can be instantiated using JSON information that is provided by a client, and subsequently
 * used to create, update and delete Company entities that exist in the database.
 ***/