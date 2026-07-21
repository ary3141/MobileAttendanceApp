package com.example.mobilesurapp.repository

import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.model.Admin
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {

    suspend fun getProfile(adminId: Int): ApiResult<Admin>

    suspend fun updateProfile(
        adminId: Int,
        username: String,
        email: String
    ): ApiResult<Boolean>

}
