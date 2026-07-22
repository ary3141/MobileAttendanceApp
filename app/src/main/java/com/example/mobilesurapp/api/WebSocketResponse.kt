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

data class RegisterResponse(

    @SerializedName("type")
    val type: String,

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String? = null,

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

data class EmployeeListResponse(

    @SerializedName("type")
    val type: String,

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String?,

    @SerializedName("employees")
    val employees: List<EmployeeDto> = emptyList()

)

data class EmployeeDto(

    @SerializedName("employee_id")
    val employeeId: Int?,

    @SerializedName("employee_code")
    val employeeCode: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("department")
    val department: String?,

    @SerializedName("position")
    val position: String?
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
data class DashboardSummaryData(

    @SerializedName("totalEmployees")
    val totalEmployees: Int,

    @SerializedName("attendanceToday")
    val attendanceToday: Int

)

data class DashboardSummaryResponse(

    @SerializedName("type")
    val type: String,

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("data")
    val data: DashboardSummaryData? = null

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