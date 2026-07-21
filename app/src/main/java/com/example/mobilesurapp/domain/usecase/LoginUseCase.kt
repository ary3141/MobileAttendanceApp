package com.example.mobilesurapp.domain.usecase

import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.api.LoginResponse
import com.example.mobilesurapp.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {

    suspend operator fun invoke(
        email: String,
        password: String
    ): ApiResult<LoginResponse> {

        return repository.loginUser(
            email,
            password
        )

    }
}