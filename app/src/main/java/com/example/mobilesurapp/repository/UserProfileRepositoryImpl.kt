package com.example.mobilesurapp.repository

import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.api.WebSocketService
import com.example.mobilesurapp.model.Admin
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val webSocketService: WebSocketService
) : UserProfileRepository {

    override suspend fun getProfile(
        adminId: Int
    ): ApiResult<Admin> {

        return webSocketService.getProfile(adminId)

    }

    override suspend fun updateProfile(
        adminId: Int,
        username: String,
        email: String
    ): ApiResult<Boolean> {

        return webSocketService.updateProfile(
            adminId = adminId,
            username = username,
            email = email
        )

    }
}