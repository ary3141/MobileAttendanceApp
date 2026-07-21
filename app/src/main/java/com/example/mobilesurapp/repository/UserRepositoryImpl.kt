package com.example.mobilesurapp.repository

import com.example.mobilesurapp.database.dao.EmployeeDao
import com.example.mobilesurapp.database.dao.PendingSyncDao
import com.example.mobilesurapp.model.Employee
import com.example.mobilesurapp.model.PendingSyncData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val employeeDao: EmployeeDao,
    private val pendingSyncDao: PendingSyncDao
) : UserRepository {

    override suspend fun saveEmployee(employee: Employee): Boolean {
        return try {
            val localId = employeeDao.insertEmployee(employee).toInt()

            pendingSyncDao.insertPendingSync(
                PendingSyncData(
                    entityType = "EMPLOYEE",
                    localEntityId = localId,
                    action = "ADD",
                    timestamp = System.currentTimeMillis()
                )
            )

            true
        } catch (e: Exception) {
            false
        }
    }

    override fun getEmployeeById(localId: Int): Flow<Employee?> {
        return employeeDao.getEmployeeByLocalId(localId)
    }

    override fun getAllEmployees(): Flow<List<Employee>> {
        return employeeDao.getAllEmployees()
    }

    override suspend fun deleteEmployee(localId: Int): Boolean {
        return try {
            employeeDao.deleteEmployeeByLocalId(localId)

            pendingSyncDao.insertPendingSync(
                PendingSyncData(
                    entityType = "EMPLOYEE",
                    localEntityId = localId,
                    action = "DELETE",
                    timestamp = System.currentTimeMillis()
                )
            )

            true
        } catch (e: Exception) {
            false
        }
    }
}