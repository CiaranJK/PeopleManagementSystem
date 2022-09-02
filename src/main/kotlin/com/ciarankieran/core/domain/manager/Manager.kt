package com.ciarankieran.core.domain.manager

import com.ciarankieran.core.domain.company.Company
import javax.persistence.*

@Entity
@Table(name = "managers")
open class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id", nullable = false)
    open var id: Int = 0

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    open var company: Company? = null

    @Column(name = "manager_name", length = 50)
    open lateinit var name: String

    @Column(name = "department", length = 50)
    open var department: String? = null

    override fun toString(): String {
        return name
    }
}

/***
 * This is the Manager class that corresponds to entities in the 'managers' table in the Postgres database.
***/
