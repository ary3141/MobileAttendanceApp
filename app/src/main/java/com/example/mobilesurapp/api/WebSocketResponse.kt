package com.example.mobilesurapp.api

import com.google.gson.annotations.SerializedName

/**
 * Login
 */
data class LoginResponse(

    @SerializedName("type")
    val type: String,

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("token")
    val token: String? = null,

    @SerializedName("adminId")
    val adminId: Int? = null,

    @SerializedName("username")
    val username: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("role")
    val role: String? = null

)

/**
 * Admin Profile
 */
data class AdminProfile(

    @SerializedName("id")
    val id: Int,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("role")
    val role: String

)

data class ProfileResponse(

    @SerializedName("type")
    val type: String,

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("data")
    val data: AdminProfile? = null

)

/**
 * Employee
 */
data class EmployeeResponse(

    @SerializedName("type")
    val type: String,

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("employeeId")
    val employeeId: Int? = null,

    @SerializedName("employeeCode")
    val employeeCode: String? = null

)

/**
 * Face Recognition
 */
data class FaceRecognitionResponse(

    @SerializedName("type")
    val type: String,

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("match")
    val match: Boolean = false,

    @SerializedName("employeeId")
    val employeeId: Int? = null,

    @SerializedName("employeeCode")
    val employeeCode: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("department")
    val department: String? = null,

    @SerializedName("position")
    val position: String? = null,

    @SerializedName("distance")
    val distance: Float? = null,

    @SerializedName("confidence")
    val confidence: Float? = null

)

/**
 * Attendance
 */
data class AttendanceResponse(

    @SerializedName("type")
    val type: String,

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String? = null

)