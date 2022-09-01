package com.ciarankieran.core.usecases

import com.ciarankieran.core.domain.employee.EmployeeDto
import com.ciarankieran.core.domain.employee.EmployeeRepository
import com.ciarankieran.core.usecases.employee.EmployeeService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
class EmployeeServiceTest @Autowired constructor (
    val service: EmployeeService,
    val employeeRepository: EmployeeRepository
    ) {

    @Nested
    @DisplayName("retrieveById()")
    inner class RetrieveById {
        @Test
        fun `should retrieve an employee with the given ID`() {
            //when
            val result = service.retrieveById(3321)
            //then
            assert(result.name == "Brennan Auer" && result.email == "bauer@linkedin.com")
        }
    }

    @Nested
    @DisplayName("addNew()")
    inner class AddNew {
        @Test
        fun `should save an employee to the employee repository`() {
            // given
            val testEmployeeDto = EmployeeDto(
                105,
                "Ruby Turner",
                "rturner@linkedin.com",
                70000,
                "LinkedIn",
                4011)
            // when
            service.addNew(testEmployeeDto)
            // then
            assert(employeeRepository.retrieveById(105)?.name == "Ruby Turner")
        }
    }

    @Nested
    @DisplayName("Delete()")
    inner class Delete {
        @Test
        fun `should delete a employee's record from the employee repository`() {
            // given
            val testEmployeeDto = EmployeeDto(
                106,
                "Mock Employee",
                "memployee@facebook.com",
                77000,
                "Facebook"
            )
            service.addNew(testEmployeeDto)
            // when
            service.delete(106)
            // then
            assert((employeeRepository.retrieveById(106) == null))
        }
    }
}
