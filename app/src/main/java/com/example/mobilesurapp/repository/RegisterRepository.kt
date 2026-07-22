package com.example.mobilesurapp.repository

import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.api.RegisterResponse

interface RegisterRepository {

    suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): ApiResult<RegisterResponse>

}