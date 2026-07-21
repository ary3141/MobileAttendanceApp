package com.example.mobilesurapp.domain.usecase

import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.model.FaceVerificationResult
import com.example.mobilesurapp.repository.FaceRepository
import javax.inject.Inject

class VerifyFaceUseCase @Inject constructor(
    private val faceRepository: FaceRepository
) {

    suspend operator fun invoke(
        embeddings: FloatArray
    ): ApiResult<FaceVerificationResult> {

        return faceRepository.verifyFace(embeddings)

    }
}