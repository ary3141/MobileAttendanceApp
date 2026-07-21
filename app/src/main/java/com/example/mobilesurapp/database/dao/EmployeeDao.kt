package com.example.mobilesurapp.database.dao

import androidx.room.*
import com.example.mobilesurapp.model.Employee
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: Employee): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployees(employees: List<Employee>)

    @Query("DELETE FROM employees WHERE employee_id IS NOT NULL")
    suspend fun clearOnlineEmployees()

    @Query("SELECT * FROM employees WHERE localId = :localId")
    fun getEmployeeByLocalId(localId: Int): Flow<Employee?>

    @Query("SELECT * FROM employees")
    fun getAllEmployees(): Flow<List<Employee>>

    @Query("DELETE FROM employees WHERE localId = :localId")
    suspend fun deleteEmployeeByLocalId(localId: Int)

    @Query("SELECT * FROM employees WHERE employee_id = :employeeId")
    fun getEmployeeByBackendId(employeeId: Int): Flow<Employee?>

    @Query("SELECT * FROM employees WHERE email = :email")
    fun getEmployeeByEmail(email: String): Flow<Employee?>
}