package com.example.practica1u3.Data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.practica1u3.Data.Entity.Student
import com.example.practica1u3.Data.InterfaceStudent.StudentDAO

@Database(entities = [Student::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun StudentDao(): StudentDAO
}