package com.example.mobilesurapp.repository

import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.model.Employee

interface EmployeeRepository {

    suspend fun getEmployees():
            ApiResult<List<Employee>>

}