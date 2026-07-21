package com.example.mobilesurapp.api

import com.example.mobilesurapp.model.Admin
import com.example.mobilesurapp.model.Employee
import com.example.mobilesurapp.model.FaceVerificationResult
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketService @Inject constructor(
    private val client: WebSocketClient,
    private val gson: Gson
) {

    fun connect(url: String) {
        client.connect(url)
    }

    fun disconnect() {
        client.disconnect()
    }

    suspend fun login(
        email: String,
        password: String
    ): ApiResult<LoginResponse> {

        val request = LoginRequest(
            email = email,
            password = password
        )

        if (!client.send(request)) {
            return ApiResult.Error(
                Exception("Unable to send login request")
            )
        }

        val response = waitForResponse("login")

        val login =
            gson.fromJson(
                response,
                LoginResponse::class.java
            )

        return if (
            login.success &&
            !login.token.isNullOrEmpty() &&
            login.adminId != null
        ) {

            ApiResult.Success(login)

        } else {

            ApiResult.Error(
                Exception(login.message ?: "Login failed")
            )

        }
    }

    suspend fun registerEmployee(
        employee: Employee
    ): ApiResult<Boolean> {

        val request = RegisterEmployeeRequest(
            adminId = employee.adminId ?: 0,
            name = employee.name,
            email = employee.email,
            phone = employee.phone,
            department = employee.department,
            position = employee.position,
            embeddings = employee.embeddings.map { it.toDouble() }
        )

        if (!client.send(request)) {
            return ApiResult.Error(
                Exception("Unable to send employee request")
            )
        }

        val response = waitForResponse("insert_employee")

        val result =
            gson.fromJson(
                response,
                EmployeeResponse::class.java
            )

        return if (result.success) {

            ApiResult.Success(true)

        } else {

            ApiResult.Error(
                Exception(result.message ?: "Registration failed")
            )

        }

    }

    suspend fun verifyEmployee(
        embeddings: FloatArray
    ): ApiResult<FaceVerificationResult> {

        val request = VerifyEmployeeRequest(
            embeddings = embeddings.map { it.toDouble() }
        )

        if (!client.send(request)) {
            return ApiResult.Error(
                Exception("Unable to send verification request")
            )
        }

        val response = waitForResponse("recognize_face")

        val result =
            gson.fromJson(
                response,
                FaceRecognitionResponse::class.java
            )

        if (!result.success || !result.match) {
            return ApiResult.Error(
                Exception(result.message ?: "Employee not recognized")
            )
        }

        val employee = Employee(
            employeeId = result.employeeId,
            employeeCode = result.employeeCode,
            adminId = null,
            name = result.name ?: "",
            email = "",
            phone = "",
            department = result.department ?: "",
            position = result.position ?: "",
            embeddings = floatArrayOf()
        )

        return ApiResult.Success(
            FaceVerificationResult(
                isMatch = true,
                matchedEmployee = employee,
                distance = result.distance ?: -1f
            )
        )
    }

    suspend fun insertAttendance(
        employeeId: Int,
        checkType: String
    ): ApiResult<Boolean> {

        val request = AttendanceRequest(
            employeeId = employeeId,
            checkType = checkType
        )

        if (!client.send(request)) {
            return ApiResult.Error(
                Exception("Unable to send attendance")
            )
        }

        val response =
            waitForResponse("insert_attendance")

        val result =
            gson.fromJson(
                response,
                AttendanceResponse::class.java
            )

        return if (result.success) {

            ApiResult.Success(true)

        } else {

            ApiResult.Error(
                Exception(result.message ?: "Attendance failed")
            )

        }

    }

    suspend fun getProfile(
        adminId: Int
    ): ApiResult<Admin> {

        val request = GetProfileRequest(
            adminId = adminId
        )

        if (!client.send(request)) {
            return ApiResult.Error(
                Exception("Unable to request profile")
            )
        }

        val response =
            waitForResponse("profile_get")

        val result =
            gson.fromJson(
                response,
                ProfileResponse::class.java
            )

        if (!result.success || result.data == null) {

            return ApiResult.Error(
                Exception(result.message ?: "Profile not found")
            )

        }

        return ApiResult.Success(

            Admin(
                id = result.data.id.toString(),
                name = result.data.username,
                email = result.data.email,
                role = result.data.role
            )

        )

    }

    suspend fun updateProfile(
        adminId: Int,
        username: String,
        email: String
    ): ApiResult<Boolean> {

        val request =
            UpdateProfileRequest(
                adminId = adminId,
                username = username,
                email = email
            )

        if (!client.send(request)) {
            return ApiResult.Error(
                Exception("Unable to update profile")
            )
        }

        val response =
            waitForResponse("profile_update")

        val result =
            gson.fromJson(
                response,
                AttendanceResponse::class.java
            )

        return if (result.success) {

            ApiResult.Success(true)

        } else {

            ApiResult.Error(
                Exception(result.message ?: "Update failed")
            )

        }

    }

    private suspend fun waitForResponse(
        type: String
    ): String {

        return client.incomingMessages.first {

            try {
                JSONObject(it).optString("type") == type
            } catch (_: Exception) {
                false
            }

        }

    }
}
