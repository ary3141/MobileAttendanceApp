package com.example.mobilesurapp.domain.usecase

import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.api.RegisterResponse
import com.example.mobilesurapp.repository.RegisterRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: RegisterRepository
) {

    suspend operator fun invoke(
        username: String,
        email: String,
        password: String
    ): ApiResult<RegisterResponse> {

        return repository.registerUser(
            username,
            email,
            password
        )

    }

}