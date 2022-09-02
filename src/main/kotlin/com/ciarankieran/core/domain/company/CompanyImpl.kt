package com.ciarankieran.core.domain.company

interface CompanyImpl {

    fun mapCompanyEntityToDto(company: Company): CompanyDto {
        return CompanyDto(
            company.id,
            company.name,
            company.website
        )
    }
}

/***
 * This interface contains a method that converts an CompanyEntity object to a Company Data Transfer Object in order
 * to pass Company related information from the database to a client, which submits requests to the API.
 ***/
