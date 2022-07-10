package com.example.people_managment_system.model

import javax.persistence.*

@Entity
@Table(name = "employees")
open class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    open var employeeID: Int? = null

    @Column(name = "employee_name")
    open var employeeName: String? = null

    @Column(name = "email", length = 50)
    open var email: String? = null

    @Column(name = "salary")
    open var salary: Int? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    open var company: Company? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    open var manager: Manager? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    open var team: Team? = null

}






