package com.example.practica1u3.Data.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "grade") val grade: String,
    @ColumnInfo(name = "group") val group: String,
    @ColumnInfo(name = "score") val score: Float
)