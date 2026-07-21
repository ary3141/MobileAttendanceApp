package com.example.mobilesurapp.UIApp.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.mobilesurapp.UIApp.login.LoginStateViewModel
import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.domain.usecase.LoginUseCase

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loginStateViewModel: LoginStateViewModel
) : ViewModel() {
    private val _id = MutableStateFlow("")
    val id: StateFlow<String> = _id

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isLoggingIn = MutableStateFlow(false)
    val isLoggingIn: StateFlow<Boolean> = _isLoggingIn

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError

    private val _jwtToken = MutableStateFlow<String?>(null)
    val jwtToken: StateFlow<String?> = _jwtToken

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        _loginError.value = null
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _loginError.value = null
    }

    fun login(onSuccess: (String) -> Unit) {

        viewModelScope.launch {

            _isLoggingIn.value = true
            _loginError.value = null
            _jwtToken.value = null

            when (val result = loginUseCase(_email.value, _password.value)) {

                is ApiResult.Success -> {

                    val login = result.data

                    _jwtToken.value = login.token

                    val adminId = login.adminId.toString()

                    loginStateViewModel.login(adminId)

                    onSuccess(adminId)

                }

                is ApiResult.Error -> {

                    _loginError.value =
                        result.exception.message ?: "Login gagal"

                    Log.e(
                        "LoginViewModel",
                        _loginError.value ?: ""
                    )

                }

                ApiResult.Loading -> Unit

            }

            _isLoggingIn.value = false

        }

    }
}