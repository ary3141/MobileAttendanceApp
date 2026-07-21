package com.example.mobilesurapp.domain.usecase

import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.model.Employee
import com.example.mobilesurapp.repository.FaceRepository
import javax.inject.Inject

class RegisterUserWithFaceUseCase @Inject constructor(
    private val faceRepository: FaceRepository
) {

    suspend operator fun invoke(
        adminId: Int?,
        name: String,
        email: String,
        phone: String,
        department: String,
        position: String,
        embeddings: FloatArray
    ): ApiResult<Boolean> {

        val employee = Employee(
            employeeId = null,
            employeeCode = null,
            adminId = adminId,
            name = name,
            email = email,
            phone = phone,
            department = department,
            position = position,
            embeddings = embeddings
        )

        return faceRepository.registerEmployeeWithFace(employee)
    }
}