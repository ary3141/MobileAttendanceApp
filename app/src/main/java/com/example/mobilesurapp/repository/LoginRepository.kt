package com.example.mobilesurapp.repository

import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.api.LoginResponse

interface LoginRepository {

    suspend fun loginUser(
        email: String,
        password: String
    ): ApiResult<LoginResponse>

}