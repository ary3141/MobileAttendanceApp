package com.example.mobilesurapp.repository

import com.example.mobilesurapp.api.WebSocketService
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(
    private val webSocketService: WebSocketService
) : EmployeeRepository {

    override suspend fun getEmployees() =
        webSocketService.getEmployees()

}