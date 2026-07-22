package com.example.mobilesurapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "employees",
    indices = [
        Index(value = ["employee_code"], unique = true),
        Index(value = ["email"], unique = true),
        Index(value = ["phone"], unique = true)
    ]
)
data class Employee(

    @PrimaryKey(autoGenerate = true)
    val localId: Int = 0,

    @SerializedName("employee_id")
    @ColumnInfo(name = "employee_id")
    val employeeId: Int? = null,

    @SerializedName("employee_code")
    @ColumnInfo(name = "employee_code")
    val employeeCode: String? = null,

    @SerializedName("admin_id")
    @ColumnInfo(name = "admin_id")
    val adminId: Int? = null,

    val name: String,

    val email: String,

    val phone: String,

    val department: String? = null,

    val position: String? = null,

    val embeddings: FloatArray
) {

    override fun equals(other: Any?): Boolean {

        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Employee

        return localId == other.localId &&
                employeeId == other.employeeId &&
                employeeCode == other.employeeCode &&
                adminId == other.adminId &&
                name == other.name &&
                email == other.email &&
                phone == other.phone &&
                department == other.department &&
                position == other.position &&
                embeddings.contentEquals(other.embeddings)

    }

    override fun hashCode(): Int {

        var result = localId
        result = 31 * result + (employeeId ?: 0)
        result = 31 * result + (employeeCode?.hashCode() ?: 0)
        result = 31 * result + (adminId ?: 0)
        result = 31 * result + name.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + phone.hashCode()
        result = 31 * result + department.hashCode()
        result = 31 * result + position.hashCode()
        result = 31 * result + embeddings.contentHashCode()

        return result

    }

}