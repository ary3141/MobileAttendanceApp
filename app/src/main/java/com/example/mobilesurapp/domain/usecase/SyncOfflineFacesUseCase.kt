package com.example.mobilesurapp.domain.usecase

import android.util.Log
import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.model.Employee
import com.example.mobilesurapp.repository.FaceRepository
import com.example.mobilesurapp.domain.utils.NetworkUtils
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SyncOfflineFacesUseCase @Inject constructor(
    private val faceRepository: FaceRepository,
    private val networkUtils: NetworkUtils
) {

    suspend operator fun invoke(): Boolean {

        if (!networkUtils.isOnline()) {
            Log.d("SyncOfflineFaces", "Offline, skipping sync.")
            return false
        }

        val pendingSyncs =
            faceRepository.getPendingSyncs().first()

        if (pendingSyncs.isEmpty()) {
            Log.d("SyncOfflineFaces", "No pending syncs.")
            return true
        }

        val employees =
            faceRepository.getLocalEmployees().first()

        var allSuccess = true

        for (sync in pendingSyncs) {

            when (sync.action) {

                "ADD" -> {

                    val employee = employees.find {
                        it.localId == sync.localEntityId
                    }

                    if (employee == null) {
                        continue
                    }
                    when (
                        faceRepository.registerEmployeeWithFace(employee)
                    ) {

                        is ApiResult.Success -> {
                            faceRepository.clearPendingSyncs()
                            Log.d("SyncOfflineFaces", "Employee synced successfully.")
                        }

                        is ApiResult.Error -> {
                            allSuccess = false
                            Log.e("SyncOfflineFaces", "Failed to sync employee.")
                        }

                        is ApiResult.Loading -> {
                            // Nothing to do
                        }
                    }
                }

                "DELETE" -> {

                    faceRepository.deleteLocalEmployee(
                        sync.localEntityId
                    )

                    faceRepository.clearPendingSyncs()

                    Log.d(
                        "SyncOfflineFaces",
                        "Delete synced."
                    )
                }
            }
        }

        return allSuccess
    }
}