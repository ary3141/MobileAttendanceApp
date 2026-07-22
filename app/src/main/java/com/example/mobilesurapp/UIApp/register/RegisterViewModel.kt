package com.example.mobilesurapp.UIApp.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _fullName = MutableStateFlow("")
    val fullName: StateFlow<String> = _fullName.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> =
        _confirmPassword.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> =
        _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> =
        _error.asStateFlow()

    private val _registerSuccess =
        MutableStateFlow(false)

    val registerSuccess: StateFlow<Boolean> =
        _registerSuccess.asStateFlow()

    fun onFullNameChange(value: String) {
        _fullName.value = value
    }

    fun onEmailChange(value: String) {
        _email.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }

    fun onConfirmPasswordChange(value: String) {
        _confirmPassword.value = value
    }

    fun clearError() {
        _error.value = null
    }

    fun register() {

        if (_fullName.value.isBlank()) {
            _error.value = "Full name cannot be empty."
            return
        }

        if (_email.value.isBlank()) {
            _error.value = "Email cannot be empty."
            return
        }

        if (_password.value.length < 8) {
            _error.value = "Password must contain at least 8 characters."
            return
        }

        if (_password.value != _confirmPassword.value) {
            _error.value = "Passwords do not match."
            return
        }

        viewModelScope.launch {

            _loading.value = true
            _error.value = null

            val result = registerUseCase(
                username = _fullName.value,
                email = _email.value,
                password = _password.value
            )

            _loading.value = false

            when (result) {

                is ApiResult.Success -> {
                    _registerSuccess.value = true
                }

                is ApiResult.Error -> {
                    _error.value =
                        result.exception.message ?: "Registration failed."
                }

                else -> Unit
            }

        }

    }

}