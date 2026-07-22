package com.example.mobilesurapp.repository

import com.example.mobilesurapp.BuildConfig
import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.api.RegisterResponse
import com.example.mobilesurapp.api.WebSocketService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterRepositoryImpl @Inject constructor(
    private val webSocketService: WebSocketService
) : RegisterRepository {

    companion object {
        private const val WEBSOCKET_URL = BuildConfig.WSS_URL
    }

    override suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): ApiResult<RegisterResponse> {

        webSocketService.connect(WEBSOCKET_URL)

        return webSocketService.register(
            username,
            email,
            password
        )
    }
}