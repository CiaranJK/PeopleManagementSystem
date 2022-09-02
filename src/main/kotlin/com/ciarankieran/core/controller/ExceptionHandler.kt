package com.ciarankieran.core.controller

import com.ciarankieran.core.config.LoadResourceAsString
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler(
    @Autowired
    val stringResources: LoadResourceAsString
) {
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(stringResources.noResourceResponse, HttpStatus.NOT_FOUND)

    /***
     * Handles exceptions related to resources not being found in the database. For example, when a client
     * submits an unknown data point such as an ID or name that doesn't exist.
     ***/

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleBadDataInput(e: DataIntegrityViolationException): ResponseEntity<String> =
        ResponseEntity(stringResources.badInputResponse, HttpStatus.BAD_REQUEST)

    /***
     * Handles exceptions related to requests from a client that cannot be processed due to
     * incorrect or insufficient data being supplied.
     ***/

    @ExceptionHandler(MismatchedInputException::class)
    fun handleBadDataInput(e: MismatchedInputException): ResponseEntity<String> =
        ResponseEntity(stringResources.badInputResponse, HttpStatus.BAD_REQUEST)

    /***
     * When the client submits requests containing data of the wrong datatype, a MisMatchedInput
     * exception is thrown. This class handles the exception.
     ***/

    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun handleBadDataInput(e: EmptyResultDataAccessException): ResponseEntity<String> =
        ResponseEntity(stringResources.badInputResponse, HttpStatus.BAD_REQUEST)

    /***
     * When the client submits a patch request but the resource it is submitting for change cannot be found
     * in the database, an EmptyResultDataAccessException is thrown.  This class handles the exception.
     ***/
}

/***
 * The ApiExceptionHandler class contains the methods that are in place to handle certain exceptions that
 * are thrown a client inputs requests containing incorrect or insufficient data.
 * A string-based reply is provided in response to each exception.
 ***/


