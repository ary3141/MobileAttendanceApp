package com.example.mobilesurapp.repository

import com.example.mobilesurapp.api.ApiResult
import com.example.mobilesurapp.api.WebSocketService
import com.example.mobilesurapp.database.dao.EmployeeDao
import com.example.mobilesurapp.database.dao.PendingSyncDao
import com.example.mobilesurapp.domain.utils.NetworkUtils
import com.example.mobilesurapp.face.FaceUtils
import com.example.mobilesurapp.model.Employee
import com.example.mobilesurapp.model.FaceVerificationResult
import com.example.mobilesurapp.model.PendingSyncData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

interface FaceRepository {

    suspend fun registerEmployeeWithFace(employee: Employee): ApiResult<Boolean>

    suspend fun verifyFace(embeddings: FloatArray): ApiResult<FaceVerificationResult>

    fun getLocalEmployees(): Flow<List<Employee>>

    suspend fun markEmployeeForSync(localId: Int, action: String)

    suspend fun clearPendingSyncs()

    fun getPendingSyncs(): Flow<List<PendingSyncData>>

    suspend fun deleteLocalEmployee(localId: Int)
}

@Singleton
class FaceRepositoryImpl @Inject constructor(
    private val employeeDao: EmployeeDao,
    private val pendingSyncDao: PendingSyncDao,
    private val webSocketService: WebSocketService,
    private val networkUtils: NetworkUtils
) : FaceRepository {

    override suspend fun registerEmployeeWithFace(
        employee: Employee
    ): ApiResult<Boolean> {

        return if (networkUtils.isOnline()) {

            val result = webSocketService.registerEmployee(employee)

            if (result is ApiResult.Success<Boolean> && result.data) {

                pendingSyncDao.clearAllPendingSyncs()

                return ApiResult.Success(true)
            } else {

                val localId = employeeDao.insertEmployee(employee).toInt()

                pendingSyncDao.insertPendingSync(
                    PendingSyncData(
                        entityType = "EMPLOYEE",
                        localEntityId = localId,
                        action = "ADD",
                        timestamp = System.currentTimeMillis()
                    )
                )

                ApiResult.Error(
                    Exception("Failed to register employee online."),
                    "Employee saved locally and queued for sync."
                )
            }

        } else {

            val localId = employeeDao.insertEmployee(employee).toInt()

            pendingSyncDao.insertPendingSync(
                PendingSyncData(
                    entityType = "EMPLOYEE",
                    localEntityId = localId,
                    action = "ADD",
                    timestamp = System.currentTimeMillis()
                )
            )

            ApiResult.Success(true)
        }
    }

    override suspend fun verifyFace(
        embeddings: FloatArray
    ): ApiResult<FaceVerificationResult> {

        return if (networkUtils.isOnline()) {

            webSocketService.verifyEmployee(embeddings)

        } else {

            val storedEmployees = employeeDao.getAllEmployees().first()

            if (storedEmployees.isEmpty()) {
                return ApiResult.Success(
                    FaceVerificationResult(
                        isMatch = false,
                        matchedEmployee = null,
                        distance = -1f
                    )
                )
            }

            var closestEmployee: Employee? = null
            var minDistance = Float.MAX_VALUE

            for (employee in storedEmployees) {

                val distance = FaceUtils.calculateEuclideanDistance(
                    embeddings,
                    employee.embeddings
                )

                if (distance < minDistance) {
                    minDistance = distance
                    closestEmployee = employee
                }
            }

            val isMatch =
                closestEmployee != null &&
                        minDistance < FaceUtils.RECOGNITION_THRESHOLD

            ApiResult.Success(
                FaceVerificationResult(
                    isMatch = isMatch,
                    matchedEmployee = closestEmployee,
                    distance = minDistance
                )
            )
        }
    }

    override fun getLocalEmployees(): Flow<List<Employee>> {
        return employeeDao.getAllEmployees()
    }

    override suspend fun markEmployeeForSync(
        localId: Int,
        action: String
    ) {
        pendingSyncDao.insertPendingSync(
            PendingSyncData(
                entityType = "EMPLOYEE",
                localEntityId = localId,
                action = action,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    override suspend fun clearPendingSyncs() {
        pendingSyncDao.clearAllPendingSyncs()
    }

    override fun getPendingSyncs(): Flow<List<PendingSyncData>> {
        return pendingSyncDao.getAllPendingSyncs()
    }

    override suspend fun deleteLocalEmployee(localId: Int) {
        employeeDao.deleteEmployeeByLocalId(localId)
    }
}