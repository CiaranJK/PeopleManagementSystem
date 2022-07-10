package com.example.people_managment_system.model

import javax.persistence.*

@Entity
@Table(name = "companies")
open class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id", nullable = false)
    open var companyId: Int? = null

    @Column(name = "company_name", nullable = false, length = 50)
    open var companyName: String? = null

    @Column(name = "website", length = 50)
    open var website: String? = null

    override fun toString(): String {
        return companyName.toString()
    }
}
