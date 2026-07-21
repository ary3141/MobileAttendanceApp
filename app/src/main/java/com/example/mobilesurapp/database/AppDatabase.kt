package com.example.mobilesurapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobilesurapp.database.dao.EmployeeDao
import com.example.mobilesurapp.database.dao.PendingSyncDao
import com.example.mobilesurapp.model.Employee
import com.example.mobilesurapp.model.PendingSyncData
import androidx.room.TypeConverters
import com.example.mobilesurapp.database.converters.FloatArrayConverter

@Database(
    entities = [
        Employee::class,
        PendingSyncData::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(FloatArrayConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
    abstract fun pendingSyncDao(): PendingSyncDao
}