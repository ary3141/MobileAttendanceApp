package com.example.mobilesurapp.repository

import com.example.mobilesurapp.model.Employee
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun saveEmployee(employee: Employee): Boolean

    fun getEmployeeById(localId: Int): Flow<Employee?>

    fun getAllEmployees(): Flow<List<Employee>>

    suspend fun deleteEmployee(localId: Int): Boolean

}