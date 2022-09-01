package com.ciarankieran.core.domain.team

interface TeamImpl {

    fun mapTeamEntityToDto(team: Team): TeamDto {
        return TeamDto(
            team.id,
            team.company?.name.toString(),
            team.manager?.id
        )
    }
}
/***
 * This interface contains a method that converts a Team Entity object to a Team Data Transfer Object in order
 * to pass Team related information from the database, to a client which submits requests to the API.
 ***/
