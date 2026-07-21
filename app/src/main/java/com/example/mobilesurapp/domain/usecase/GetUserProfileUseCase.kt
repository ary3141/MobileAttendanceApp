package com.example.mobilesurapp.domain.usecase

import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.model.Admin
import com.example.mobilesurapp.repository.UserProfileRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {

    suspend operator fun invoke(
        adminId: Int
    ): ApiResult<Admin> {

        return repository.getProfile(adminId)

    }
}