package com.ciarankieran.core.domain.company

import javax.persistence.*

@Entity
@Table(name = "companies")
open class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id", nullable = false)
    open var id: Int = 0

    @Column(name = "company_name", nullable = false, length = 50)
    open lateinit var name: String

    @Column(name = "website", length = 50)
    open var website: String? = null

    override fun toString(): String {
        return name
    }
}

/***
 * This is the Company class that corresponds to entities in the 'companies' table in the Postgres database.
***/
