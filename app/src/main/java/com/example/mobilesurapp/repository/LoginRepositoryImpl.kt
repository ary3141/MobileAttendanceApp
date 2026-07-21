package com.example.mobilesurapp.repository

import com.example.mobilesurapp.BuildConfig
import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.api.LoginResponse
import com.example.mobilesurapp.api.WebSocketService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val webSocketService: WebSocketService
) : LoginRepository {

    companion object {
        private const val WEBSOCKET_URL = BuildConfig.WSS_URL
    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): ApiResult<LoginResponse> {

        webSocketService.connect(WEBSOCKET_URL)

        return webSocketService.login(email, password)
    }
}