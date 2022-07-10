package com.example.people_managment_system.model

import javax.persistence.*

@Entity
@Table(name = "managers")
open class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id", nullable = false)
    open var managerID: Int? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    open var company: Company? = null

    @Column(name = "manager_name", length = 50)
    open var managerName: String? = null

    @Column(name = "department", length = 50)
    open var department: String? = null

    override fun toString(): String {
        return managerName.toString()
}}