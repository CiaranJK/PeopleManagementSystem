package com.ciarankieran.core.domain.employee

import com.ciarankieran.core.domain.company.Company
import com.ciarankieran.core.domain.team.Team
import javax.persistence.*

@Entity
@Table(name = "employees")
open class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    open var id: Int = 0

    @Column(name = "employee_name", nullable = false)
    open lateinit var name: String

    @Column(name = "email", length = 50)
    open var email: String? = null

    @Column(name = "salary")
    open var salary: Int? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    open var company: Company? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    open var team: Team? = null
}

/***
 * This is the Employee class that that corresponds to entities in the 'employees' table in the Postgres database.
***/
