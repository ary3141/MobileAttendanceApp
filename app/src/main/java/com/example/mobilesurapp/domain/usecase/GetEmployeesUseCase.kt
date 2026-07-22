package com.example.mobilesurapp.domain.usecase

import com.example.mobilesurapp.repository.EmployeeRepository
import javax.inject.Inject

class GetEmployeesUseCase @Inject constructor(
    private val repository: EmployeeRepository
) {

    suspend operator fun invoke() =
        repository.getEmployees()

}