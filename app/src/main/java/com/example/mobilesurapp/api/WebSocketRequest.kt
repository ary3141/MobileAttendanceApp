package com.example.mobilesurapp.api

import com.google.gson.annotations.SerializedName

/**
 * Authentication
 */

data class LoginRequest(

    @SerializedName("type")
    val type: String = "login",

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String

)

data class BiometricLoginRequest(

    @SerializedName("type")
    val type: String = "biometric_login",

    @SerializedName("embeddings")
    val embeddings: List<Double>

)

/**
 * Employee
 */

data class RegisterEmployeeRequest(

    @SerializedName("type")
    val type: String = "insert_employee",

    @SerializedName("adminId")
    val adminId: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("department")
    val department: String,

    @SerializedName("position")
    val position: String,

    @SerializedName("embeddings")
    val embeddings: List<Double>

)

data class VerifyEmployeeRequest(

    @SerializedName("type")
    val type: String = "recognize_face",

    @SerializedName("embeddings")
    val embeddings: List<Double>

)

/**
 * Attendance
 */

data class AttendanceRequest(

    @SerializedName("type")
    val type: String = "insert_attendance",

    @SerializedName("employeeId")
    val employeeId: Int,

    @SerializedName("checkType")
    val checkType: String

)

/**
 * Profile
 */

data class GetProfileRequest(

    @SerializedName("type")
    val type: String = "profile_get",

    @SerializedName("adminId")
    val adminId: Int

)

data class UpdateProfileRequest(

    @SerializedName("type")
    val type: String = "profile_update",

    @SerializedName("adminId")
    val adminId: Int,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String

)