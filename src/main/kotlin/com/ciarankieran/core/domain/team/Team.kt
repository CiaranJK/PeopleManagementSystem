package com.ciarankieran.core.domain.team

import com.ciarankieran.core.domain.company.Company
import com.ciarankieran.core.domain.manager.Manager
import javax.persistence.*

@Entity
@Table(name = "teams")
open class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", nullable = false)
    open var id: Int = 0

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    open var company: Company? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id")
    open var manager: Manager? = null

    override fun toString(): String {
        return id.toString()
    }
}

/***
 * This is the Team class that corresponds entities in the 'teams' table in the Postgres database.
 ***/
