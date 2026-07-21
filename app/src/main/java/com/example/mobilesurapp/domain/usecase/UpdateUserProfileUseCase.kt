package com.example.mobilesurapp.domain.usecase

import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.model.Admin
import com.example.mobilesurapp.repository.UserProfileRepository
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {

    suspend operator fun invoke(
        profile: Admin
    ): ApiResult<Boolean> {

        return repository.updateProfile(
            adminId = profile.id.toInt(),
            username = profile.name,
            email = profile.email
        )

    }
}