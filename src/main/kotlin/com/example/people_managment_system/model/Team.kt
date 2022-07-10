package com.example.people_managment_system.model

import javax.persistence.*

@Entity
@Table(name = "teams")
open class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", nullable = false)
    open var teamID: Int? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    open var company: Company? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    open var manager: Manager? = null

    override fun toString(): String {
        return teamID.toString()
    }
}